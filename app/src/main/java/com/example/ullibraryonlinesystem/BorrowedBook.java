package com.example.ullibraryonlinesystem;

public class BorrowedBook {
        private int bookId;
        private String title;
        private String author;
        private String synopsis;
        private String coverImagePath;

        private String pagecount;

        // constructor
        public BorrowedBook(int bookId, String title, String author, String synopsis, String coverImagePath) {
                this.bookId = bookId;
                this.title = title;
                this.author = author;
                this.synopsis = synopsis;
                this.coverImagePath = coverImagePath;
                this.pagecount = pagecount;
        }

        public String getTitle() {
                return title;
        }

        // getter and setter...
}

