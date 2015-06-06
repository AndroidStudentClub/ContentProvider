package com.example.admin.personallibrarycatalogue.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Intent;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.ContactsContract;
import android.net.Uri;


/**
 * Created by Mikhail Valuyskiy on 03.06.2015.
 */
public class BooksProvider extends ContentProvider {

    private static final UriMatcher uriMatcher_ = buildUriMatcher();
    private LibraryDatabaseHelper helper_;

    static final int BOOKS = 100;
    static final int BOOK_WITH_ID = 101;

    private static final SQLiteQueryBuilder queryBuilder_ = new SQLiteQueryBuilder();

    private static final String bookSelectionQuery = DatabaseContract.BooksTable.TABLE_NAME + "." + DatabaseContract.BooksTable._ID + " = ?";


    private Cursor getBookById(Uri uri,String[] projection, String sortOrder){
        int bookId = DatabaseContract.getBookIdFromUri(uri);
        queryBuilder_.setTables(DatabaseContract.BooksTable.TABLE_NAME);

        return queryBuilder_.query(helper_.getReadableDatabase(),
                projection,
                bookSelectionQuery,
                new String[]{Integer.toString(bookId)},
                null,
                null,
                sortOrder
        );
    }

    static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DatabaseContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, DatabaseContract.PATH_BOOKS, BOOKS);
        matcher.addURI(authority, DatabaseContract.PATH_BOOKS +"/#", BOOK_WITH_ID);
        return matcher;
    }

    @Override
    public boolean onCreate(){
        helper_ = new LibraryDatabaseHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri){
        // Uri matcher is used to determine what kind of Uri this is
        final int match = uriMatcher_.match(uri);

        switch (match){
            case BOOKS:
                return DatabaseContract.BooksTable.CONTENT_TYPE;
            case BOOK_WITH_ID:
                return DatabaseContract.BooksTable.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder){
        Cursor returnedCursor;

        switch (uriMatcher_.match(uri)) {
            //"books/"
            case BOOKS:
                returnedCursor = helper_.getReadableDatabase().query(
                        DatabaseContract.BooksTable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs, null,
                        null, sortOrder
                );
                break;

            case BOOK_WITH_ID:
                returnedCursor = getBookById(uri, projection, sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        returnedCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return returnedCursor;
        }

    @Override
    public Uri insert(Uri uri, ContentValues values){
        final SQLiteDatabase db = helper_.getWritableDatabase();
        final int match = uriMatcher_.match(uri);
        Uri resultUri;

        switch (match){
            case BOOKS:
                long id = db.insert(DatabaseContract.BooksTable.TABLE_NAME,null,values);
                if (id > 0) {
                    resultUri = DatabaseContract.BooksTable.buildBookUri(id);
                } else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;

            default:
                throw new android.database.SQLException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        db.close();
        return resultUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs){
        final SQLiteDatabase db = helper_.getWritableDatabase();
        final int match = uriMatcher_.match(uri);
        int rowDeleted;

        switch (match){
            case BOOK_WITH_ID:
                String id = uri.getLastPathSegment();
                selection = DatabaseContract.BooksTable._ID + " = " + id;
                rowDeleted = db.delete(DatabaseContract.BooksTable.TABLE_NAME,selection,selectionArgs);
                break;

              default:
                  throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        final SQLiteDatabase db = helper_.getWritableDatabase();
        final int match =uriMatcher_.match(uri);
        int rowsUpdated;
        switch (match){
            case BOOK_WITH_ID:
                String id = uri.getLastPathSegment();
                selection = DatabaseContract.BooksTable._ID + " = " + id;
                rowsUpdated = db.update(DatabaseContract.BooksTable.TABLE_NAME, values,selection,selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsUpdated;
    }


}
