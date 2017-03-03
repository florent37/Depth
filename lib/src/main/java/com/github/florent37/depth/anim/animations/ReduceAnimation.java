package com.github.florent37.depth.anim.animations;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.view.View;

import com.github.florent37.depth.anim.TransitionHelper;

import no.agens.depth.lib.DepthLayout;

/**
 * Created by florentchampigny on 02/03/2017.
 */

public class ReduceAnimation extends DepthAnimation<ReduceAnimation> {

    private ReduceConfiguration reduceConfiguration = new ReduceConfiguration();

    public ReduceAnimation setReduceConfiguration(ReduceConfiguration reduceConfiguration) {
        if (reduceConfiguration != null) {
            this.reduceConfiguration = reduceConfiguration;
        }
        return this;
    }

    private void exitAnimate() {

        final DepthLayout target = depthLayout;

        final TimeInterpolator interpolator = TransitionHelper.interpolator;
        final float density = target.getResources().getDisplayMetrics().density;

        target.setPivotY(TransitionHelper.getDistanceToCenter(target));
        target.setPivotX(TransitionHelper.getDistanceToCenterX(target));
        target.setCameraDistance(10000 * density);

        final long totalDuration = reduceConfiguration.getDuration();

        final long duration = (long) (totalDuration * 0.7f);
        final long translationDuration = (long) (0.125 * totalDuration);

        final float finalTranslationY = reduceConfiguration.getTranslationY() * density;
        final float finalElevation = reduceConfiguration.getElevation() * density;
        final float finalScale = reduceConfiguration.getScale();
        final float finalRotationX = reduceConfiguration.getRotationX();
        final float finalRotationZ = reduceConfiguration.getRotationZ();

        { //rotation X & Z
            final ObjectAnimator rotationX = ObjectAnimator.ofFloat(target, View.ROTATION_X, finalRotationX);
            rotationX.setDuration(duration);
            rotationX.setInterpolator(interpolator);
            rotationX.addListener(listener);
            rotationX.start();

            final ObjectAnimator rotation = ObjectAnimator.ofFloat(target, View.ROTATION, finalRotationZ);
            rotation.setDuration(totalDuration);
            rotation.setInterpolator(interpolator);
            rotation.start();
        }

        { //shadow
            final ObjectAnimator elevation = ObjectAnimator.ofFloat(target, "CustomShadowElevation", target.getCustomShadowElevation(), finalElevation);
            elevation.setDuration(duration);
            elevation.setInterpolator(interpolator);
            elevation.start();
        }

        { //scale

            final ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, finalScale);
            scaleX.setDuration(duration);
            scaleX.setInterpolator(interpolator);
            scaleX.start();

            final ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, finalScale);
            scaleY.setDuration(duration);
            scaleY.setInterpolator(interpolator);
            scaleY.start();
        }


        { //translation Y
            final ObjectAnimator translationY = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, finalTranslationY);
            translationY.setDuration(translationDuration);
            translationY.setInterpolator(interpolator);
            translationY.start();
        }

    }

    @Override
    public void start() {
        exitAnimate();
    }
}
