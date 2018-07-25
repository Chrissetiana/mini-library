package com.chrissetiana.minilibrary;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    BookAdapter adapter;
    ProgressBar progress;
    TextView emptyText;
    private String source = "https://www.googleapis.com/books/v1/volumes?q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new BookAdapter(this, new ArrayList<BookActivity>());

        ListView list = findViewById(R.id.list_items);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                BookActivity current = adapter.getItem(position);
                assert current != null;

                Uri uri = Uri.parse(getString(R.string.hint_link) + current.getIsbn());

                Intent webIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(webIntent);
                Toast.makeText(MainActivity.this, "Redirecting to website", Toast.LENGTH_SHORT).show();

                Log.v("MainActivity", "Redirecting to " + uri.toString());
            }
        });

        emptyText = findViewById(R.id.list_empty);
        list.setEmptyView(emptyText);

        progress = findViewById(R.id.list_progress);
        progress.setVisibility(View.GONE);

        ImageButton searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emptyText.setText("");
                EditText searchText = findViewById(R.id.search_text);
                String searchQuery = source + searchText.getText().toString().trim();

                BookAsyncTask task = new BookAsyncTask();
                task.execute(searchQuery);
                progress.setVisibility(View.VISIBLE);

                Log.v("MainActivity", searchQuery);
            }
        });
    }

    private class BookAsyncTask extends AsyncTask<String, Void, List<BookActivity>> {

        @Override
        protected List<BookActivity> doInBackground(String... strings) {
            if (strings.length < 1 || strings[0] == null) {
                return null;
            }

            return BookQuery.fetchData(strings[0]);
        }

        @Override
        protected void onPostExecute(List<BookActivity> data) {
            progress.setVisibility(View.GONE);
            adapter.clear();

            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                if (data != null && !data.isEmpty()) {
                    adapter.addAll(data);
                } else {
                    emptyText.setText(getString(R.string.no_result));
                }
            } else {
                progress.setVisibility(View.GONE);
                emptyText.setText(getString(R.string.no_conn));
            }
        }
    }
}