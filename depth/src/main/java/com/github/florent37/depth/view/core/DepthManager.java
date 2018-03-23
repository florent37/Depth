package com.github.florent37.depth.view.core;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Build;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.github.florent37.depth.CustomShadow;

import java.util.ArrayList;
import java.util.List;

public class DepthManager {
    public static final int DEFAULT_EDGE_COLOR = Color.WHITE;
    public static final int DEFAULT_THICKNESS = 2;

    public final PointF topLeft = new PointF(0, 0);
    public final PointF topRight = new PointF(0, 0);
    public final PointF bottomLeft = new PointF(0, 0);
    public final PointF bottomRight = new PointF(0, 0);
    public final PointF topLeftBack = new PointF(0, 0);
    public final PointF topRightBack = new PointF(0, 0);
    public final PointF bottomLeftBack = new PointF(0, 0);
    public final PointF bottomRightBack = new PointF(0, 0);

    public final CustomShadow customShadow;
    private final View view;
    public Paint edgePaint = new Paint();
    public float[] prevSrc = new float[8];
    public int depthIndex = 0;
    public float depth;
    public boolean isCircle = false;

    public DepthManager(View view) {
        this.view = view;
        customShadow = new CustomShadow(view);
    }

    public void init(int edgeColor, boolean isCircle, float depth, int depthIndex, float elevation, boolean autoAnimate) {
        view.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        edgePaint.setColor(DEFAULT_EDGE_COLOR);
        edgePaint.setAntiAlias(true);

        if (edgeColor != -1) {
            this.edgePaint.setColor(edgeColor);
        }
        this.setIsCircle(isCircle);
        if (depth != -1) {
            this.setDepth(depth);
        } else {
            this.setDepth(DEFAULT_THICKNESS * view.getResources().getDisplayMetrics().density);
        }
        if (depthIndex != -1) {
            this.depthIndex = depthIndex;
        }
        setCustomShadowElevation(elevation);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {

                }
            });
        }

        autoAnimate(autoAnimate);
    }

    public PointF getTopLeft() {
        return topLeft;
    }

    public PointF getTopRight() {
        return topRight;
    }

    public PointF getBottomLeft() {
        return bottomLeft;
    }

    public PointF getBottomRight() {
        return bottomRight;
    }

    public PointF getTopLeftBack() {
        return topLeftBack;
    }

    public PointF getTopRightBack() {
        return topRightBack;
    }

    public PointF getBottomLeftBack() {
        return bottomLeftBack;
    }

    public PointF getBottomRightBack() {
        return bottomRightBack;
    }

    boolean hasMatrixChanged(float[] newSrc) {
        for (int i = 0; i < 8; i++) {
            if (newSrc[i] != prevSrc[i])
                return true;
        }
        return false;
    }

    public boolean isCircle() {
        return isCircle;
    }

    public void setIsCircle(boolean isCircle) {
        this.isCircle = isCircle;
    }

    public boolean calculateBounds() {

        float[] src = new float[8];
        float[] dst = new float[]{0, 0, view.getWidth(), 0, 0, view.getHeight(), view.getWidth(), view.getHeight()};
        final Matrix matrix = view.getMatrix();

        matrix.mapPoints(src, dst);
        topLeft.x = src[0] + view.getLeft();
        topLeft.y = src[1] + view.getTop();
        topRight.x = src[2] + view.getLeft();
        topRight.y = src[3] + view.getTop();

        bottomLeft.x = src[4] + view.getLeft();
        bottomLeft.y = src[5] + view.getTop();
        bottomRight.x = src[6] + view.getLeft();
        bottomRight.y = src[7] + view.getTop();
        final boolean returnValue = hasMatrixChanged(src);
        prevSrc = src;
        float percentFrom90X = (view.getRotationX()) / 90f;
        float percentFrom90Y = (-view.getRotationY()) / 90f;


        matrix.postTranslate(percentFrom90Y * getDepth(), percentFrom90X * getDepth());
        src = new float[8];
        dst = new float[]{0, 0, view.getWidth(), 0, 0, view.getHeight(), view.getWidth(), view.getHeight()};
        matrix.mapPoints(src, dst);

        topLeftBack.x = src[0] + view.getLeft();
        topLeftBack.y = src[1] + view.getTop();
        topRightBack.x = src[2] + view.getLeft();
        topRightBack.y = src[3] + view.getTop();

        bottomLeftBack.x = src[4] + view.getLeft();
        bottomLeftBack.y = src[5] + view.getTop();
        bottomRightBack.x = src[6] + view.getLeft();
        bottomRightBack.y = src[7] + view.getTop();
        this.customShadow.calculateBounds();

        return returnValue;
    }

    public float getDepth() {
        return depth;
    }

    public void setDepth(float depth) {
        this.depth = depth;
        if (view.getParent() instanceof View) {
            ((View) view.getParent()).invalidate();
        }
    }

    public Paint getEdgePaint() {
        return edgePaint;
    }

    public void setEdgePaint(Paint edgePaint) {
        this.edgePaint = edgePaint;
    }

    public int getDepthIndex() {
        return depthIndex;
    }

    public CustomShadow getCustomShadow() {
        return customShadow;
    }

    public void setCustomShadowElevation(float customShadowElevation) {
        this.customShadow.setCustomShadowElevation(customShadowElevation);
        if (view.getParent() instanceof View) {
            ((View) view.getParent()).invalidate();
        }
    }

    private List<Animator> animators = new ArrayList<>();

    public void autoAnimate(boolean animate) {
        if(animate) {
            view.post(new Runnable() {
                @Override
                public void run() {
                    final ObjectAnimator rotationY = ObjectAnimator.ofFloat(view, View.ROTATION_Y, 25, 0, -25);
                    rotationY.setDuration(1000);
                    rotationY.setRepeatCount(ValueAnimator.INFINITE);
                    rotationY.setRepeatMode(ValueAnimator.REVERSE);
                    rotationY.start();

                    animators.add(rotationY);

                    final ObjectAnimator rotationX = ObjectAnimator.ofFloat(view, View.ROTATION_X, -25, 0, 25);
                    rotationX.setDuration(2500);
                    rotationX.setRepeatCount(ValueAnimator.INFINITE);
                    rotationX.setRepeatMode(ValueAnimator.REVERSE);
                    rotationX.start();

                    animators.add(rotationX);
                }
            });
        } else {
            for (Animator animator : animators) {
                animator.cancel();
            }
        }
    }
}
