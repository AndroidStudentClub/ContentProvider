package com.example.admin.personallibrarycatalogue;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    SharedPreferences preferences = null;
    private static final String APP_NAME = "com.example.admin.personallibrarycatalogue";
    private static final String FIRSTRUN = "firstrun";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences(APP_NAME, MODE_PRIVATE);

        // If app was launched first time show user sugestion to add book
        if (preferences.getBoolean(FIRSTRUN, true)) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, new FirstLaunchFragment())
                    .commit();

            preferences.edit().putBoolean(FIRSTRUN, false).commit();

        } else {
            Intent intent = new Intent(this, BooksListActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()){
            case R.id.add_book_action:
                Intent intent = new Intent(this,AddBookActivity.class);
                startActivity(intent);
             default:
                 return super.onOptionsItemSelected(item);
        }
    }
}
