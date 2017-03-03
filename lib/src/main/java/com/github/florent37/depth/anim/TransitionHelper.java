package com.github.florent37.depth.anim;

import android.animation.TimeInterpolator;
import android.view.View;

import no.agens.depth.lib.tween.interpolators.QuintOut;

public class TransitionHelper {

    public static final TimeInterpolator interpolator = new QuintOut();

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
