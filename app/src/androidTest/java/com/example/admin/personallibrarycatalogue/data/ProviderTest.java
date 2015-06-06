package com.example.admin.personallibrarycatalogue.data;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.test.AndroidTestCase;

import com.example.admin.personallibrarycatalogue.Util;

/**
 * Created by Mikhail Valuyskiy on 04.06.2015.
 */
public class ProviderTest extends AndroidTestCase {
    public static final String LOG_TAG = ProviderTest.class.getSimpleName();

    public void deleteAllRecordsFromProvider(){
        mContext.getContentResolver().delete(
                DatabaseContract.BooksTable.CONTENT_URI,
                null,
                null
        );

        Cursor cursor = mContext.getContentResolver().query(
                DatabaseContract.BooksTable.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertEquals("Error: Records not deleted from Weather table during delete",0, cursor.getCount());
        cursor.close();
    }

    public void deleteAllRecordsFromDatabase(){
        LibraryDatabaseHelper helper = new LibraryDatabaseHelper(mContext);
        SQLiteDatabase database = helper.getWritableDatabase();

        database.delete(DatabaseContract.BooksTable.TABLE_NAME, null, null);
        database.close();
    }

    public void deleteAllRecords(){
        deleteAllRecordsFromDatabase();
    }

  //  @Override
    protected void SetUp() throws Exception{
        super.setUp();
        deleteAllRecords();
    }

    public void testProviderRegistry(){
        PackageManager packageManager = mContext.getPackageManager();

        ComponentName componentName = new ComponentName(mContext.getPackageName(),BooksProvider.class.getName());

        try {
            ProviderInfo providerInfo = packageManager.getProviderInfo(componentName, 0);

            assertEquals("Error:BooksProvider registered with authority: " + providerInfo.authority + " instead of authority: " + DatabaseContract.CONTENT_AUTHORITY,
                    providerInfo.authority, DatabaseContract.CONTENT_AUTHORITY);
        }catch (PackageManager.NameNotFoundException e){
            assertTrue("Error: BooksProovicer not registered at "+ mContext.getPackageName(), false);
        }
    }

    public void testGetType(){
        // content://com.example.admin.personallibrarycatalogue.app/books
        String type = mContext.getContentResolver().getType(DatabaseContract.BooksTable.CONTENT_URI);
        // vnd.android.cursor.dir/com.example.admin.personallibrarycatalogue.app/books
        assertEquals("Error: the CONTENT_URI should return BooksTable.CONTENT_TYPE", DatabaseContract.BooksTable.CONTENT_TYPE,type);

    }

    public void testBasicWeatherQuery(){
        // insert test records into the database
        LibraryDatabaseHelper helper = new LibraryDatabaseHelper(mContext);
        SQLiteDatabase database = helper.getWritableDatabase();

        ContentValues testValues = Utily.createBooksValues();

        long id = database.insert(DatabaseContract.BooksTable.TABLE_NAME,null,testValues);

        assertTrue("Unable to insert ", id != -1);
        database.close();

        Cursor cursor = mContext.getContentResolver().query(
                DatabaseContract.BooksTable.CONTENT_URI,
                null,
                null,
                null,
                null
        );
    }


}
