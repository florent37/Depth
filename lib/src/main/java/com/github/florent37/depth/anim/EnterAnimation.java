package com.github.florent37.depth.anim;

import android.animation.ObjectAnimator;
import android.view.View;

import no.agens.depth.lib.DepthLayout;
import no.agens.depth.lib.tween.interpolators.CircInOut;
import no.agens.depth.lib.tween.interpolators.ExpoOut;
import no.agens.depth.lib.tween.interpolators.QuadInOut;
import no.agens.depth.lib.tween.interpolators.QuintInOut;

/**
 * Created by florentchampigny on 02/03/2017.
 */

public class EnterAnimation extends DepthAnimation<EnterAnimation> {

    private float initialRotationX;
    private float initialRotationZ;
    private float initialScale;
    private float initialElevation;

    public EnterAnimation() {
        this.totalDuration = 1400l;
        this.initialRotationX = 60f;
        this.initialRotationZ = -50f;
        this.initialScale = 0.5f;
        this.initialElevation = 30f;
    }

    public EnterAnimation setInitialRotationX(float initialRotationX) {
        this.initialRotationX = initialRotationX;
        return this;
    }

    public EnterAnimation setInitialRotationZ(float initialRotationZ) {
        this.initialRotationZ = initialRotationZ;
        return this;
    }

    public EnterAnimation setInitialScale(float initialScale) {
        this.initialScale = initialScale;
        return this;
    }

    public EnterAnimation setInitialElevation(float initialElevation) {
        this.initialElevation = initialElevation;
        return this;
    }

    private void introAnimate() {
        final DepthLayout target = this.depthLayout;

        final float density = target.getResources().getDisplayMetrics().density;

        final float initialElevation = this.initialElevation * density;

        target.setPivotY(TransitionHelper.getDistanceToCenter(target));
        target.setPivotX(TransitionHelper.getDistanceToCenterX(target));
        target.setCameraDistance(10000 * density);

        final float finalRotationX = target.getRotationX();
        final float finalRotationZ = target.getRotation();
        final float finalTranslationY = target.getTranslationY();
        final float finalTranslationX = target.getTranslationX();
        final float finalScaleY = target.getScaleY();
        final float finalScaleX = target.getScaleX();
        final float finalElevation = target.getCustomShadowElevation();

        final long duration = (long) (totalDuration * 0.714f);
        final long firstTranslationDuration = (long) (duration * 0.8f);
        final long rotationZ_duration = totalDuration;

        final long rotationZ_startDelay = (long) (duration * 0.3f);
        final long translations_startDelay = (long) (duration * 0.7f);
        final long scale_shadow_rotationX_startDelay = rotationZ_startDelay + translations_startDelay;

        { //initial position & restore
            final int initialTranslationY = target.getResources().getDisplayMetrics().heightPixels;
            final int initialTranslationX = -target.getResources().getDisplayMetrics().widthPixels;

            target.setTranslationY(initialTranslationY);
            final ObjectAnimator translationY2 = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, initialTranslationY, finalTranslationY);
            translationY2.setDuration(firstTranslationDuration);
            translationY2.setInterpolator(new ExpoOut());
            translationY2.setStartDelay(translations_startDelay);
            translationY2.start();

            target.setTranslationX(initialTranslationX);
            final ObjectAnimator translationX2 = ObjectAnimator.ofFloat(target, View.TRANSLATION_X, initialTranslationX, finalTranslationX);
            translationX2.setDuration(firstTranslationDuration);
            translationX2.setInterpolator(new ExpoOut());
            translationX2.setStartDelay(translations_startDelay);
            translationX2.start();
        }

        { //rotation

            target.setRotationX(initialRotationX);
            final ObjectAnimator rotationX = ObjectAnimator.ofFloat(target, View.ROTATION_X, initialRotationX, finalRotationX);
            rotationX.setDuration(duration);
            rotationX.setInterpolator(new QuintInOut());
            rotationX.setStartDelay(scale_shadow_rotationX_startDelay);
            rotationX.start();

            target.setRotation(initialRotationZ);
            final ObjectAnimator rotation = ObjectAnimator.ofFloat(target, View.ROTATION, initialRotationZ, finalRotationZ);
            rotation.setDuration(rotationZ_duration);
            rotation.setInterpolator(new QuadInOut());
            rotation.setStartDelay(rotationZ_startDelay);
            rotation.start();
        }


        { //shadow
            target.setCustomShadowElevation(initialElevation);
            final ObjectAnimator elevation = ObjectAnimator.ofFloat(target, "CustomShadowElevation", initialElevation, finalElevation);
            elevation.setDuration(duration);
            elevation.setInterpolator(new QuintInOut());
            elevation.setStartDelay(scale_shadow_rotationX_startDelay);
            elevation.start();
        }

        { //enlarge
            target.setScaleX(initialScale);
            final ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, initialScale, finalScaleX);
            scaleX.setDuration(duration);
            scaleX.setInterpolator(new CircInOut());
            scaleX.setStartDelay(scale_shadow_rotationX_startDelay);
            scaleX.start();

            target.setScaleY(initialScale);
            final ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, initialScale, finalScaleY);
            scaleY.setDuration(duration);
            scaleY.setInterpolator(new CircInOut());
            scaleY.setStartDelay(scale_shadow_rotationX_startDelay);
            scaleY.addListener(listener);
            scaleY.start();
        }
    }

    @Override
    public void start() {
        introAnimate();
    }

}
