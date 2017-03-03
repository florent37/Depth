package com.github.florent37.depth.anim;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.view.View;

import no.agens.depth.lib.DepthLayout;

/**
 * Created by florentchampigny on 02/03/2017.
 */

public class ReduceAnimation extends DepthAnimation<ReduceAnimation> {

    public ReduceAnimation() {
        this.totalDuration = 1600l;

        this.finalScale = 0.5f;
        this.finalTranslationX = 0;
        this.finalTranslationY = 0;
        this.finalRotationX = 60f;
        this.finalRotationZ = -50f;
        this.finalElevation = 30f;
    }

    private void exitAnimate() {
        final DepthLayout target = depthLayout;

        final TimeInterpolator interpolator = TransitionHelper.interpolator;
        final float density = target.getResources().getDisplayMetrics().density;

        target.setPivotY(TransitionHelper.getDistanceToCenter(target));
        target.setPivotX(TransitionHelper.getDistanceToCenterX(target));
        target.setCameraDistance(10000 * density);

        final long duration = (long) (totalDuration * 0.7f);
        final long translationDuration = (long) (0.125 * totalDuration);

        final float finalTranslationY = this.finalTranslationY * density;
        final float finalElevation = this.finalElevation * density;

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
