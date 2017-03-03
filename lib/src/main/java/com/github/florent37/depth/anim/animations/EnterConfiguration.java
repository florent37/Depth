package com.github.florent37.depth.anim.animations;

import android.support.annotation.FloatRange;

/**
 * Created by florentchampigny on 03/03/2017.
 */

public class EnterConfiguration {

    private long duration = 1400l;

    private float initialRotationX = 60f;
    private float initialRotationZ = -50f;
    private float initialScale = 0.5f;
    private float initialElevation = 30f;

    @FloatRange(from = -1f, to = 1f)
    private float initialXPercent = -1f; //left of screen

    @FloatRange(from = -1f, to = 1f)
    private float initialYPercent = 1f; //bottom of screen


    public EnterConfiguration setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    long getDuration() {
        return duration;
    }

    float getInitialRotationX() {
        return initialRotationX;
    }

    public EnterConfiguration setInitialRotationX(float initialRotationX) {
        this.initialRotationX = initialRotationX;
        return this;
    }

    float getInitialRotationZ() {
        return initialRotationZ;
    }

    public EnterConfiguration setInitialRotationZ(float initialRotationZ) {
        this.initialRotationZ = initialRotationZ;
        return this;
    }

    float getInitialScale() {
        return initialScale;
    }

    public EnterConfiguration setInitialScale(float initialScale) {
        this.initialScale = initialScale;
        return this;
    }

    float getInitialElevation() {
        return initialElevation;
    }

    public EnterConfiguration setInitialElevation(float initialElevation) {
        this.initialElevation = initialElevation;
        return this;
    }

    float getInitialXPercent() {
        return initialXPercent;
    }

    public EnterConfiguration setInitialXPercent(float initialXPercent) {
        if (-1f <= initialXPercent && initialXPercent <= 1f) {
            this.initialXPercent = initialXPercent;
        }
        return this;
    }

    float getInitialYPercent() {
        return initialYPercent;
    }

    public EnterConfiguration setInitialYPercent(float initialYPercent) {
        if (-1f <= initialYPercent && initialYPercent <= 1f) {
            this.initialYPercent = initialYPercent;
        }
        return this;
    }
}