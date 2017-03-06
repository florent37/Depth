package com.github.florent37.depth.animations;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.view.View;

import com.github.florent37.depth.DepthRelativeLayout;
import com.github.florent37.depth.TransitionHelper;

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

    @Override
    public void prepareAnimators(DepthRelativeLayout target, int index, int animationDelay) {

        final TimeInterpolator interpolator = TransitionHelper.interpolator;
        final float density = target.getResources().getDisplayMetrics().density;

        target.setPivotY(TransitionHelper.getDistanceToCenter(target));
        target.setPivotX(TransitionHelper.getDistanceToCenterX(target));
        target.setCameraDistance(10000 * density);

        final long totalDuration = reduceConfiguration.getDuration();

        final long duration = (long) (totalDuration * 0.7f);
        final long translationDuration = (long) (0.125 * totalDuration);

        final float finalTranslationY = (index * -1 * 8) * density + reduceConfiguration.getTranslationY() * density;
        final float finalElevation = reduceConfiguration.getElevation() * density;
        final float finalScale = reduceConfiguration.getScale();
        final float finalRotationX = reduceConfiguration.getRotationX();
        final float finalRotationZ = reduceConfiguration.getRotationZ();

        { //rotation X & Z
            final ObjectAnimator rotationX = ObjectAnimator.ofFloat(target, View.ROTATION_X, finalRotationX);
            rotationX.setDuration(duration);
            rotationX.setInterpolator(interpolator);
            rotationX.setStartDelay(animationDelay);
            attachListener(rotationX);
            add(rotationX);

            final ObjectAnimator rotation = ObjectAnimator.ofFloat(target, View.ROTATION, finalRotationZ);
            rotation.setDuration(totalDuration);
            rotation.setInterpolator(interpolator);
            rotation.setStartDelay(animationDelay);
            add(rotation);
        }

        { //shadow
            final ObjectAnimator elevation = ObjectAnimator.ofFloat(target, "CustomShadowElevation", target.getCustomShadowElevation(), finalElevation);
            elevation.setDuration(duration);
            elevation.setInterpolator(interpolator);
            elevation.setStartDelay(animationDelay);
            add(elevation);
        }

        { //scale

            final ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, finalScale);
            scaleX.setDuration(duration);
            scaleX.setInterpolator(interpolator);
            scaleX.setStartDelay(animationDelay);
            add(scaleX);

            final ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, finalScale);
            scaleY.setDuration(duration);
            scaleY.setInterpolator(interpolator);
            scaleY.setStartDelay(animationDelay);
            add(scaleY);
        }


        { //translation Y
            final ObjectAnimator translationY = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, finalTranslationY);
            translationY.setDuration(translationDuration);
            translationY.setInterpolator(interpolator);
            translationY.setStartDelay(animationDelay);
            add(translationY);
        }

    }

}
