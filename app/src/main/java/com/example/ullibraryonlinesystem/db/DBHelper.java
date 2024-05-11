package com.example.ullibraryonlinesystem.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ullibraryonlinesystem.BorrowedBook;
import com.example.ullibraryonlinesystem.LibraryBook;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "UserDB.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase database;



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建用户表
        db.execSQL("CREATE TABLE IF NOT EXISTS users (" +
                "email TEXT PRIMARY KEY, " +
                "password TEXT, " +
                "name TEXT, " +
                "major TEXT)");

        // 创建书籍信息表
        db.execSQL("CREATE TABLE IF NOT EXISTS books (" +
                "bookId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
                "author TEXT, " +
                "synopsis TEXT, " +
                "coverImagePath TEXT)");

        // 插入管理员数据
        db.execSQL("INSERT INTO users (email, password, name, major) VALUES ('admin@managementmail.ul.ie', '123456', 'Admin', 'Admin')");

        // 插入用户数据
        db.execSQL("INSERT INTO users (email, password, name, major) VALUES ('22371095@studentmail.ul.ie', '123456', 'Daniel', 'SoftwareDevelopment')");
        db.execSQL("INSERT INTO users (email, password, name, major) VALUES ('22003932@studentmail.ul.ie', '123456', 'Song', 'MachineLearning')");
        db.execSQL("INSERT INTO users (email, password, name, major) VALUES ('22098852@studentmail.ul.ie', '123456', 'Sia', 'SoftwareEngineering')");
        db.execSQL("INSERT INTO users (email, password, name, major) VALUES ('22053093@studentmail.ul.ie', '123456', 'Dennis', 'ManagementSystem')");
        db.execSQL("INSERT INTO users (email, password, name, major) VALUES ('22170871@studentmail.ul.ie', '123456', 'Wale', 'SoftwareDevelopment')");
    }


    // 方法来添加书籍到数据库
    public void addBookToLibrary(String title, String author, String synopsis, String coverImagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("author", author);
        values.put("synopsis", synopsis);
        values.put("coverImagePath", coverImagePath);

        db.insert("books", null, values);
        db.close();
    }

    // 方法来获取所有的书籍列表
    public List<LibraryBook> getAllLibraryBooks() {
        List<LibraryBook> bookList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("books", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex("bookId");
                int titleIndex = cursor.getColumnIndex("title");
                int authorIndex = cursor.getColumnIndex("author");
                int synopsisIndex = cursor.getColumnIndex("synopsis");
                int coverImagePathIndex = cursor.getColumnIndex("coverImagePath");

                LibraryBook book = new LibraryBook(
                        cursor.getInt(idIndex),
                        cursor.getString(titleIndex),
                        cursor.getString(authorIndex),
                        cursor.getString(synopsisIndex),
                        cursor.getString(coverImagePathIndex)
                );
                bookList.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookList;
    }


    // 方法来获取用户已借阅的书籍列表
    public List<BorrowedBook> getUserBorrowedBooks(int userId) {
        List<BorrowedBook> borrowedBookList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String BORROWED_BOOKS_QUERY = "SELECT * FROM books INNER JOIN user_books ON books.bookId = user_books.bookId WHERE user_books.userId = ?";
        Cursor cursor = db.rawQuery(BORROWED_BOOKS_QUERY, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                int bookIdIndex = cursor.getColumnIndex("bookId");
                int titleIndex = cursor.getColumnIndex("title");
                int authorIndex = cursor.getColumnIndex("author");
                int synopsisIndex = cursor.getColumnIndex("synopsis");
                int coverImagePathIndex = cursor.getColumnIndex("coverImagePath");

                BorrowedBook book = new BorrowedBook(
                        cursor.getInt(bookIdIndex),
                        cursor.getString(titleIndex),
                        cursor.getString(authorIndex),
                        cursor.getString(synopsisIndex),
                        cursor.getString(coverImagePathIndex)
                );
                borrowedBookList.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return borrowedBookList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    // 检查用户是否存在
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email=? AND password=?", new String[]{email, password});
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }

    // 在 DBHelper 类中添加以下方法
    public void addUser(String email, String password, String name, String major) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("name", name);
        contentValues.put("major", major);
        db.insert("users", null, contentValues);
        db.close();

}
}