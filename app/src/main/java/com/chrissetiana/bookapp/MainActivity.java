package com.chrissetiana.bookapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String SOURCE = "https://developers.google.com/books/docs/v1/getting_started#intro";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<BookActivity> books = BookQuery.fetchData(SOURCE);
        BookAdapter adapter = new BookAdapter(this, books);
        ListView list = findViewById(R.id.list_items);
        list.setAdapter(adapter);

        View emptyText = findViewById(R.id.list_empty);
        list.setEmptyView(emptyText);

        View progress = findViewById(R.id.list_progress);
        progress.setVisibility(View.GONE);
    }
}