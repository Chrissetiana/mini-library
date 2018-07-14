package com.chrissetiana.bookapp;

import java.util.ArrayList;

public class BookActivity extends ArrayList<BookActivity> {
    private String bookTitle; // items[] > volumeinfo{} > bookTitle""
    private String bookAuthor; // items[] > volumeinfo{} > authors[]
    private String published; // items[] > volumeinfo{} > publishedDate""
    private String publisher; // items[] > volumeinfo{} > publisher""
    private String bookPages; // items[] > volumeinfo{} > pageCount#
    private int bookThumbnail = -1; // items[] > volumeinfo{} > imageLinks{} > smallThumbnail""
    private String bookDescription; // items[] > searchinfo{} > textSnippet
    private String isbn; // items[] > volumeinfo{} > industryIdentifiers[] > identifiers""

    public BookActivity(String title, String author, String year, String publisher, String pages, String description, int image, String isbn) {
        bookTitle = title;
        bookAuthor = author;
        published = year;
        this.publisher = publisher;
        bookPages = pages;
        bookDescription = description;
        bookThumbnail = image;
        this.isbn = isbn;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public String getPublished() {
        return published;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getBookPages() {
        return bookPages;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getBookThumbnail() {
        return bookThumbnail;
    }

    public boolean hasImage() {
        return getBookThumbnail() != -1;
    }
}
