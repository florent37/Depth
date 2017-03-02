package com.github.florent37.depth.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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

    public ExitAnimation setDepthLayout(DepthLayout depthLayout) {
        this.depthLayout = depthLayout;
        return this;
    }

    public void start(Animator.AnimatorListener listener) {
        continueOutToRight(depthLayout, 0, 20, listener);
    }

    private void continueOutToRight(DepthLayout target, float moveY, int startDelay, Animator.AnimatorListener listener) {
        final TimeInterpolator interpolator = new ExpoIn();
        final int duration = 900;

        final ObjectAnimator translationY2 = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, -moveY * target.getResources().getDisplayMetrics().density, -target.getResources().getDisplayMetrics().heightPixels);
        translationY2.setDuration(duration);
        //translationY2.setInterpolator(new AccelerateInterpolator());
        translationY2.setInterpolator(interpolator);
        translationY2.setStartDelay(startDelay);
        translationY2.addListener(listener);
        translationY2.start();

        final ObjectAnimator translationX2 = ObjectAnimator.ofFloat(target, View.TRANSLATION_X, target.getTranslationX(), target.getResources().getDisplayMetrics().widthPixels);
        translationX2.setDuration(duration);
        translationX2.setInterpolator(interpolator);
        translationX2.setStartDelay(startDelay);
        translationX2.start();
    }

}
