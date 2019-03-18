package me.iantje.gamebacklog;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.iantje.gamebacklog.adapter.BacklogAdapter;
import me.iantje.gamebacklog.model.BacklogItem;
import me.iantje.gamebacklog.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final int ADD_REQUESTCODE = 1337;
    public static final String EXTRA_BACKLOG_ITEM = "Backlog_object";

    private MainViewModel mainViewModel;
    private List<BacklogItem> allItems;

    private BacklogAdapter backlogAdapter;

    private RecyclerView backlogRecycler;

    private GestureDetector gestureDetector;

    private ItemTouchHelper.SimpleCallback itemTouchHelperCallback;
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        allItems = new ArrayList<>();
        mainViewModel.getAllItems().observe(this, new Observer<List<BacklogItem>>() {
            @Override
            public void onChanged(@Nullable List<BacklogItem> backlogItems) {
                Log.d(TAG, "MainViewModel observer triggered");
                allItems = backlogItems;

                for (BacklogItem item : backlogItems) {
                    Log.d(TAG, "#" + item.getId().toString() + " | Title: " + item.getTitle());
                }
                
                updateUI();
            }
        });

        backlogAdapter = new BacklogAdapter(allItems);
        backlogRecycler = findViewById(R.id.backlogRecycler);
        backlogRecycler.setAdapter(backlogAdapter);
        backlogRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false));

        backlogRecycler.addOnItemTouchListener(this);

        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                    @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int adapterPosition = viewHolder.getAdapterPosition();

                mainViewModel.delete(allItems.get(adapterPosition));
            }
        };

        itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(backlogRecycler);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddItemActivity.class);

                startActivityForResult(intent, ADD_REQUESTCODE);
            }
        });
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete_item) {
            // delete items
            deleteAll();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK) {
            if(requestCode == ADD_REQUESTCODE) {
                BacklogItem item = data.getParcelableExtra(EXTRA_BACKLOG_ITEM);

                if(item != null) {
                    mainViewModel.insert(item);
                }
            }
        }
    }

    private void updateUI() {
        backlogAdapter.swapList(allItems);
    }

    private void deleteAll() {
        mainViewModel.delete(allItems);
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
        int itemLocation = recyclerView.getChildAdapterPosition(child);

        if(child != null && gestureDetector.onTouchEvent(motionEvent)) {
            BacklogItem item = allItems.get(itemLocation);

            // Go to edit activity, but for now
            Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG).show();
        }

        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }
}
