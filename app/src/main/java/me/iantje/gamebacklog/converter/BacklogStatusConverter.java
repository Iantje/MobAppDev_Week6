package me.iantje.gamebacklog.converter;

import android.arch.persistence.room.TypeConverter;

import me.iantje.gamebacklog.model.BacklogStatus;

public class BacklogStatusConverter {
    @TypeConverter
    public static BacklogStatus toBacklogStatus(int statusInt) {
        return BacklogStatus.values()[statusInt];
    }

    @TypeConverter
    public static int fromBacklogStatus(BacklogStatus status) {
        return status.getValue();
    }
}
