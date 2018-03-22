package com.github.florent37.depth;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.RelativeLayout;

import com.gihub.florent37.depth.R;

public class DepthRelativeLayout extends RelativeLayout implements DepthLayout {

    public static final int DEFAULT_EDGE_COLOR = Color.WHITE;
    public static final int DEFAULT_THICKNESS = 2;
    private final CustomShadow customShadow;
    private final PointF topLeft = new PointF(0, 0);
    private final PointF topRight = new PointF(0, 0);
    private final PointF bottomLeft = new PointF(0, 0);
    private final PointF bottomRight = new PointF(0, 0);
    private final PointF topLeftBack = new PointF(0, 0);
    private final PointF topRightBack = new PointF(0, 0);
    private final PointF bottomLeftBack = new PointF(0, 0);
    private final PointF bottomRightBack = new PointF(0, 0);
    private Paint edgePaint = new Paint();
    private float[] prevSrc = new float[8];
    private int depthIndex = 0;
    private float depth;
    private boolean isCircle = false;

    public DepthRelativeLayout(Context context) {
        super(context);
        customShadow = new CustomShadow(this);
        initView(null);
    }

    public DepthRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        customShadow = new CustomShadow(this);
        initView(attrs);

    }

    public DepthRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        customShadow = new CustomShadow(this);
        initView(attrs);
    }

    public int getDepthIndex() {
        return depthIndex;
    }

    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }

    public Paint getEdgePaint() {
        return edgePaint;
    }

    public void setEdgePaint(Paint edgePaint) {
        this.edgePaint = edgePaint;
    }

    private void initView(AttributeSet attrs) {

        setLayerType(LAYER_TYPE_HARDWARE, null);

        edgePaint.setColor(DEFAULT_EDGE_COLOR);
        edgePaint.setAntiAlias(true);
        if (attrs != null) {
            final TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.DepthRelativeLayout);
            edgePaint.setColor(arr.getInt(R.styleable.DepthRelativeLayout_depth_edgeColor, DEFAULT_EDGE_COLOR));
            setIsCircle(arr.getBoolean(R.styleable.DepthRelativeLayout_depth_isCircle, false));
            depth = arr.getDimension(R.styleable.DepthRelativeLayout_depth_value, DEFAULT_THICKNESS * getResources().getDisplayMetrics().density);
            depthIndex = arr.getInteger(R.styleable.DepthRelativeLayout_depth_zIndex, depthIndex);
            setCustomShadowElevation(arr.getDimension(R.styleable.DepthRelativeLayout_depth_elevation, 0));
            arr.recycle();
        } else {
            edgePaint.setColor(DEFAULT_EDGE_COLOR);
            depth = DEFAULT_THICKNESS * getResources().getDisplayMetrics().density;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {

                }
            });
        }
    }

    public float getDepth() {
        return depth;
    }

    public void setDepth(float depth) {
        this.depth = depth;
        ((View) getParent()).invalidate();
    }

    public boolean isCircle() {
        return isCircle;
    }

    public void setIsCircle(boolean isCircle) {
        this.isCircle = isCircle;
    }

    public boolean calculateBounds() {

        float[] src = new float[8];
        float[] dst = new float[]{0, 0, getWidth(), 0, 0, getHeight(), getWidth(), getHeight()};
        Matrix matrix = getMatrix();

        matrix.mapPoints(src, dst);
        topLeft.x = src[0] + getLeft();
        topLeft.y = src[1] + getTop();
        topRight.x = src[2] + getLeft();
        topRight.y = src[3] + getTop();

        bottomLeft.x = src[4] + getLeft();
        bottomLeft.y = src[5] + getTop();
        bottomRight.x = src[6] + getLeft();
        bottomRight.y = src[7] + getTop();
        final boolean returnValue = hasMatrixChanged(src);
        prevSrc = src;
        float percentFrom90X = (getRotationX()) / 90f;
        float percentFrom90Y = (-getRotationY()) / 90f;


        matrix.postTranslate(percentFrom90Y * getDepth(), percentFrom90X * getDepth());
        src = new float[8];
        dst = new float[]{0, 0, getWidth(), 0, 0, getHeight(), getWidth(), getHeight()};
        matrix.mapPoints(src, dst);

        topLeftBack.x = src[0] + getLeft();
        topLeftBack.y = src[1] + getTop();
        topRightBack.x = src[2] + getLeft();
        topRightBack.y = src[3] + getTop();

        bottomLeftBack.x = src[4] + getLeft();
        bottomLeftBack.y = src[5] + getTop();
        bottomRightBack.x = src[6] + getLeft();
        bottomRightBack.y = src[7] + getTop();
        customShadow.calculateBounds(this);

        return returnValue;
    }

    boolean hasMatrixChanged(float[] newSrc) {
        for (int i = 0; i < 8; i++) {
            if (newSrc[i] != prevSrc[i])
                return true;
        }
        return false;
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

    public CustomShadow getCustomShadow() {
        return customShadow;
    }

    public float getCustomShadowElevation() {
        return this.customShadow.getCustomShadowElevation();
    }

    public void setCustomShadowElevation(float customShadowElevation) {
        this.customShadow.setCustomShadowElevation(customShadowElevation);
        if (getParent() instanceof View) {
            ((View) getParent()).invalidate();
        }
    }

    static class CustomShadow {

        private static final float DEFAULT_SHADOW_PADDING = 10f;

        private final View view;

        private PointF topLeftBack = new PointF(0, 0);
        private PointF topRightBack = new PointF(0, 0);
        private PointF bottomLeftBack = new PointF(0, 0);
        private PointF bottomRightBack = new PointF(0, 0);
        private int padding;
        private Matrix matrix = new Matrix();

        private float customShadowElevation;

        CustomShadow(View view) {
            this.view = view;
        }

        private float getCustomShadowElevation() {
            return customShadowElevation;
        }

        private void setCustomShadowElevation(float customShadowElevation) {
            this.customShadowElevation = customShadowElevation;
        }

        public boolean calculateBounds(DepthRelativeLayout target) {
            float[] src = new float[8];
            float density = view.getResources().getDisplayMetrics().density;
            float offsetY = customShadowElevation;
            float offsetX = customShadowElevation / 5;
            padding = (int) (customShadowElevation / 4f + DEFAULT_SHADOW_PADDING * density);

            float[] dst = new float[]{-padding, -padding, target.getWidth() + padding, -padding, -padding, target.getHeight() + padding, target.getWidth() + padding, target.getHeight() + padding};
            final Matrix matrix = view.getMatrix();
            matrix.mapPoints(src, dst);

            topLeftBack.x = src[0] + target.getLeft() + offsetX;
            topLeftBack.y = src[1] + target.getTop() + offsetY;
            topRightBack.x = src[2] + target.getLeft() + offsetX;
            topRightBack.y = src[3] + target.getTop() + offsetY;

            bottomLeftBack.x = src[4] + target.getLeft() + offsetX;
            bottomLeftBack.y = src[5] + target.getTop() + offsetY;
            bottomRightBack.x = src[6] + target.getLeft() + offsetX;
            bottomRightBack.y = src[7] + target.getTop() + offsetY;

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
}
