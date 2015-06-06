package com.example.admin.personallibrarycatalogue.data;

/**
 * Created by Mikhail Valyuskiy on 25.05.2015.
 */

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Describes fields of database for Personal Library Application
 */
public class DatabaseContract {

    public static final String CONTENT_AUTHORITY = "com.example.admin.personallibrarycatalogue.app";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_BOOKS = "books";

    /**
     * Inner class that defines the table contents of the Books table
     */
    public static final class BooksTable implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_BOOKS).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +CONTENT_AUTHORITY + "/" + PATH_BOOKS;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;

        public static Uri buildBookUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }

        public static final String TABLE_NAME = "books";
        public static final String TITLE = "title";
        public static final String AUTHOR = "author";
        public static final String COVER = "cover";
        public static final String DESCRIPTION = "description";
        public static final String YEAR = "year";
        public static final String ISBN = "isbn";
    }

    public static int getBookIdFromUri(Uri uri){
        return Integer.parseInt(uri.getPathSegments().get(1));
    }

}
