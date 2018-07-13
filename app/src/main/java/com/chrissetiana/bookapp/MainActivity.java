package com.chrissetiana.bookapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String source = "https://www.googleapis.com/books/v1/volumes?q=";
    BookAdapter adapter;
    ProgressBar progress;
    TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new BookAdapter(this, new ArrayList<BookActivity>());

        ImageButton searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText searchText = findViewById(R.id.search_text);
                String searchQuery = source + searchText.getText().toString();
                // String searchStr = "https://www.googleapis.com/books/v1/volumes?q=flowers+inauthor:keyes";
                Log.v("MainActivity", searchQuery);
            }
        });

        ListView list = findViewById(R.id.list_items);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                BookActivity current = adapter.getItem(position);
                Uri uri = Uri.parse(getString(R.string.hint_link) + current.getIsbn());
                Intent webIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(webIntent);
                Log.v("MainActivity", "Redirecting to " + uri.toString());
            }
        });

        emptyText = findViewById(R.id.list_empty);
        list.setEmptyView(emptyText);

        progress = findViewById(R.id.list_progress);
        progress.setVisibility(View.GONE);
    }
}