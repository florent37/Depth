package com.github.florent37.depth.animations;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.view.View;

import com.github.florent37.depth.DepthRelativeLayout;

import com.github.florent37.depth.lib.tween.interpolators.ExpoIn;

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

    @Override
    public void prepareAnimators(DepthRelativeLayout target, int index) {
        final TimeInterpolator interpolator = new ExpoIn();

        final float finalTranslationY = exitConfiguration.getFinalYPercent() * target.getResources().getDisplayMetrics().heightPixels;
        final float finalTranslationX = exitConfiguration.getFinalXPercent() * target.getResources().getDisplayMetrics().widthPixels;

        final long totalDuration = exitConfiguration.getDuration();

        final ObjectAnimator translationY2 = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, finalTranslationY);
        translationY2.setDuration(totalDuration);
        //translationY2.setInterpolator(new AccelerateInterpolator());
        translationY2.setInterpolator(interpolator);
        attachListener(translationY2);
        add(translationY2);

        final ObjectAnimator translationX2 = ObjectAnimator.ofFloat(target, View.TRANSLATION_X, finalTranslationX);
        translationX2.setDuration(totalDuration);
        translationX2.setInterpolator(interpolator);
        add(translationX2);
    }
}
