package me.iantje.gamebacklog;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

import me.iantje.gamebacklog.converter.BacklogStatusConverter;
import me.iantje.gamebacklog.model.BacklogItem;

public class UpdateActivity extends AppCompatActivity {

    private TextView editTitle, editPlatform;
    private Spinner editStatus;

    private BacklogItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTitle = findViewById(R.id.editGameTitle);
        editPlatform = findViewById(R.id.editGamePlatform);
        editStatus = findViewById(R.id.editStatusSpinner);

        if(getIntent().getExtras() == null) {
            finishActivity(RESULT_CANCELED);
        } else {
            item = getIntent().getExtras().getParcelable(MainActivity.EXTRA_BACKLOG_ITEM);

            assert item != null;
            editTitle.setText(item.getTitle());
            editPlatform.setText(item.getPlatform());
            editStatus.setSelection(item.getStatus().getValue());
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setTitle(editTitle.getText().toString());
                item.setPlatform(editPlatform.getText().toString());
                item.setStatus(BacklogStatusConverter.toBacklogStatus(editStatus.getSelectedItemPosition()));
                item.setLastUpdated(Calendar.getInstance().getTimeInMillis());

                // Intent back
                Intent intent = new Intent();
                intent.putExtra(MainActivity.EXTRA_BACKLOG_ITEM, item);

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
