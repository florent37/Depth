package com.github.florent37.depth;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.view.View;

public class CustomShadow {

    private static final float DEFAULT_SHADOW_PADDING = 10f;

    private final View view;

    private PointF topLeftBack = new PointF(0, 0);
    private PointF topRightBack = new PointF(0, 0);
    private PointF bottomLeftBack = new PointF(0, 0);
    private PointF bottomRightBack = new PointF(0, 0);
    private int padding;
    private Matrix matrix = new Matrix();

    private float customShadowElevation;

    public CustomShadow(View view) {
        this.view = view;
    }

    public float getCustomShadowElevation() {
        return customShadowElevation;
    }

    public void setCustomShadowElevation(float customShadowElevation) {
        this.customShadowElevation = customShadowElevation * 3;
    }

    public boolean calculateBounds() {
        float[] src = new float[8];
        float density = view.getResources().getDisplayMetrics().density;
        float offsetY = customShadowElevation;
        float offsetX = customShadowElevation / 5;
        padding = (int) (customShadowElevation / 4f + DEFAULT_SHADOW_PADDING * density);

        float[] dst = new float[]{-padding, -padding, view.getWidth() + padding, -padding, -padding, view.getHeight() + padding, view.getWidth() + padding, view.getHeight() + padding};
        final Matrix matrix = view.getMatrix();
        matrix.mapPoints(src, dst);

        topLeftBack.x = src[0] + view.getLeft() + offsetX;
        topLeftBack.y = src[1] + view.getTop() + offsetY;
        topRightBack.x = src[2] + view.getLeft() + offsetX;
        topRightBack.y = src[3] + view.getTop() + offsetY;

        bottomLeftBack.x = src[4] + view.getLeft() + offsetX;
        bottomLeftBack.y = src[5] + view.getTop() + offsetY;
        bottomRightBack.x = src[6] + view.getLeft() + offsetX;
        bottomRightBack.y = src[7] + view.getTop() + offsetY;

        return false;
    }

    public void drawShadow(Canvas canvas, Drawable shadow) {
        shadow.setBounds(-padding, -padding, view.getWidth() + padding, view.getHeight() + padding);
        float[] src = new float[]{0, 0, view.getWidth(), 0, view.getWidth(), view.getHeight(), 0, view.getHeight()};
        float[] dst = new float[]{topLeftBack.x, topLeftBack.y, topRightBack.x, topRightBack.y, bottomRightBack.x, bottomRightBack.y, bottomLeftBack.x, bottomLeftBack.y};
        final int count = canvas.save();
        matrix.setPolyToPoly(src, 0, dst, 0, src.length >> 1);
        canvas.concat(matrix);
        shadow.draw(canvas);
        canvas.restoreToCount(count);
    }
}
