package com.example.ullibraryonlinesystem;

public class LibraryBook {
        private int bookId;
        private String title;
        private String author;
        private String synopsis;
        private String coverImagePath;

        public LibraryBook(int bookId, String title, String author, String synopsis, String coverImagePath) {
                this.bookId = bookId;
                this.title = title;
                this.author = author;
                this.synopsis = synopsis;
                this.coverImagePath = coverImagePath;
        }
}

