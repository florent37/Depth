package com.github.florent37.depth.animations;

import android.animation.ObjectAnimator;
import android.view.View;

import com.github.florent37.depth.DepthRelativeLayout;
import com.github.florent37.depth.TransitionHelper;

import com.github.florent37.depth.lib.tween.interpolators.BackOut;
import com.github.florent37.depth.lib.tween.interpolators.CircInOut;
import com.github.florent37.depth.lib.tween.interpolators.QuintInOut;

/**
 * Created by florentchampigny on 02/03/2017.
 */

public class RevertAnimation extends DepthAnimation<RevertAnimation> {

    private RevertConfiguration revertConfiguration;

    public RevertAnimation() {
        this.revertConfiguration = new RevertConfiguration();
    }

    public RevertAnimation setRevertConfiguration(RevertConfiguration revertConfiguration) {
        if (revertConfiguration != null) {
            this.revertConfiguration = revertConfiguration;
        }
        return this;
    }

    @Override
    public void prepareAnimators(DepthRelativeLayout target, int index, int animationDelay) {
        final long totalDuration = revertConfiguration.getDuration();

        final float density = target.getResources().getDisplayMetrics().density;
        final long duration = (long) (totalDuration * 0.9f);
        final int firstDelay = (int) (duration * 0.3f);

        target.setPivotY(TransitionHelper.getDistanceToCenter(target));
        target.setPivotX(TransitionHelper.getDistanceToCenterX(target));
        target.setCameraDistance(10000 * density);

        final float finalElevation = revertConfiguration.getElevation() * density;
        final float finalTranslationY = revertConfiguration.getTranslationY() * density;
        final float finalTranslationX = revertConfiguration.getTranslationX() * density;
        final float finalRotationX = revertConfiguration.getRotationX();
        final float finalRotationZ = revertConfiguration.getRotationZ();
        final float finalScale = revertConfiguration.getScale();

        { //translation
            final long translationDuration = (long) (duration * 0.7f);

            final ObjectAnimator translationY = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, finalTranslationY);
            translationY.setDuration(translationDuration);
            translationY.setInterpolator(new BackOut());
            translationY.setStartDelay((long) (duration * 0.25f + firstDelay));
            add(translationY);

            final ObjectAnimator translationX = ObjectAnimator.ofFloat(target, View.TRANSLATION_X, finalTranslationX);
            translationX.setDuration(translationDuration);
            translationX.setInterpolator(new BackOut());
            translationX.setStartDelay((long) (duration * 0.25f + firstDelay));
            add(translationX);
        }

        { //rotation
            final ObjectAnimator rotation = ObjectAnimator.ofFloat(target, View.ROTATION, target.getRotation(), finalRotationZ);
            rotation.setDuration(totalDuration);
            rotation.setInterpolator(new QuintInOut());
            add(rotation);

            final ObjectAnimator rotationX = ObjectAnimator.ofFloat(target, View.ROTATION_X, target.getRotationX(), finalRotationX);
            rotationX.setDuration(duration);
            rotationX.setInterpolator(new QuintInOut());
            rotationX.setStartDelay(firstDelay);
            add(rotationX);
//            target.setRotationX(TransitionHelper.TARGET_ROTATION_X);
        }

        { //shadow
            final ObjectAnimator elevation = ObjectAnimator.ofFloat(target, "CustomShadowElevation", target.getCustomShadowElevation(), finalElevation);
            elevation.setDuration(duration);
            elevation.setInterpolator(new QuintInOut());
            elevation.setStartDelay(firstDelay);
            add(elevation);
        }

        { // scale
            final ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, target.getScaleX(), finalScale);
            scaleX.setDuration(duration);
            scaleX.setInterpolator(new CircInOut());
            scaleX.setStartDelay(firstDelay);
            attachListener(scaleX);
            add(scaleX);
            //target.setScaleX(TransitionHelper.TARGET_SCALE);

            final ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, target.getScaleY(), finalScale);
            scaleY.setDuration(duration);
            scaleY.setInterpolator(new CircInOut());
            scaleY.setStartDelay(firstDelay);
            add(scaleY);
            //target.setScaleY(TransitionHelper.TARGET_SCALE);
        }

    }

}
