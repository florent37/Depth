package com.github.florent37.depth.anim;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.view.View;

import no.agens.depth.lib.DepthLayout;

/**
 * Created by florentchampigny on 02/03/2017.
 */

public class ReduceAnimation {

    private DepthLayout depthLayout;
    private Animator.AnimatorListener listener;
    private long totalDuration;

    private ViewFinalState viewFinalState;

    public ReduceAnimation() {
        this.totalDuration = 1600l;

        this.viewFinalState = new ViewFinalState.Builder()
                .setFinalScale(0.5f)
                .setFinalRotationX(60f)
                .setFinalRotationZ(-50f)
                .setFinalTranslationY(0)
                .setFinalElevation(30f)
                .build();
    }

    public ReduceAnimation setDepthLayout(DepthLayout depthLayout) {
        this.depthLayout = depthLayout;
        return this;
    }

    public ReduceAnimation setListener(Animator.AnimatorListener listener) {
        this.listener = listener;
        return this;
    }

    public ReduceAnimation setViewFinalState(ViewFinalState viewFinalState) {
        this.viewFinalState = viewFinalState;
        return this;
    }

    public ReduceAnimation setDuration(long totalDuration) {
        this.totalDuration = totalDuration;
        return this;
    }

    public float getFinalRotationX(){
        return viewFinalState.getFinalRotationX();
    }

    public float getFinalRotationZ(){
        return viewFinalState.getFinalRotationZ();
    }

    private void exitAnimate(final DepthLayout target) {
        final TimeInterpolator interpolator = TransitionHelper.interpolator;
        final float density = target.getResources().getDisplayMetrics().density;

        target.setPivotY(TransitionHelper.getDistanceToCenter(target));
        target.setPivotX(TransitionHelper.getDistanceToCenterX(target));
        target.setCameraDistance(10000 * density);

        final long duration = (long) (totalDuration * 0.7f);
        final long translationDuration = (long) (0.125 * totalDuration);

        final float finalScale = viewFinalState.getFinalScale();
        final float finalTranslationY = -1 * viewFinalState.getFinalTranslationY() * density;
        final float finalRotationX = viewFinalState.getFinalRotationX();
        final float finalRotationZ = viewFinalState.getFinalRotationZ();
        final float finalElevation = viewFinalState.getFinalElevation() * density;


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

    public void start() {
        exitAnimate(depthLayout);
    }
}
