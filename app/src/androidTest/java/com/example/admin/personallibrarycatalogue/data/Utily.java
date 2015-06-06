package com.example.admin.personallibrarycatalogue.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.AndroidTestCase;

import com.example.admin.personallibrarycatalogue.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by Mikhail Valuyskiy on 04.06.2015.
 */
public class Utily extends AndroidTestCase {

    private static final String BOOK_TITLE = "Thinking in Java";
    private static final String BOOK_AUTHOR = "Bruce Eckel";
    private static final String BOOK_DESCRIPTION = "Thinking in Java is a book about the Java programming language, written by Bruce Eckel and first published in 1998";

    static ContentValues createBooksValues() {
        ContentValues booksValues = new ContentValues();
        booksValues.put(DatabaseContract.BooksTable.TITLE, BOOK_TITLE);
        booksValues.put(DatabaseContract.BooksTable.AUTHOR, BOOK_AUTHOR);
        booksValues.put(DatabaseContract.BooksTable.DESCRIPTION, BOOK_DESCRIPTION);

        return booksValues;
    }

    static void validateBooksDbRecord(String error, Cursor cursor){
        assertEquals(cursor.getString(cursor.getColumnIndex(DatabaseContract.BooksTable.TITLE)), BOOK_TITLE);
        assertEquals(cursor.getString(cursor.getColumnIndex(DatabaseContract.BooksTable.AUTHOR)), BOOK_AUTHOR);
        assertEquals(cursor.getString(cursor.getColumnIndex(DatabaseContract.BooksTable.DESCRIPTION)), BOOK_DESCRIPTION);
    }

}
