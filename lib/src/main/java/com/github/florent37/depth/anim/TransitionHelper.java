package com.github.florent37.depth.anim;

import android.animation.TimeInterpolator;
import android.view.View;

import no.agens.depth.lib.tween.interpolators.QuintOut;

public class TransitionHelper {

    public static final float TARGET_SCALE = 0.5f;
    public static final float TARGET_ROTATION = -50f;
    public static final float TARGET_ROTATION_X = 60f;
    public static final int DURATION = 1100;

    public static final TimeInterpolator interpolator = new QuintOut();

    public static final int FISRTDELAY = 300;

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
