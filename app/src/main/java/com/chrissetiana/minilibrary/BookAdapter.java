package com.chrissetiana.minilibrary;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<BookActivity> {

    BookAdapter(Activity context, ArrayList<BookActivity> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.activity_layout, parent, false);
        }

        BookActivity current = getItem(position);
        assert current != null;

        TextView title = view.findViewById(R.id.book_title);
        title.setText(current.getBookTitle());

        TextView author = view.findViewById(R.id.book_author);
        author.setText(current.getBookAuthor());

        TextView published = view.findViewById(R.id.book_year);
        published.setText(current.getPublished());

        TextView publisher = view.findViewById(R.id.book_publisher);
        publisher.setText(current.getPublisher());

        TextView pages = view.findViewById(R.id.book_pages);
        pages.setText(current.getBookPages());

        TextView description = view.findViewById(R.id.book_desc);
        description.setText(current.getBookDescription());

        ImageView thumbnail = view.findViewById(R.id.book_image);
        if (current.getBookThumbnail() != null) {
            Picasso.get()
                    .load(current.getBookThumbnail())
                    .placeholder(R.drawable.no_image)
                    .centerCrop()
                    .into(thumbnail);
            thumbnail.setVisibility(View.VISIBLE);
        } else {
            thumbnail.setVisibility(View.GONE);
        }

        return view;
    }
}