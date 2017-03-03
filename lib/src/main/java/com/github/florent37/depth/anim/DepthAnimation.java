package com.github.florent37.depth.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

import no.agens.depth.lib.DepthLayout;

/**
 * Created by florentchampigny on 03/03/2017.
 */

public abstract class DepthAnimation<THIS> {

    protected DepthLayout depthLayout;

    protected Animator.AnimatorListener listener;
    protected long totalDuration;

    protected float finalScale;
    protected float finalTranslationX;
    protected float finalTranslationY;
    protected float finalRotationX;
    protected float finalRotationZ;
    protected float finalElevation;

    public THIS setDepthLayout(DepthLayout depthLayout) {
        this.depthLayout = depthLayout;
        this.listener = new AnimatorListenerAdapter() {};
        return (THIS) this;
    }

    public THIS setListener(Animator.AnimatorListener listener) {
        this.listener = listener;
        return (THIS)this;
    }

    public THIS setFinalScale(float finalScale) {
        this.finalScale = finalScale;
        return (THIS)this;
    }

    public THIS setFinalTranslationX(float finalTranslationX) {
        this.finalTranslationX = finalTranslationX;
        return (THIS)this;
    }

    public THIS setFinalTranslationY(float finalTranslationY) {
        this.finalTranslationY = finalTranslationY;
        return (THIS)this;
    }

    public THIS setFinalElevation(float finalElevation) {
        this.finalElevation = finalElevation;
        return (THIS)this;
    }

    public THIS setDuration(long totalDuration) {
        this.totalDuration = totalDuration;
        return (THIS)this;
    }

    public float getFinalRotationX() {
        return finalRotationX;
    }

    public THIS setFinalRotationX(float finalRotationX) {
        this.finalRotationX = finalRotationX;
        return (THIS)this;
    }

    public float getFinalRotationZ() {
        return finalRotationZ;
    }

    public THIS setFinalRotationZ(float finalRotationZ) {
        this.finalRotationZ = finalRotationZ;
        return (THIS)this;
    }

    public abstract void start();

}
