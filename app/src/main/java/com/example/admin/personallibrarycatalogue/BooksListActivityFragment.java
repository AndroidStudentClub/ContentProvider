package com.example.admin.personallibrarycatalogue;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.admin.personallibrarycatalogue.data.Book;
import com.example.admin.personallibrarycatalogue.data.DatabaseContract;
import com.example.admin.personallibrarycatalogue.data.LibraryDatabaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A placeholder fragment containing a simple view.
 */
public class BooksListActivityFragment extends Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LIBRARY_LOADER = 0;
    private final static String ID = "id";
    private ListView listView_;
    private BooksListAdapter booksListAdapter_;
    private LibraryDatabaseHelper helper_;

    private static final String[] BOOK_COLUMNS = {

            DatabaseContract.BooksTable.TABLE_NAME + "." + DatabaseContract.BooksTable._ID,
            DatabaseContract.BooksTable.TITLE,
            DatabaseContract.BooksTable.AUTHOR,
            DatabaseContract.BooksTable.DESCRIPTION,
            DatabaseContract.BooksTable.COVER,
            DatabaseContract.BooksTable.YEAR,
            DatabaseContract.BooksTable.ISBN
    };

    public BooksListActivityFragment() {
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(
                getActivity(),
                DatabaseContract.BooksTable.CONTENT_URI,
                BOOK_COLUMNS,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        booksListAdapter_.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        booksListAdapter_.swapCursor(null);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_books_list, container, false);


        listView_ = (ListView) rootView.findViewById(R.id.books_list_view);
        booksListAdapter_ = new BooksListAdapter(getActivity(), null, 0);
        listView_.setAdapter(booksListAdapter_);

        // Set ContextMenu for listView
        registerForContextMenu(listView_);

        listView_.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) listView_.getItemAtPosition(position);
                changeBook(cursor);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(LIBRARY_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        menu.setHeaderTitle(R.string.Menu);
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Cursor cursor = (Cursor) listView_.getItemAtPosition(info.position);

        switch (item.getItemId()) {
            case R.id.edit_book:
                changeBook(cursor);
                break;

            case R.id.delete_book:
                deleteBook(cursor);
                booksListAdapter_.notifyDataSetChanged();
                break;

            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    /**
     * Set up activity for changing some information about book (title, author etc.)
     */
    public void changeBook(Cursor cursor) {
        Book book = LibraryDatabaseHelper.getBook(cursor);
        int id = book.getId();

        Intent intent = new Intent();
        intent.setClass(getActivity(), AddBookActivity.class);
        intent.putExtra(ID, id);
        startActivity(intent);
    }

    public void deleteBook(Cursor cursor) {
        Book book = LibraryDatabaseHelper.getBook(cursor);
        int id = book.getId();

        Uri bookWithIdUri = DatabaseContract.BooksTable.buildBookUri(id);
        // Delete from database with help of Content Provider
        getActivity().getContentResolver().delete(bookWithIdUri, null, null);
    }
}
