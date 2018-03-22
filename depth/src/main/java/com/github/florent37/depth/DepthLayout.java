package com.github.florent37.depth;

import android.graphics.Paint;
import android.graphics.PointF;

public interface DepthLayout {

    int getWidth();
    int getHeight();

    DepthRelativeLayout.CustomShadow getCustomShadow();

    float getRotationY();
    float getRotationX();

    PointF getTopLeftBack();
    PointF getTopRightBack();
    PointF getBottomRightBack();
    PointF getBottomLeftBack();

    Paint getEdgePaint();

    boolean isCircle();

    boolean calculateBounds();

    PointF getTopRight();
    PointF getBottomRight();
    PointF getTopLeft();
    PointF getBottomLeft();
}
