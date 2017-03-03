package com.github.florent37.depth.anim;

import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;

import no.agens.depth.lib.DepthLayout;
import no.agens.depth.lib.tween.interpolators.BackOut;
import no.agens.depth.lib.tween.interpolators.CircInOut;
import no.agens.depth.lib.tween.interpolators.ExpoOut;
import no.agens.depth.lib.tween.interpolators.QuadInOut;
import no.agens.depth.lib.tween.interpolators.QuintInOut;

import static com.github.florent37.depth.anim.TransitionHelper.FISRTDELAY;
import static com.github.florent37.depth.anim.TransitionHelper.TARGET_ROTATION;
import static com.github.florent37.depth.anim.TransitionHelper.TARGET_ROTATION_X;
import static com.github.florent37.depth.anim.TransitionHelper.TARGET_SCALE;
import static com.github.florent37.depth.anim.TransitionHelper.getDistanceToCenter;
import static com.github.florent37.depth.anim.TransitionHelper.getDistanceToCenterX;

/**
 * Created by florentchampigny on 02/03/2017.
 */

public class EnterAnimation {

    private AnimatorListenerAdapter listener = new AnimatorListenerAdapter() {};

    private DepthLayout depthLayout;

    public EnterAnimation setEndListener(AnimatorListenerAdapter listener) {
        this.listener = listener;
        return this;
    }

    public EnterAnimation setDepthLayout(DepthLayout depthLayout) {
        this.depthLayout = depthLayout;
        return this;
    }

    private void introAnimate(final DepthLayout target, final float moveY, final float customElevation, int subtractDelay) {
        final float density = target.getResources().getDisplayMetrics().density;

        target.setPivotY(TransitionHelper.getDistanceToCenter(target));
        target.setPivotX(TransitionHelper.getDistanceToCenterX(target));
        target.setCameraDistance(10000 * density);

        final ObjectAnimator translationY2 = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, target.getResources().getDisplayMetrics().heightPixels, -moveY * density).setDuration(800);
        translationY2.setInterpolator(new ExpoOut());
        translationY2.setStartDelay(700 + subtractDelay);
        translationY2.start();
        target.setTranslationY(target.getResources().getDisplayMetrics().heightPixels);

        final ObjectAnimator translationX2 = ObjectAnimator.ofFloat(target, View.TRANSLATION_X, -target.getResources().getDisplayMetrics().widthPixels, 0).setDuration(800);
        translationX2.setInterpolator(new ExpoOut());
        translationX2.setStartDelay(700 + subtractDelay);
        translationX2.start();
        target.setTranslationX(-target.getResources().getDisplayMetrics().widthPixels);

        final ObjectAnimator translationY = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, 0);
        translationY.setDuration(700);
        translationY.setInterpolator(new BackOut());
        translationY.setStartDelay(700 + 800);
        translationY.start();

        final ObjectAnimator rotationX = ObjectAnimator.ofFloat(target, View.ROTATION_X, TransitionHelper.TARGET_ROTATION_X, 0).setDuration(1000);
        rotationX.setInterpolator(new QuintInOut());
        rotationX.setStartDelay(700 + TransitionHelper.FISRTDELAY + subtractDelay);
        rotationX.start();
        target.setRotationX(TransitionHelper.TARGET_ROTATION_X);

        final ObjectAnimator elevation = ObjectAnimator.ofFloat(target, "CustomShadowElevation", customElevation * density, target.getCustomShadowElevation()).setDuration(1000);
        elevation.setInterpolator(new QuintInOut());
        elevation.setStartDelay(700 + TransitionHelper.FISRTDELAY + subtractDelay * 2);
        elevation.start();
        target.setCustomShadowElevation(customElevation * density);

        final ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, TransitionHelper.TARGET_SCALE, target.getScaleX()).setDuration(1000);
        scaleX.setInterpolator(new CircInOut());
        scaleX.setStartDelay(700 + TransitionHelper.FISRTDELAY + subtractDelay);
        scaleX.start();
        target.setScaleX(TransitionHelper.TARGET_SCALE);

        final ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, TransitionHelper.TARGET_SCALE, target.getScaleY()).setDuration(1000);
        scaleY.setInterpolator(new CircInOut());
        scaleY.setStartDelay(700 + TransitionHelper.FISRTDELAY + subtractDelay);
        scaleY.addListener(listener);
        scaleY.start();
        target.setScaleY(TransitionHelper.TARGET_SCALE);

        final ObjectAnimator rotation = ObjectAnimator.ofFloat(target, View.ROTATION, TransitionHelper.TARGET_ROTATION, 0).setDuration(1400);
        rotation.setInterpolator(new QuadInOut());
        rotation.setStartDelay(TransitionHelper.FISRTDELAY + subtractDelay);
        rotation.start();
        target.setRotation(TransitionHelper.TARGET_ROTATION);
    }

    public void start() {
        introAnimate(depthLayout, 0, 30f, 180);
    }

}
