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

    private Animator.AnimatorListener listener = new AnimatorListenerAdapter() {};
    private View root;
    private DepthLayout depthLayout;

    public RevertAnimation setAnimatorListener(Animator.AnimatorListener animatorListener) {
        this.listener = animatorListener;
        return this;
    }

    public RevertAnimation setRoot(View root) {
        this.root = root;
        return this;
    }

    public RevertAnimation setDepthLayout(DepthLayout depthLayout) {
        this.depthLayout = depthLayout;
        return this;
    }

    private ObjectAnimator revertFromMenu(final DepthLayout target, final float customElevation, int subtractDelay, float targetElevation) {
        final float density = target.getResources().getDisplayMetrics().density;
        final int duration = 1000;

        target.setPivotY(TransitionHelper.getDistanceToCenter(target));
        target.setPivotX(TransitionHelper.getDistanceToCenterX(target));
        target.setCameraDistance(10000 * density);


        final ObjectAnimator translationY = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, 0);
        translationY.setDuration(duration - 300);
        translationY.setInterpolator(new BackOut());
        translationY.setStartDelay(250 + TransitionHelper.FISRTDELAY + subtractDelay);
        translationY.start();


        final ObjectAnimator rotationX = ObjectAnimator.ofFloat(target, View.ROTATION_X, target.getRotationX(), 0);
        rotationX.setDuration(duration);
        rotationX.setInterpolator(new QuintInOut());
        rotationX.setStartDelay(TransitionHelper.FISRTDELAY + subtractDelay);
        rotationX.start();
        target.setRotationX(TransitionHelper.TARGET_ROTATION_X);

        final ObjectAnimator elevation = ObjectAnimator.ofFloat(target, "CustomShadowElevation", target.getCustomShadowElevation(), targetElevation * density);
        elevation.setDuration(duration);
        elevation.setInterpolator(new QuintInOut());
        elevation.setStartDelay(TransitionHelper.FISRTDELAY + subtractDelay * 2);
        elevation.start();
        target.setCustomShadowElevation(customElevation * density);

        final ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, target.getScaleX(), 1f);
        scaleX.setDuration(duration);
        scaleX.setInterpolator(new CircInOut());
        scaleX.setStartDelay(TransitionHelper.FISRTDELAY + subtractDelay);
        scaleX.start();
        target.setScaleX(TransitionHelper.TARGET_SCALE);

        final ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, target.getScaleY(), 1f);
        scaleY.setDuration(duration);
        scaleY.setInterpolator(new CircInOut());
        scaleY.setStartDelay(TransitionHelper.FISRTDELAY + subtractDelay);
        scaleY.start();
        target.setScaleY(TransitionHelper.TARGET_SCALE);

        final ObjectAnimator rotation = ObjectAnimator.ofFloat(target, View.ROTATION, target.getRotation(), 0);
        rotation.setDuration(duration + 100);
        rotation.setInterpolator(new QuintInOut());
        rotation.setStartDelay(subtractDelay);
        rotation.start();
        return scaleY;
    }

    public void start() {
        revertFromMenu(depthLayout, 30f, 10, 0);

        final ObjectAnimator translationY = ObjectAnimator.ofFloat(root, View.TRANSLATION_Y, 0);
        translationY.setDuration(TransitionHelper.DURATION);
        translationY.setInterpolator(new QuintInOut());
        translationY.addListener(listener);
        translationY.start();
    }


}
