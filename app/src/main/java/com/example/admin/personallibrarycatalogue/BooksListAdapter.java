package com.example.admin.personallibrarycatalogue;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.personallibrarycatalogue.data.Book;
import com.example.admin.personallibrarycatalogue.data.DatabaseContract;

import java.util.List;

/**
 * Created by Admin on 27.05.2015.
 */
public class BooksListAdapter extends CursorAdapter {

    private static class ViewHolder {
        TextView authorTextView_;
        TextView titleTextView_;
        ImageView coverImageView_;

        ViewHolder(View view) {
            this.authorTextView_ = (TextView) view.findViewById(R.id.list_item_author);
            this.titleTextView_ = (TextView) view.findViewById(R.id.list_item_title);
            this.coverImageView_ = (ImageView) view.findViewById(R.id.list_item_image_view);
        }
    }

    public BooksListAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        String title = cursor.getString(cursor.getColumnIndex(DatabaseContract.BooksTable.TITLE));
        viewHolder.titleTextView_.setText(title);

        String author = cursor.getString(cursor.getColumnIndex(DatabaseContract.BooksTable.AUTHOR));
        viewHolder.authorTextView_.setText(author);

        byte[] cover = cursor.getBlob(cursor.getColumnIndex(DatabaseContract.BooksTable.COVER));
        viewHolder.coverImageView_.setImageBitmap(Util.getBitmapFromBytes(cover));
    }
}
