package me.iantje.gamebacklog.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import me.iantje.gamebacklog.model.BacklogItem;

@Database(entities = {BacklogItem.class}, version = 1, exportSchema = false)
public abstract class BacklogRoomDatabase extends RoomDatabase {
    private final static String DATABASE_NAME = "backlog_database";
    public abstract BacklogDao backlogDao();

    private static volatile BacklogRoomDatabase INSTANCE;

    public static BacklogRoomDatabase getInstance(final Context context) {
        if(INSTANCE == null) {
            synchronized (BacklogRoomDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), BacklogRoomDatabase.class,
                            DATABASE_NAME).build();
                }
            }
        }

        return INSTANCE;
    }
}
