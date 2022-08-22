package com.film.debugview.logs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.film.app.core.utils.Intents;
import com.film.debugview.LumberYard;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public final class LogsDialog extends AlertDialog {
    private final LumberYard lumberYard;
    private final LogAdapter adapter;
    private ListView listView;
    private CompositeDisposable subscriptions;

    public LogsDialog(Context context, LumberYard lumberYard) {
        super(context);
        this.lumberYard = lumberYard;

        adapter = new LogAdapter(context);

        listView = new ListView(context);
        listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
        listView.setAdapter(adapter);

        setTitle("Logs");
        setView(listView);
        setButton(BUTTON_NEGATIVE, "Close", (dialog, which) -> {
            // NO-OP.
        });
        setButton(BUTTON_POSITIVE, "Share", (dialog, which) -> LogsDialog.this.share());
    }

    @Override protected void onStart() {
        super.onStart();

        adapter.setLogs(lumberYard.bufferedLogs());

        subscriptions = new CompositeDisposable();
        subscriptions.add(lumberYard.logs()
                .observeOn(AndroidSchedulers.mainThread()) //
                .subscribe(adapter));

        listView.post(() -> listView.setSelection(adapter.getCount() - 1));
    }

    @Override protected void onStop() {
        super.onStop();
        subscriptions.dispose();
    }

    private void share() {
        Disposable subscribe = lumberYard.save() //
            .subscribeOn(Schedulers.io()) //
            .observeOn(AndroidSchedulers.mainThread()) //
            .subscribe(file -> {
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                Intents.maybeStartChooser(getContext(), sendIntent);
            }, throwable -> Toast.makeText(getContext(), "Couldn't save the logs for sharing.",
                Toast.LENGTH_SHORT).show());
    }
}
