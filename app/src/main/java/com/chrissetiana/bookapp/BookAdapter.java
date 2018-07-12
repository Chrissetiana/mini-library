package com.chrissetiana.bookapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<BookActivity> {
    public BookAdapter(Activity context, ArrayList<BookActivity> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.activity_layout, parent, false);
            BookActivity current = getItem(position);

            TextView title = view.findViewById(R.id.book_title);
            title.setText(current.getBookTitle());
        }
        return view;
    }
}
