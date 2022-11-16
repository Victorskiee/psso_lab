package com.company;

public class Timer {
    public long startTime = 0;
    public long stopTime = 0;

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public void stop() {
        stopTime = System.currentTimeMillis();
    }

    public long getTime() {
        long connectionTime = (stopTime - startTime)/1000;
        return connectionTime;
    }
}
