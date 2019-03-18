package me.iantje.gamebacklog.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import me.iantje.gamebacklog.model.BacklogItem;

@Dao
public interface BacklogDao {

    @Query("SELECT * FROM backlog_items") LiveData<List<BacklogItem>> getAllItems();

    @Delete void deleteItem(BacklogItem item);
    @Delete void deleteItems(List<BacklogItem> items);
    @Insert void insertItem(BacklogItem item);
    @Insert void insertItems(List<BacklogItem> items);
    @Update void updateItem(BacklogItem item);
}
