package com.github.florent37.depth.anim.animations;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.view.View;

import no.agens.depth.lib.tween.interpolators.ExpoIn;

/**
 * Created by florentchampigny on 02/03/2017.
 */

public class ExitAnimation extends DepthAnimation<ExitAnimation> {

    private ExitConfiguration exitConfiguration = new ExitConfiguration();

    public ExitAnimation setExitConfiguration(ExitConfiguration exitConfiguration) {
        if (exitConfiguration != null) {
            this.exitConfiguration = exitConfiguration;
        }
        return this;
    }

    //call after reduce
    @Override
    public void start() {
        exitAnim();
    }

    private void exitAnim() {
        final View target = this.depthLayout;

        final TimeInterpolator interpolator = new ExpoIn();

        final float finalTranslationY = exitConfiguration.getFinalYPercent() * depthLayout.getResources().getDisplayMetrics().heightPixels;
        final float finalTranslationX = exitConfiguration.getFinalXPercent() * depthLayout.getResources().getDisplayMetrics().widthPixels;

        final long totalDuration = exitConfiguration.getDuration();

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
