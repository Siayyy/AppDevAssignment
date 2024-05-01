package com.example.ullibraryonlinesystem;

public class BorrowedBook {
        private int bookId;
        private String title;
        private String author;
        private String synopsis;
        private String coverImagePath;

        // 构造函数
        public BorrowedBook(int bookId, String title, String author, String synopsis, String coverImagePath) {
                this.bookId = bookId;
                this.title = title;
                this.author = author;
                this.synopsis = synopsis;
                this.coverImagePath = coverImagePath;
        }

        public String getTitle() {
                return title;
        }

        // 省略 getter 和 setter 方法...
}

