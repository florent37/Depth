package com.github.florent37.depth.anim.animations;

/**
 * Created by florentchampigny on 03/03/2017.
 */
public class ReduceConfiguration {

    private float scale = 0.5f;
    private float translationX = 0;
    private float translationY = 0;
    private float rotationX = 60f;
    private float rotationZ = -50f;
    private float elevation = 30f;

    private long duration = 1600l;

    public long getDuration() {
        return duration;
    }

    public ReduceConfiguration setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    public ReduceConfiguration setScale(float scale) {
        this.scale = scale;
        return this;
    }

    public float getScale() {
        return scale;
    }

    public float getTranslationX() {
        return translationX;
    }

    public ReduceConfiguration setTranslationX(float translationX) {
        this.translationX = translationX;
        return this;
    }

    float getTranslationY() {
        return translationY;
    }

    public ReduceConfiguration setTranslationY(float translationY) {
        this.translationY = translationY;
        return this;
    }

    float getRotationX() {
        return rotationX;
    }

    public ReduceConfiguration setRotationX(float rotationX) {
        this.rotationX = rotationX;
        return this;
    }

    float getRotationZ() {
        return rotationZ;
    }

    public ReduceConfiguration setRotationZ(float rotationZ) {
        this.rotationZ = rotationZ;
        return this;
    }

    public float getElevation() {
        return elevation;
    }

    public ReduceConfiguration setElevation(float elevation) {
        this.elevation = elevation;
        return this;
    }
}
