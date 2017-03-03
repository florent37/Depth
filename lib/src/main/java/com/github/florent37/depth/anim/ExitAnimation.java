package com.github.florent37.depth.anim;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.support.annotation.FloatRange;
import android.view.View;

import no.agens.depth.lib.DepthLayout;
import no.agens.depth.lib.tween.interpolators.ExpoIn;

/**
 * Created by florentchampigny on 02/03/2017.
 */

public class ExitAnimation extends DepthAnimation<ExitAnimation>{

    @FloatRange(from= -1f, to= 1f)
    private float finalXPercent = 1f; //right of screen

    @FloatRange(from= -1f, to= 1f)
    private float finalYPercent = -1f; //top of screen

    public ExitAnimation() {
        this.totalDuration = 900l;
    }

    public ExitAnimation setFinalXPercent(@FloatRange(from= -1f, to= 1f) float finalXPercent) {
        this.finalXPercent = finalXPercent;
        return this;
    }

    public ExitAnimation setFinalYPercent(@FloatRange(from= -1f, to= 1f) float finalYPercent) {
        this.finalYPercent = finalYPercent;
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
        translationY2.setDuration(totalDuration);
        //translationY2.setInterpolator(new AccelerateInterpolator());
        translationY2.setInterpolator(interpolator);
        translationY2.addListener(listener);
        translationY2.start();

        final ObjectAnimator translationX2 = ObjectAnimator.ofFloat(target, View.TRANSLATION_X, finalTranslationX);
        translationX2.setDuration(totalDuration);
        translationX2.setInterpolator(interpolator);
        translationX2.start();
    }

}
