package com.kistudio.ua.affirmation;

import android.net.Uri;

/**
 * Created by Dim on 24.11.2015.
 */
public class Settings {
    // Database const old --del
    public static final String ROW_NAME = "name";
    public static final String ROW_EMAIL = "email";

    // Database const new
    public static final String ROW_DATE = "date";
    public static final String ROW_TITLE = "title";
    public static final String ROW_TEXT = "text";

    public static final String PROVIDER_NAME = "com.kistudio.ua.affirmation";
    public static final String URI = "content://"+PROVIDER_NAME+"/cte";
    public static final Uri CONTENT_URI = Uri.parse(URI);
    public static final String ID = "_id";
    public static final String LOG_TAG = "CPA";
    public static final String USERS_PATH = "users";
    public static final String URI_USERS = "content://"+PROVIDER_NAME+"/"+USERS_PATH;
    public static final Uri CONTENT_URI_USERS = Uri.parse(URI_USERS);

    public static final String UPDATE_PATH = "update";
    public static final String URI_UPDATE = "content://"+PROVIDER_NAME+"/"+UPDATE_PATH;
    public static final Uri CONTENT_URI_UPDATE = Uri.parse(URI_UPDATE);
}
