package com.kistudio.ua.affirmation;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {

    CursorLoader cursorLoader;
    ListView LV;
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportLoaderManager().initLoader(1, null, this);
        ((Button) findViewById(R.id.btnUpdate)).setOnClickListener(this);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        switch (id){
            case 1:
                Log.d(Settings.LOG_TAG,"Loader begin");
                cursorLoader = new CursorLoader(this,Settings.CONTENT_URI_UPDATE,null,null,null,null);
                break;
            default:
        }
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        LV = (ListView)findViewById(R.id.main_list);
        adapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_2,data,new String[]{Settings.ROW_TITLE,Settings.ROW_TEXT},new int[]{android.R.id.text1,android.R.id.text2},adapter.NO_SELECTION);
        LV.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {

    }


    /* Button listener */
    @Override
    public void onClick(View v) {
        getSupportLoaderManager().initLoader(1, null, this);
//        Toast.makeText(this,"Click",Toast.LENGTH_SHORT).show();
    }
}
