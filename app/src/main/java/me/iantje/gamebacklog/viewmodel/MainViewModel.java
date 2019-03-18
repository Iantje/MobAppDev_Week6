package me.iantje.gamebacklog.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import me.iantje.gamebacklog.model.BacklogItem;
import me.iantje.gamebacklog.repository.BacklogRepository;

public class MainViewModel extends AndroidViewModel {

    private BacklogRepository backlogRepository;
    private LiveData<List<BacklogItem>> allItems;

    public MainViewModel(@NonNull Application application) {
        super(application);

        backlogRepository = new BacklogRepository(application.getApplicationContext());
        allItems = backlogRepository.getAllItems();
    }

    public LiveData<List<BacklogItem>> getAllItems() {
        return allItems;
    }

    public void insert(BacklogItem item) {
        backlogRepository.insert(item);
    }

    public void insert(List<BacklogItem> items) {
        backlogRepository.insert(items);
    }

    public void delete(BacklogItem item) {
        backlogRepository.delete(item);
    }

    public void delete(List<BacklogItem> items) {
        backlogRepository.delete(items);
    }

    public void update(BacklogItem item) {
        backlogRepository.update(item);
    }
}
