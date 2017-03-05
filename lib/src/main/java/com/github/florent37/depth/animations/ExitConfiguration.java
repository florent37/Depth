package com.github.florent37.depth.animations;

import android.support.annotation.FloatRange;

/**
 * Created by florentchampigny on 03/03/2017.
 */
public class ExitConfiguration {

    @FloatRange(from = -1f, to = 1f)
    private float finalXPercent = 1f; //right of screen

    @FloatRange(from = -1f, to = 1f)
    private float finalYPercent = -1f; //top of screen

    private long duration = 900l;

    public long getDuration() {
        return duration;
    }

    public ExitConfiguration setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    float getFinalXPercent() {
        return finalXPercent;
    }

    public ExitConfiguration setFinalXPercent(float finalXPercent) {
        if (-1f <= finalXPercent && finalXPercent <= 1f) {
            this.finalXPercent = finalXPercent;
        }
        return this;
    }

    float getFinalYPercent() {
        return finalYPercent;
    }

    public ExitConfiguration setFinalYPercent(float finalYPercent) {
        if (-1f <= finalYPercent && finalYPercent <= 1f) {
            this.finalYPercent = finalYPercent;
        }
        return this;
    }
}
