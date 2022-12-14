package com.film.debugview;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.processors.PublishProcessor;
import okio.BufferedSink;
import okio.Okio;
import timber.log.Timber;

@Singleton public final class LumberYard {
    private static final int BUFFER_SIZE = 200;

    private final Application app;

    private final Deque<Entry> entries = new ArrayDeque<>(BUFFER_SIZE + 1);
    private final PublishProcessor<Entry> entrySubject = PublishProcessor.create();

    @Inject public LumberYard(Application app) {
        this.app = app;
    }

    public Timber.Tree tree() {
        return new Timber.DebugTree() {
            @Override protected String createStackElementTag(StackTraceElement element) {
                return super.createStackElementTag(element) + ":" + element.getLineNumber();
            }

            @Override protected void log(int priority, String tag, String message, Throwable t) {
                addEntry(new Entry(priority, tag, message));
            }
        };
    }

    private synchronized void addEntry(Entry entry) {
        entries.addLast(entry);
        if (entries.size() > BUFFER_SIZE) {
            entries.removeFirst();
        }

        entrySubject.onNext(entry);
    }

    public List<Entry> bufferedLogs() {
        return new ArrayList<>(entries);
    }

    public PublishProcessor<Entry> logs() {
        return entrySubject;
    }

    /**
     * Save the current logs to disk.
     */
    public Observable<File> save() {
        return Observable.create(observableEmitter -> {
            File folder = app.getExternalFilesDir(null);
            if (folder == null) {
                observableEmitter.onError(new IOException("External storage is not mounted."));
                return;
            }

            String fileName = String.valueOf(System.currentTimeMillis());
            File output = new File(folder, fileName);

            BufferedSink sink = null;
            try {
                sink = Okio.buffer(Okio.sink(output));
                List<Entry> entries = bufferedLogs();
                for (Entry entry : entries) {
                    sink.writeUtf8(entry.prettyPrint()).writeByte('\n');
                }
                // need to close before emiting file to the subscriber, because when subscriber receives data in the same thread
                // the file may be truncated
                sink.close();
                sink = null;

                observableEmitter.onNext(output);
                observableEmitter.onComplete();
            } catch (IOException e) {
                observableEmitter.onError(e);
            } finally {
                if (sink != null) {
                    try {
                        sink.close();
                    } catch (IOException e) {
                        observableEmitter.onError(e);
                    }
                }
            }
        });
    }

    /**
     * Delete all of the log files saved to disk. Be careful not to call this before any intents
     * have
     * finished using the file reference.
     */
    public void cleanUp() {
        new AsyncTask<Void, Void, Void>() {
            @Override protected Void doInBackground(Void... folders) {
                try {
                    File folder = app.getExternalFilesDir(null);
                    if (folder != null) {
                        for (File file : folder.listFiles()) {
                            if (file.getName().endsWith(".log")) {
                                file.delete();
                            }
                        }
                    }
                } catch (Exception ignored) {
                }
                return null;
            }
        }.execute();
    }

    public static final class Entry {
        public final int level;
        public final String tag;
        public final String message;

        public Entry(int level, String tag, String message) {
            this.level = level;
            this.tag = tag;
            this.message = message;
        }

        public String prettyPrint() {
            return String.format("%22s %s %s", tag, displayLevel(),
                // Indent newlines to match the original indentation.
                message.replaceAll("\\n", "\n                         "));
        }

        public String displayLevel() {
            switch (level) {
                case Log.VERBOSE:
                    return "V";
                case Log.DEBUG:
                    return "D";
                case Log.INFO:
                    return "I";
                case Log.WARN:
                    return "W";
                case Log.ERROR:
                    return "E";
                case Log.ASSERT:
                    return "A";
                default:
                    return "?";
            }
        }
    }
}
