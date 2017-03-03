package com.github.florent37.depth.anim;

import android.animation.ObjectAnimator;
import android.view.View;

import no.agens.depth.lib.DepthLayout;
import no.agens.depth.lib.tween.interpolators.BackOut;
import no.agens.depth.lib.tween.interpolators.CircInOut;
import no.agens.depth.lib.tween.interpolators.QuintInOut;

/**
 * Created by florentchampigny on 02/03/2017.
 */

public class RevertAnimation extends DepthAnimation<RevertAnimation> {

    public RevertAnimation() {
        this.totalDuration = 1100l;
        this.finalElevation = 30f;
        this.finalRotationX = 0f;
        this.finalRotationZ = 0f;
        this.finalTranslationY = 0f;
        this.finalScale = 1f;
    }

    private void revertFromMenu() {
        final DepthLayout target = depthLayout;

        final float density = target.getResources().getDisplayMetrics().density;
        final long duration = (long) (totalDuration - totalDuration * 0.1f);
        final int fisrtdelay = 300;

        target.setPivotY(TransitionHelper.getDistanceToCenter(target));
        target.setPivotX(TransitionHelper.getDistanceToCenterX(target));
        target.setCameraDistance(10000 * density);

        final float finalElevation = this.finalElevation * density;
        final float finalTranslationY = this.finalTranslationY * density;

        { //translation Y
            final long translationDuration = (long) (duration * 0.7f);

            final ObjectAnimator translationY = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, finalTranslationY);
            translationY.setDuration(translationDuration);
            translationY.setInterpolator(new BackOut());
            translationY.setStartDelay((long) (duration * 0.25f + fisrtdelay));
            translationY.start();
        }

        { //rotation
            final ObjectAnimator rotation = ObjectAnimator.ofFloat(target, View.ROTATION, target.getRotation(), finalRotationZ);
            rotation.setDuration(totalDuration);
            rotation.setInterpolator(new QuintInOut());
            rotation.start();

            final ObjectAnimator rotationX = ObjectAnimator.ofFloat(target, View.ROTATION_X, target.getRotationX(), finalRotationX);
            rotationX.setDuration(duration);
            rotationX.setInterpolator(new QuintInOut());
            rotationX.setStartDelay(fisrtdelay);
            rotationX.start();
//            target.setRotationX(TransitionHelper.TARGET_ROTATION_X);
        }

        { //shadow
            final ObjectAnimator elevation = ObjectAnimator.ofFloat(target, "CustomShadowElevation", target.getCustomShadowElevation(), finalElevation);
            elevation.setDuration(duration);
            elevation.setInterpolator(new QuintInOut());
            elevation.setStartDelay(fisrtdelay);
            elevation.start();
            target.setCustomShadowElevation(finalElevation * density);
        }

        { // scale
            final ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, target.getScaleX(), finalScale);
            scaleX.setDuration(duration);
            scaleX.setInterpolator(new CircInOut());
            scaleX.setStartDelay(fisrtdelay);
            scaleX.start();
            //target.setScaleX(TransitionHelper.TARGET_SCALE);

            final ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, target.getScaleY(), finalScale);
            scaleY.setDuration(duration);
            scaleY.setInterpolator(new CircInOut());
            scaleY.setStartDelay(fisrtdelay);
            scaleY.start();
            //target.setScaleY(TransitionHelper.TARGET_SCALE);
        }

    }

    @Override
    public void start() {
        revertFromMenu();
    }


}
