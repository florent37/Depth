package com.github.florent37.depth.container.core;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.depth.view.core.DepthLayout;

public class DepthMotionHandler {
    private final ViewGroup view;

    float speedRotationX = 1.8f;
    float speedRotationY = 1.8f;

    int minRotationX = -50;
    int maxRotationX = 50;
    int minRotationY = -50;
    int maxRotationY = 50;

    private Float clickedX;
    private Float clickedY;

    public DepthMotionHandler(ViewGroup view) {
        this.view = view;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (view.getWidth() == 0 || view.getHeight() == 0) {
            return false;
        }

        final float x = event.getX();
        final float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                clickedX = x;
                clickedY = y;

                break;
            }
            case MotionEvent.ACTION_MOVE: {
                final float translationX = x - clickedX;
                final float translationY = y - clickedY;

                for (int i = 0; i < view.getChildCount(); i++) {
                    final View childAt = view.getChildAt(i);
                    if (childAt instanceof DepthLayout) {
                        float percentX = translationX / view.getWidth();
                        float percentY = translationY / view.getHeight();

                        Log.d("Depth", "percentX :" + percentX);

                        final int rotationY = (int) ((percentX * 90) * speedRotationY);
                        final int rotationX = (int) ((-1 * percentY * 90) * speedRotationX);

                        childAt.setRotationY(limit(rotationY, minRotationY, maxRotationY));
                        childAt.setRotationX(limit(rotationX, minRotationX, maxRotationX));
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                clickedX = null;
                clickedY = null;
                break;
            }
        }

        return true;
    }

    private int limit(int value, int min, int max) {
        if (value > max) {
            value = max;
        }
        if (value < min) {
            value = min;
        }
        return value;
    }
}