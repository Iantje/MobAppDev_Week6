package me.iantje.gamebacklog;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import me.iantje.gamebacklog.model.BacklogItem;
import me.iantje.gamebacklog.model.BacklogStatus;

public class AddItemActivity extends AppCompatActivity {

    private static final String TAG = AddItemActivity.class.getSimpleName();

    private TextView titleInput;
    private TextView platformInput;
    private Spinner statusInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titleInput = findViewById(R.id.gameTitle);
        platformInput = findViewById(R.id.gamePlatform);
        statusInput = findViewById(R.id.statusSpinner);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, titleInput.getText() + " | " + titleInput.toString());
                Log.d(TAG, platformInput.getText() + " | " + platformInput.toString());

                Intent intent = new Intent();
                // In java, enums cannot be cast directly to int or vice versa, so the "best" solution is to take
                // all the values in an array, and select the enum value from there... seems legit.
                intent.putExtra(MainActivity.EXTRA_BACKLOG_ITEM,
                        new BacklogItem(titleInput.getText().toString(), platformInput.getText().toString(),
                                BacklogStatus.values()[(int)statusInput.getSelectedItemId()]));

                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setResult(Activity.RESULT_CANCELED, null);
        finish();

        return true;
    }

}
