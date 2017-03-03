package no.agens.depth;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.view.View;

import no.agens.depth.lib.DepthLayout;
import no.agens.depth.lib.tween.interpolators.BackOut;
import no.agens.depth.lib.tween.interpolators.CircInOut;
import no.agens.depth.lib.tween.interpolators.ExpoIn;
import no.agens.depth.lib.tween.interpolators.ExpoOut;
import no.agens.depth.lib.tween.interpolators.QuadInOut;
import no.agens.depth.lib.tween.interpolators.QuintInOut;
import no.agens.depth.lib.tween.interpolators.QuintOut;

public class TransitionHelper {

    public static final float TARGET_SCALE = 0.5f;
    public static final float TARGET_ROTATION = -50f;
    public static final float TARGET_ROTATION_X = 60f;
    public static final int MOVE_Y_STEP = 15;
    public static final int DURATION = 1100;
    public static final QuintOut VALUEinterpolator = new QuintOut();

    public static final int FISRTDELAY = 300;

    public static void startIntroAnim(View root, AnimatorListenerAdapter introEndListener) {
        introAnimate((DepthLayout) root.findViewById(R.id.root_depth_layout), 0, 30f, 15, 180);
    }

    static ObjectAnimator introAnimate(final DepthLayout target, final float moveY, final float customElevation, long delay, int subtractDelay) {

        target.setPivotY(getDistanceToCenter(target));
        target.setPivotX(getDistanceToCenterX(target));
        target.setCameraDistance(10000 * target.getResources().getDisplayMetrics().density);

        ObjectAnimator translationY2 = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, target.getResources().getDisplayMetrics().heightPixels, -moveY * target.getResources().getDisplayMetrics().density).setDuration(800);
        translationY2.setInterpolator(new ExpoOut());
        translationY2.setStartDelay(700 + subtractDelay);
        translationY2.start();
        target.setTranslationY(target.getResources().getDisplayMetrics().heightPixels);

        ObjectAnimator translationX2 = ObjectAnimator.ofFloat(target, View.TRANSLATION_X, -target.getResources().getDisplayMetrics().widthPixels, 0).setDuration(800);
        translationX2.setInterpolator(new ExpoOut());
        translationX2.setStartDelay(700 + subtractDelay);
        translationX2.start();
        target.setTranslationX(-target.getResources().getDisplayMetrics().widthPixels);

        ObjectAnimator translationY = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, 0).setDuration(700);
        translationY.setInterpolator(new BackOut());
        translationY.setStartDelay(700 + 800);
        translationY.start();


        ObjectAnimator rotationX = ObjectAnimator.ofFloat(target, View.ROTATION_X, TARGET_ROTATION_X, 0).setDuration(1000);
        rotationX.setInterpolator(new QuintInOut());
        rotationX.setStartDelay(700 + FISRTDELAY + subtractDelay);
        rotationX.start();
        target.setRotationX(TARGET_ROTATION_X);

        ObjectAnimator elevation = ObjectAnimator.ofFloat(target, "CustomShadowElevation", customElevation * target.getResources().getDisplayMetrics().density, target.getCustomShadowElevation()).setDuration(1000);
        elevation.setInterpolator(new QuintInOut());
        elevation.setStartDelay(700 + FISRTDELAY + subtractDelay * 2);
        elevation.start();
        target.setCustomShadowElevation(customElevation * target.getResources().getDisplayMetrics().density);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, TARGET_SCALE, target.getScaleX()).setDuration(1000);
        scaleX.setInterpolator(new CircInOut());
        scaleX.setStartDelay(700 + FISRTDELAY + subtractDelay);
        scaleX.start();
        target.setScaleX(TARGET_SCALE);

        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, TARGET_SCALE, target.getScaleY()).setDuration(1000);
        scaleY.setInterpolator(new CircInOut());
        scaleY.setStartDelay(700 + FISRTDELAY + subtractDelay);
        scaleY.start();
        target.setScaleY(TARGET_SCALE);

        ObjectAnimator rotation = ObjectAnimator.ofFloat(target, View.ROTATION, TARGET_ROTATION, 0).setDuration(1400);
        rotation.setInterpolator(new QuadInOut());
        rotation.setStartDelay(FISRTDELAY + subtractDelay);
        rotation.start();
        target.setRotation(TARGET_ROTATION);
        return scaleY;
    }

    public static float getDistanceToCenter(View target) {
        float viewCenter = target.getTop() + target.getHeight() / 2f;
        float rootCenter = ((View) target.getParent()).getHeight() / 2;
        return target.getHeight() / 2f + rootCenter - viewCenter;
    }

    public static float getDistanceToCenterX(View target) {
        float viewCenter = target.getLeft() + target.getWidth() / 2f;
        float rootCenter = ((View) target.getParent()).getWidth() / 2;
        return target.getWidth() / 2f + rootCenter - viewCenter;
    }
}
