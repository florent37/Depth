package com.github.florent37.depth.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;

import no.agens.depth.lib.DepthLayout;
import no.agens.depth.lib.tween.interpolators.BackOut;
import no.agens.depth.lib.tween.interpolators.CircInOut;
import no.agens.depth.lib.tween.interpolators.QuintInOut;

/**
 * Created by florentchampigny on 02/03/2017.
 */

public class RevertAnimation {

    private Animator.AnimatorListener listener = new AnimatorListenerAdapter() {
    };
    private DepthLayout depthLayout;
    private long totalDuration = 1100;
    private ViewFinalState viewFinalState;

    public RevertAnimation() {
        this.totalDuration = 1100l;
        this.viewFinalState = new ViewFinalState.Builder()
                .setFinalElevation(30f)
                .setFinalRotationX(0f)
                .setFinalRotationZ(0f)
                .setFinalTranslationY(0f)
                .setFinalScale(1f)
                .build();
    }

    public RevertAnimation setAnimatorListener(Animator.AnimatorListener animatorListener) {
        this.listener = animatorListener;
        return this;
    }

    public RevertAnimation setDepthLayout(DepthLayout depthLayout) {
        this.depthLayout = depthLayout;
        return this;
    }

    public RevertAnimation setViewFinalState(ViewFinalState viewFinalState) {
        this.viewFinalState = viewFinalState;
        return this;
    }

    public RevertAnimation setDuration(long totalDuration) {
        this.totalDuration = totalDuration;
        return this;
    }

    private void revertFromMenu(final DepthLayout target, final float customElevation) {
        final float density = target.getResources().getDisplayMetrics().density;
        final long duration = (long) (totalDuration - totalDuration * 0.1f);
        final int fisrtdelay = 300;

        target.setPivotY(TransitionHelper.getDistanceToCenter(target));
        target.setPivotX(TransitionHelper.getDistanceToCenterX(target));
        target.setCameraDistance(10000 * density);

        final float finalElevation = viewFinalState.getFinalElevation() * density;
        final float finalRotationX = viewFinalState.getFinalRotationX();
        final float finalRotation = viewFinalState.getFinalRotationZ();
        final float finalTranslationY = viewFinalState.getFinalTranslationY();
        final float finalScale = viewFinalState.getFinalScale();

        { //translation Y
            final long translationDuration = (long) (duration * 0.7f);

            final ObjectAnimator translationY = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, finalTranslationY);
            translationY.setDuration(translationDuration);
            translationY.setInterpolator(new BackOut());
            translationY.setStartDelay((long) (duration * 0.25f + fisrtdelay));
            translationY.start();
        }

        { //rotation
            final ObjectAnimator rotation = ObjectAnimator.ofFloat(target, View.ROTATION, target.getRotation(), finalRotation);
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
            target.setCustomShadowElevation(customElevation * density);
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

    public void start() {
        revertFromMenu(depthLayout, 0);
    }


}
