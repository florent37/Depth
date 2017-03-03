package com.github.florent37.depth.anim.animations;

import android.animation.ObjectAnimator;
import android.view.View;

import com.github.florent37.depth.anim.TransitionHelper;

import no.agens.depth.lib.DepthLayout;
import no.agens.depth.lib.tween.interpolators.BackOut;
import no.agens.depth.lib.tween.interpolators.CircInOut;
import no.agens.depth.lib.tween.interpolators.QuintInOut;

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

    private void revertFromMenu() {
        final DepthLayout target = depthLayout;

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
            translationY.start();

            final ObjectAnimator translationX = ObjectAnimator.ofFloat(target, View.TRANSLATION_X, finalTranslationX);
            translationX.setDuration(translationDuration);
            translationX.setInterpolator(new BackOut());
            translationX.setStartDelay((long) (duration * 0.25f + firstDelay));
            translationX.start();
        }

        { //rotation
            final ObjectAnimator rotation = ObjectAnimator.ofFloat(target, View.ROTATION, target.getRotation(), finalRotationZ);
            rotation.setDuration(totalDuration);
            rotation.setInterpolator(new QuintInOut());
            rotation.start();

            final ObjectAnimator rotationX = ObjectAnimator.ofFloat(target, View.ROTATION_X, target.getRotationX(), finalRotationX);
            rotationX.setDuration(duration);
            rotationX.setInterpolator(new QuintInOut());
            rotationX.setStartDelay(firstDelay);
            rotationX.start();
//            target.setRotationX(TransitionHelper.TARGET_ROTATION_X);
        }

        { //shadow
            final ObjectAnimator elevation = ObjectAnimator.ofFloat(target, "CustomShadowElevation", target.getCustomShadowElevation(), finalElevation);
            elevation.setDuration(duration);
            elevation.setInterpolator(new QuintInOut());
            elevation.setStartDelay(firstDelay);
            elevation.start();
            target.setCustomShadowElevation(finalElevation * density);
        }

        { // scale
            final ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, target.getScaleX(), finalScale);
            scaleX.setDuration(duration);
            scaleX.setInterpolator(new CircInOut());
            scaleX.setStartDelay(firstDelay);
            scaleX.start();
            //target.setScaleX(TransitionHelper.TARGET_SCALE);

            final ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, target.getScaleY(), finalScale);
            scaleY.setDuration(duration);
            scaleY.setInterpolator(new CircInOut());
            scaleY.setStartDelay(firstDelay);
            scaleY.start();
            //target.setScaleY(TransitionHelper.TARGET_SCALE);
        }

    }

    @Override
    public void start() {
        revertFromMenu();
    }


}
