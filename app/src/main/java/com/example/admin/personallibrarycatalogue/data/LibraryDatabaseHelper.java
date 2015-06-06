package com.example.admin.personallibrarycatalogue.data;

/**
 * Created by Mikhail Valuyskiy on 25.05.2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.example.admin.personallibrarycatalogue.data.DatabaseContract.BooksTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class which helps work with database
 */
public class LibraryDatabaseHelper extends SQLiteOpenHelper {

    // When the database schema was changed, you must increment the database version
    private static final int DATABASE_VERSION = 4;
    static final String DATABASE_NAME = "library.db";

    public LibraryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates table to hold book data
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_BOOKS_TABLE = "CREATE TABLE " + BooksTable.TABLE_NAME + " (" +
                BooksTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                BooksTable.AUTHOR + " TEXT NOT NULL, " +
                BooksTable.TITLE + " TEXT NOT NULL, " +
                BooksTable.DESCRIPTION + " TEXT, " +
                BooksTable.COVER + " BLOB," +
                BooksTable.YEAR + " INTEGER, " +
                BooksTable.ISBN + " TEXT );";

        sqLiteDatabase.execSQL(SQL_CREATE_BOOKS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.BooksTable.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public static List<Book> getBooksList(Cursor cursor) {
        List<Book> bookList = new ArrayList<Book>();

        if (cursor.moveToFirst()) {
            do {
                Book book = getBook(cursor);
                bookList.add(book);
            } while (cursor.moveToNext());
        }
        return bookList;
    }

    public static Book getBook(Cursor cursor) {
        Book book = new Book();

        int position = cursor.getPosition();
        if (position > 0) {
            book = setBookValues(cursor);

        } else if (cursor.moveToFirst()) {
            book = setBookValues(cursor);
        }
            return book;
        }

    public static Book setBookValues (Cursor cursor){
        Book book = new Book();
        book.setId(cursor.getInt(cursor.getColumnIndex(BooksTable._ID)));
        book.setTitle(cursor.getString(cursor.getColumnIndex(BooksTable.TITLE)));
        book.setAuthor(cursor.getString(cursor.getColumnIndex(BooksTable.AUTHOR)));

        if ((cursor.getString(cursor.getColumnIndex(BooksTable.DESCRIPTION))) != null) {
            book.setDescription(cursor.getString(cursor.getColumnIndex(BooksTable.DESCRIPTION)));
        }

        if (cursor.getBlob(cursor.getColumnIndex(BooksTable.COVER)) != null) {
            book.setCover(cursor.getBlob(cursor.getColumnIndex(BooksTable.COVER)));
        }

        book.setYear(cursor.getInt(cursor.getColumnIndex(BooksTable.YEAR)));
        book.setIsbn(cursor.getString(cursor.getColumnIndex(BooksTable.ISBN)));

        return book;
    }

    public static ContentValues createBooksValues(Book book) {
        ContentValues booksValues = new ContentValues();
        booksValues.put(DatabaseContract.BooksTable.TITLE, book.getTitle());
        booksValues.put(DatabaseContract.BooksTable.AUTHOR, book.getAuthor());
        booksValues.put(DatabaseContract.BooksTable.DESCRIPTION, book.getDescription());
        booksValues.put(DatabaseContract.BooksTable.COVER, book.getCover());
        booksValues.put(BooksTable.YEAR, book.getYear());
        booksValues.put(BooksTable.ISBN, book.getIsbn());
        return booksValues;
    }

}
