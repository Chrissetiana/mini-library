package com.chrissetiana.bookapp;

public class BookActivity {
    private String bookTitle; // items[] > volumeinfo{} > bookTitle""
    private String bookAuthor; // items[] > volumeinfo{} > authors[]
    private String published; // items[] > volumeinfo{} > publishedDate""
    private String publisher; // items[] > volumeinfo{} > publisher""
    private String bookPages; // items[] > volumeinfo{} > pageCount#
    private String bookThumbnail; // items[] > volumeinfo{} > imageLinks{} > smallThumbnail""
    private String bookDescription; // items[] > searchinfo{} > textSnippet
    private String isbn; // items[] > volumeinfo{} > industryIdentifiers[] > identifiers""

    public BookActivity(String title, String author, String year, String publisher, String pages, String description, String image) {
        bookTitle = title;
        bookAuthor = author;
        published = year;
        this.publisher = publisher;
        bookPages = pages;
        bookDescription = description;
        bookThumbnail = image;
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

    public String getBookThumbnail() {
        return bookThumbnail;
    }
}
