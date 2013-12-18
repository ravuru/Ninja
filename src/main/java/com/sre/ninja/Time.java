package com.sre.ninja;

import java.util.concurrent.TimeUnit;

public class Time {

    private long start;
    private long end;

    public void start() {
        this.start = System.nanoTime();
    }

    public void end() {
        this.end = System.nanoTime();
    }

    public long elapsedTimeSeconds() {
        return TimeUnit.SECONDS.convert((end - start), TimeUnit.NANOSECONDS);
    }
}
