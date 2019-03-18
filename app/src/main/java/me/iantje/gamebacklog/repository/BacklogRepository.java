package me.iantje.gamebacklog.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import me.iantje.gamebacklog.database.BacklogDao;
import me.iantje.gamebacklog.database.BacklogRoomDatabase;
import me.iantje.gamebacklog.model.BacklogItem;

public class BacklogRepository {
    private BacklogRoomDatabase backlogDatabase;
    private BacklogDao backlogDao;
    private LiveData<List<BacklogItem>> allItems;
    private Executor executor = Executors.newSingleThreadExecutor();

    public BacklogRepository(Context context) {
        backlogDatabase = BacklogRoomDatabase.getInstance(context);
        backlogDao = backlogDatabase.backlogDao();
        allItems = backlogDao.getAllItems();
    }

    public LiveData<List<BacklogItem>> getAllItems() {
        return allItems;
    }

    public void insert(final BacklogItem item) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                backlogDatabase.backlogDao().insertItem(item);
            }
        });
    }

    public void insert(final List<BacklogItem> items) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                backlogDatabase.backlogDao().insertItems(items);
            }
        });
    }

    public void delete(final BacklogItem item) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                backlogDatabase.backlogDao().deleteItem(item);
            }
        });
    }

    public void delete(final List<BacklogItem> items) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                backlogDatabase.backlogDao().deleteItems(items);
            }
        });
    }

    public void update(final BacklogItem item) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                backlogDatabase.backlogDao().updateItem(item);
            }
        });
    }
}
