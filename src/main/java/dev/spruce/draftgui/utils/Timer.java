package dev.spruce.draftgui.utils;

public class Timer {

    private long startTime;
    private long duration;

    public Timer(long duration) {
        this.duration = duration;
        this.startTime = System.currentTimeMillis();
    }

    public void reset() {
        this.startTime = System.currentTimeMillis();
    }

    public boolean hasElapsed() {
        return System.currentTimeMillis() - startTime >= duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
