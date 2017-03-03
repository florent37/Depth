package com.github.florent37.depth.anim.animations;

import android.animation.ObjectAnimator;
import android.view.View;

import com.github.florent37.depth.anim.TransitionHelper;

import no.agens.depth.lib.DepthLayout;
import no.agens.depth.lib.tween.interpolators.CircInOut;
import no.agens.depth.lib.tween.interpolators.ExpoOut;
import no.agens.depth.lib.tween.interpolators.QuadInOut;
import no.agens.depth.lib.tween.interpolators.QuintInOut;

/**
 * Created by florentchampigny on 02/03/2017.
 */

public class EnterAnimation extends DepthAnimation<EnterAnimation> {

    private EnterConfiguration enterConfiguration = new EnterConfiguration();

    public EnterAnimation setEnterConfiguration(EnterConfiguration enterConfiguration) {
        if (enterConfiguration != null) {
            this.enterConfiguration = enterConfiguration;
        }
        return this;
    }

    private void introAnimate() {
        final DepthLayout target = this.depthLayout;

        final float density = target.getResources().getDisplayMetrics().density;

        final float initialElevation = this.enterConfiguration.getInitialElevation() * density;
        final long totalDuration = this.enterConfiguration.getDuration();
        final float initialScale = this.enterConfiguration.getInitialScale();
        final float initialRotationX = this.enterConfiguration.getInitialRotationX();
        final float initialRotationZ = this.enterConfiguration.getInitialRotationZ();

        final float initialTranslationY = this.enterConfiguration.getInitialYPercent() * target.getResources().getDisplayMetrics().heightPixels;
        final float initialTranslationX = this.enterConfiguration.getInitialXPercent() * target.getResources().getDisplayMetrics().widthPixels;

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

        final long scale_shadow_rotationX_startDelay = (long) (totalDuration * 0.6f);
        final long rotationX_shadow_scale_duration = totalDuration - scale_shadow_rotationX_startDelay;
        final long firstTranslationDuration = (long) (totalDuration * 0.6f);

        final long rotationZ_startDelay = (long) (rotationX_shadow_scale_duration * 0.2f);
        final long rotationZ_duration = (long) (totalDuration - rotationX_shadow_scale_duration / 2f - rotationZ_startDelay);

        { //initial position & restore
            target.setTranslationY(initialTranslationY);
            final ObjectAnimator translationY2 = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, initialTranslationY, finalTranslationY);
            translationY2.setDuration(firstTranslationDuration);
            translationY2.setInterpolator(new ExpoOut());
            translationY2.start();

            target.setTranslationX(initialTranslationX);
            final ObjectAnimator translationX2 = ObjectAnimator.ofFloat(target, View.TRANSLATION_X, initialTranslationX, finalTranslationX);
            translationX2.setDuration(firstTranslationDuration);
            translationX2.setInterpolator(new ExpoOut());
            translationX2.start();
        }

        { //rotation

            target.setRotationX(initialRotationX);
            final ObjectAnimator rotationX = ObjectAnimator.ofFloat(target, View.ROTATION_X, initialRotationX, finalRotationX);
            rotationX.setDuration(rotationX_shadow_scale_duration);
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
            elevation.setDuration(rotationX_shadow_scale_duration);
            elevation.setInterpolator(new QuintInOut());
            elevation.setStartDelay(scale_shadow_rotationX_startDelay);
            elevation.start();
        }

        { //enlarge
            target.setScaleX(initialScale);
            final ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, initialScale, finalScaleX);
            scaleX.setDuration(rotationX_shadow_scale_duration);
            scaleX.setInterpolator(new CircInOut());
            scaleX.setStartDelay(scale_shadow_rotationX_startDelay);
            scaleX.start();

            target.setScaleY(initialScale);
            final ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, initialScale, finalScaleY);
            scaleY.setDuration(rotationX_shadow_scale_duration);
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
