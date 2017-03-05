package com.github.florent37.depth.animations;

/**
 * Created by florentchampigny on 03/03/2017.
 */
public class RevertConfiguration {

    private long duration = 1100l;

    private float elevation = 30f;
    private float rotationX = 0f;
    private float rotationZ = 0f;
    private float translationY = 0f;
    private float translationX = 0;
    private float scale = 1f;

    public long getDuration() {
        return duration;
    }

    public RevertConfiguration setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    public RevertConfiguration setScale(float scale) {
        this.scale = scale;
        return this;
    }

    public float getScale() {
        return scale;
    }

    float getTranslationX() {
        return translationX;
    }

    public RevertConfiguration setTranslationX(float translationX) {
        this.translationX = translationX;
        return this;
    }

    float getTranslationY() {
        return translationY;
    }

    public RevertConfiguration setTranslationY(float translationY) {
        this.translationY = translationY;
        return this;
    }

    float getRotationX() {
        return rotationX;
    }

    public RevertConfiguration setRotationX(float rotationX) {
        this.rotationX = rotationX;
        return this;
    }

    float getRotationZ() {
        return rotationZ;
    }

    public RevertConfiguration setRotationZ(float rotationZ) {
        this.rotationZ = rotationZ;
        return this;
    }

    float getElevation() {
        return elevation;
    }

    public RevertConfiguration setElevation(float elevation) {
        this.elevation = elevation;
        return this;
    }


}
