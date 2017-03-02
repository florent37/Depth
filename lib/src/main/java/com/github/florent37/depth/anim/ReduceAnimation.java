package com.github.florent37.depth.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.View;

import no.agens.depth.lib.DepthLayout;

/**
 * Created by florentchampigny on 02/03/2017.
 */

public class ReduceAnimation {

    private View root;
    private DepthLayout depthLayout;

    public ReduceAnimation setRoot(View root) {
        this.root = root;
        return this;
    }

    public ReduceAnimation setDepthLayout(DepthLayout depthLayout) {
        this.depthLayout = depthLayout;
        return this;
    }

    private ValueAnimator exitAnimate(final DepthLayout target, final float moveY, final float customElevation, long startDelay, int subtractDelay) {
        final TimeInterpolator interpolator = TransitionHelper.interpolator;
        final int duration = TransitionHelper.DURATION;
        final float density = target.getResources().getDisplayMetrics().density;

        target.setPivotY(TransitionHelper.getDistanceToCenter(target));
        target.setPivotX(TransitionHelper.getDistanceToCenterX(target));
        target.setCameraDistance(10000 * density);

        final ObjectAnimator rotationX = ObjectAnimator.ofFloat(target, View.ROTATION_X, TransitionHelper.TARGET_ROTATION_X).setDuration(duration);
        rotationX.setInterpolator(interpolator);
        rotationX.setStartDelay(startDelay);
        rotationX.start();

        final ObjectAnimator elevation = ObjectAnimator.ofFloat(target, "CustomShadowElevation", target.getCustomShadowElevation(), customElevation * density).setDuration(duration);
        elevation.setInterpolator(interpolator);
        elevation.setStartDelay(startDelay);
        elevation.start();

        final ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, TransitionHelper.TARGET_SCALE);
        scaleX.setDuration(duration);
        scaleX.setInterpolator(interpolator);
        scaleX.setStartDelay(startDelay);
        scaleX.start();

        final ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, TransitionHelper.TARGET_SCALE);
        scaleY.setDuration(duration);
        scaleY.setInterpolator(interpolator);
        scaleY.setStartDelay(startDelay);
        scaleY.start();

        final ObjectAnimator rotation = ObjectAnimator.ofFloat(target, View.ROTATION, TransitionHelper.TARGET_ROTATION);
        rotation.setDuration(duration + 500);
        rotation.setInterpolator(interpolator);
        rotation.setStartDelay(startDelay);
        rotation.start();

        final ObjectAnimator translationY = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, -moveY * density);
        translationY.setDuration(subtractDelay);
        translationY.setInterpolator(interpolator);
        translationY.setStartDelay(startDelay);
        translationY.start();

        return scaleY;
    }

    public void start(Animator.AnimatorListener listener) {
        exitAnimate(depthLayout, 0, 30f, 15, 190);

        final ObjectAnimator translationY = ObjectAnimator.ofFloat(root, View.TRANSLATION_Y, -90f * root.getResources().getDisplayMetrics().density);
        translationY.setDuration(TransitionHelper.DURATION);
        translationY.setInterpolator(TransitionHelper.interpolator);
        if (listener != null) {
            translationY.addListener(listener);
        }
        translationY.start();
    }
}
