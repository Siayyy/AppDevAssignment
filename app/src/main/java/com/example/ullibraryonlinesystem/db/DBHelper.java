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

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_MAJOR = "major";

    private SQLiteDatabase database;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create users table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_EMAIL + " TEXT PRIMARY KEY,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_MAJOR + " TEXT"
                + ")";
        db.execSQL(CREATE_USERS_TABLE);

        // create books table
        db.execSQL("CREATE TABLE IF NOT EXISTS books (" +
                "bookId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
                "author TEXT, " +
                "synopsis TEXT, " +
                "coverImagePath TEXT)");

        // insert admin data
        db.execSQL("INSERT INTO users (email, password, name, major) VALUES ('admin@managementmail.ul.ie', '123456', 'Admin', 'Admin')");

        // insert users data
        db.execSQL("INSERT INTO users (email, password, name, major) VALUES ('22371095@studentmail.ul.ie', '123456', 'Daniel', 'SoftwareDevelopment')");
        db.execSQL("INSERT INTO users (email, password, name, major) VALUES ('22003932@studentmail.ul.ie', '123456', 'Song', 'MachineLearning')");
        db.execSQL("INSERT INTO users (email, password, name, major) VALUES ('22098852@studentmail.ul.ie', '123456', 'Sia', 'SoftwareEngineering')");
        db.execSQL("INSERT INTO users (email, password, name, major) VALUES ('22053093@studentmail.ul.ie', '123456', 'Dennis', 'ManagementSystem')");
        db.execSQL("INSERT INTO users (email, password, name, major) VALUES ('22170871@studentmail.ul.ie', '123456', 'Wale', 'SoftwareDevelopment')");
    }

    // Method to add books to the database
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

    // Method to get a list of all the books
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

    // Method to get a list of books that the user has borrowed
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

    // Check whether the user already exists
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email=? AND password=?", new String[]{email, password});
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }

    // Check if the email already exists
    public boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_EMAIL};
        String selection = COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return exists;
    }

    // Add a new user to the database
    public void addUser(String email, String password, String name, String major) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_PASSWORD, password);
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_MAJOR, major);
        db.insert(TABLE_USERS, null, contentValues);
        db.close();
    }
}