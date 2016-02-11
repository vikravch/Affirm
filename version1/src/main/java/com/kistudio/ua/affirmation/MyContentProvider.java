package com.kistudio.ua.affirmation;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class MyContentProvider extends ContentProvider {
    static final int URI_CODE = 1;
    static final int URI_USERS = 2;
    static final int URI_UPDATE = 3;

    static final UriMatcher uriMatcher;
    private SQLiteDatabase db;
    static final String DATABASE_NAME = "mydb";

    static final String DATABASE_NAME_AFFIRM = "mydb";

    static final String TABLE_NAME = "names";
    static final String TABLE_NAME_AFFIRMATION = "affirm";
    static final int DB_VERSION = 1;
    static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Settings.ROW_NAME + " TEXT NOT NULL,"
            + Settings.ROW_EMAIL +  " TEXT NOT NULL);";

/*
    static final String CREATE_TABLE_AFF = "CREATE TABLE "
            + TABLE_NAME_AFFIRMATION + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Settings.ROW_DATE + " DATE NOT NULL,"
            + Settings.ROW_TITLE +  " TEXT NOT NULL, "
            + Settings.ROW_TEXT +  " TEXT NOT NULL" +
            ");";*/

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Settings.PROVIDER_NAME,"cte",URI_CODE);
        uriMatcher.addURI(Settings.PROVIDER_NAME, Settings.USERS_PATH, URI_USERS);
        uriMatcher.addURI(Settings.PROVIDER_NAME, Settings.UPDATE_PATH, URI_UPDATE);
    }

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = db.insert(TABLE_NAME,"",values);
        if (rowID > 0){
            Uri _uri = ContentUris.withAppendedId(Settings.CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri,null);
            return _uri;
        }else try {
            throw new SQLException("Failed "+ uri);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DBHelperAffirmation dba = new DBHelperAffirmation(context);
        db = dba.getWritableDatabase();
        return db!=null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.d(Settings.LOG_TAG,"Content query");

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        Cursor c = null;
        qb.setTables(TABLE_NAME_AFFIRMATION);
        c = qb.query(db,projection,selection,selectionArgs,null,null,sortOrder);

/*
        switch (uriMatcher.match(uri)){
            case URI_CODE:
            case URI_USERS:
                //c.setNotificationUri(getContext().getContentResolver(),uri);
                c = getFromSiteUsers("http://cityfinder.esy.es/getuser.php");
                break;
            case URI_UPDATE:
                qb.setTables(TABLE_NAME_AFFIRMATION);
                c = qb.query(db,projection,selection,selectionArgs,null,null,sortOrder);
                c.setNotificationUri(getContext().getContentResolver(),uri);
            default:
                return null;
        }
*/
        return c;

    }

    private Cursor getFromSiteUsers(String s) {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("name", "Vitaly");
        data.put("password", "mypass");
        AsyncHttpPost asyncHttpPost = new AsyncHttpPost(data);
        String res= "";
        try {
            res = asyncHttpPost.execute(s).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d("AsyncTask", res);

        String[] arrayRes = res.split(";");
        MatrixCursor cursor = new MatrixCursor(new String[]{Settings.ID,Settings.ROW_NAME,Settings.ROW_EMAIL});
        cursor.moveToFirst();

        for (int i = 1; i <= arrayRes.length; i++) {
            cursor.addRow(new Object[]{i,arrayRes[i-1],"email"});
        }

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    private class DBHelperAffirmation extends SQLiteOpenHelper {

        static final String CREATE_TABLE = "CREATE TABLE "
                + TABLE_NAME_AFFIRMATION + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Settings.ROW_DATE + " DATE NOT NULL,"
                + Settings.ROW_TITLE +  " TEXT NOT NULL, "
                + Settings.ROW_TEXT +  " TEXT NOT NULL" +
                ");";



        public DBHelperAffirmation(Context context) {
            super(context, DATABASE_NAME_AFFIRM, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }


}
