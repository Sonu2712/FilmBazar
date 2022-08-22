package com.film.debugview.logs;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;

import com.film.debugview.LumberYard.Entry;
import com.film.debugview.R;
import com.film.debugview.core.adapter.BindableAdapter;
import com.film.debugview.databinding.DebugLogsListItemBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.functions.Consumer;

final class LogAdapter extends BindableAdapter<Entry> implements Consumer<Entry> {
    private List<Entry> logs;

    public LogAdapter(Context context) {
        super(context);
        logs = Collections.emptyList();
    }

    public static @DrawableRes
    int backgroundForLevel(int level) {
        switch (level) {
            case Log.VERBOSE:
            case Log.DEBUG:
                return R.color.debug_log_accent_debug;
            case Log.INFO:
                return R.color.debug_log_accent_info;
            case Log.WARN:
                return R.color.debug_log_accent_warn;
            case Log.ERROR:
            case Log.ASSERT:
                return R.color.debug_log_accent_error;
            default:
                return R.color.debug_log_accent_unknown;
        }
    }

    public void setLogs(List<Entry> logs) {
        this.logs = new ArrayList<>(logs);
    }

    @Override public int getCount() {
        return logs.size();
    }

    @Override public Entry getItem(int i) {
        return logs.get(i);
    }

    @Override public long getItemId(int i) {
        return i;
    }

    @Override public View newView(LayoutInflater inflater, int position, ViewGroup container) {
        View view = inflater.inflate(R.layout.debug_logs_list_item, container, false);
        DebugLogsListItemBinding binding = DebugLogsListItemBinding.bind(view);
        view.setTag(binding);
        return view;
    }

    @Override public void bindView(Entry item, int position, View view) {
        DebugLogsListItemBinding binding = (DebugLogsListItemBinding) view.getTag();
        setEntry(binding, item);
    }

    @Override public void accept(Entry entry) throws Exception {
        logs.add(entry);
        notifyDataSetChanged();
    }

    public void setEntry(DebugLogsListItemBinding binding, Entry entry) {
        binding.debugLogLevel.setBackgroundResource(backgroundForLevel(entry.level));
        binding.debugLogLevel.setText(entry.displayLevel());
        binding.debugLogTag.setText(entry.tag);
        binding.debugLogMessage.setText(entry.message);
    }
}
