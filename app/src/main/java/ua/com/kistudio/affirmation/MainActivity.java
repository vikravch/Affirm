package ua.com.kistudio.affirmation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<Object> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public android.content.Loader<Object> onCreateLoader(int id, Bundle args) {
        return
    }

    @Override
    public void onLoadFinished(android.content.Loader<Object> loader, Object data) {

    }

    @Override
    public void onLoaderReset(android.content.Loader<Object> loader) {

    }

}
