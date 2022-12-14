package com.film.debugview.core.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NetworkPercentageAdapter extends BindableAdapter<Integer> {
    private static final int[] VALUES = {
        0, 1, 3, 10, 25, 50, 75, 100
    };

    public NetworkPercentageAdapter(Context context) {
        super(context);
    }

    public static int getPositionForValue(int value) {
        for (int i = 0; i < VALUES.length; i++) {
            if (VALUES[i] == value) {
                return i;
            }
        }
        return 1; // Default to 3% if something changes.
    }

    @Override public int getCount() {
        return VALUES.length;
    }

    @Override public Integer getItem(int position) {
        return VALUES[position];
    }

    @Override public long getItemId(int position) {
        return position;
    }

    @Override public View newView(LayoutInflater inflater, int position, ViewGroup container) {
        return inflater.inflate(android.R.layout.simple_spinner_item, container, false);
    }

    @Override public void bindView(Integer item, int position, View view) {
        TextView tv = view.findViewById(android.R.id.text1);
        if (item == 0) {
            tv.setText("None");
        } else {
            tv.setText(item + "%");
        }
    }

    @Override
    public View newDropDownView(LayoutInflater inflater, int position, ViewGroup container) {
        return inflater.inflate(android.R.layout.simple_spinner_dropdown_item, container, false);
    }
}
