package me.iantje.gamebacklog.model;

public enum BacklogStatus {
    WANT(0),
    PLAYING(1),
    STALLED(2),
    DROPPED(3);

    private final int value;
    BacklogStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
