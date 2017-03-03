package com.github.florent37.depth.anim;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.view.View;

import no.agens.depth.lib.DepthLayout;
import no.agens.depth.lib.tween.interpolators.ExpoIn;

/**
 * Created by florentchampigny on 02/03/2017.
 */

public class ExitAnimation {

    private DepthLayout depthLayout;
    private Animator.AnimatorListener listener;

    private long duration;

    private float finalXPercent = 1f; //right of screen
    private float finalYPercent = -1f; //top of screen

    public ExitAnimation() {
        this.duration = 900l;
    }

    public ExitAnimation setDepthLayout(DepthLayout depthLayout) {
        this.depthLayout = depthLayout;
        return this;
    }

    public ExitAnimation setListener(Animator.AnimatorListener listener) {
        this.listener = listener;
        return this;
    }

    public ExitAnimation setFinalXPercent(float finalXPercent) {
        this.finalXPercent = finalXPercent;
        return this;
    }

    public ExitAnimation setFinalYPercent(float finalYPercent) {
        this.finalYPercent = finalYPercent;
        return this;
    }

    public ExitAnimation setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    //call after reduce
    public void start() {
        final float finalDestinationY = finalYPercent * depthLayout.getResources().getDisplayMetrics().heightPixels;
        final float finalDestinationX = finalXPercent * depthLayout.getResources().getDisplayMetrics().widthPixels;

        exitAnim(depthLayout, finalDestinationY, finalDestinationX);
    }

    private void exitAnim(DepthLayout target, float finalTranslationY, float finalTranslationX) {
        final TimeInterpolator interpolator = new ExpoIn();

        final ObjectAnimator translationY2 = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, finalTranslationY);
        translationY2.setDuration(duration);
        //translationY2.setInterpolator(new AccelerateInterpolator());
        translationY2.setInterpolator(interpolator);
        translationY2.addListener(listener);
        translationY2.start();

        final ObjectAnimator translationX2 = ObjectAnimator.ofFloat(target, View.TRANSLATION_X, finalTranslationX);
        translationX2.setDuration(duration);
        translationX2.setInterpolator(interpolator);
        translationX2.start();
    }

}
