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


        { //initial position & restore
            final int initialTranslationY = target.getResources().getDisplayMetrics().heightPixels;
            final int initialTranslationX = -target.getResources().getDisplayMetrics().widthPixels;
            final float finalTranslationY = -moveY * density;
            final int finalTranslationX = 0;

            target.setTranslationY(initialTranslationY);
            final ObjectAnimator translationY2 = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, initialTranslationY, finalTranslationY);
            translationY2.setDuration(800);
            translationY2.setInterpolator(new ExpoOut());
            translationY2.setStartDelay(700 + subtractDelay);
            translationY2.start();

            target.setTranslationX(initialTranslationX);
            final ObjectAnimator translationX2 = ObjectAnimator.ofFloat(target, View.TRANSLATION_X, initialTranslationX, finalTranslationX);
            translationX2.setDuration(800);
            translationX2.setInterpolator(new ExpoOut());
            translationX2.setStartDelay(700 + subtractDelay);
            translationX2.start();
        }

        final ObjectAnimator translationY = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, 0);
        translationY.setDuration(700);
        translationY.setInterpolator(new BackOut());
        translationY.setStartDelay(700 + 800);
        translationY.start();

        { //rotation
            final float initialRotationX = TransitionHelper.TARGET_ROTATION_X;
            final float finalRotationX = 0;
            final float initialRotationZ = TransitionHelper.TARGET_ROTATION;
            final float finalRotationZ = 0;

            target.setRotationX(initialRotationX);
            final ObjectAnimator rotationX = ObjectAnimator.ofFloat(target, View.ROTATION_X, initialRotationX, finalRotationX);
            rotationX.setDuration(1000);
            rotationX.setInterpolator(new QuintInOut());
            rotationX.setStartDelay(700 + TransitionHelper.FISRTDELAY + subtractDelay);
            rotationX.start();

            target.setRotation(initialRotationZ);
            final ObjectAnimator rotation = ObjectAnimator.ofFloat(target, View.ROTATION, initialRotationZ, finalRotationZ);
            rotation.setDuration(1400);
            rotation.setInterpolator(new QuadInOut());
            rotation.setStartDelay(TransitionHelper.FISRTDELAY + subtractDelay);
            rotation.start();
        }

        { //shadow
            final ObjectAnimator elevation = ObjectAnimator.ofFloat(target, "CustomShadowElevation", customElevation * density, target.getCustomShadowElevation());
            elevation.setDuration(1000);
            elevation.setInterpolator(new QuintInOut());
            elevation.setStartDelay(700 + TransitionHelper.FISRTDELAY + subtractDelay * 2);
            elevation.start();
            target.setCustomShadowElevation(customElevation * density);
        }

        { //enlarge
            final float initialScale = TransitionHelper.TARGET_SCALE;
            final float finalScaleY = target.getScaleY();
            final float finalScaleX = target.getScaleX();

            target.setScaleX(initialScale);
            final ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, initialScale, finalScaleX);
            scaleX.setDuration(1000);
            scaleX.setInterpolator(new CircInOut());
            scaleX.setStartDelay(700 + TransitionHelper.FISRTDELAY + subtractDelay);
            scaleX.start();

            target.setScaleY(initialScale);
            final ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, initialScale, finalScaleY);
            scaleY.setDuration(1000);
            scaleY.setInterpolator(new CircInOut());
            scaleY.setStartDelay(700 + TransitionHelper.FISRTDELAY + subtractDelay);
            scaleY.addListener(listener);
            scaleY.start();
        }
    }

    public void start() {
        introAnimate(depthLayout, 0, 30f, 180);
    }

}
