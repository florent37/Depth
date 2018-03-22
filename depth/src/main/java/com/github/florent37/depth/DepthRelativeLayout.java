package com.github.florent37.depth;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.RelativeLayout;

import com.gihub.florent37.depth.R;

public class DepthRelativeLayout extends RelativeLayout implements DepthLayout {

    public static final int DEFAULT_EDGE_COLOR = Color.WHITE;
    public static final int DEFAULT_THICKNESS = 2;
    private final DepthManager depthManager;

    public static class DepthManager {
        public final PointF topLeft = new PointF(0, 0);
        public final PointF topRight = new PointF(0, 0);
        public final PointF bottomLeft = new PointF(0, 0);
        public final PointF bottomRight = new PointF(0, 0);
        public final PointF topLeftBack = new PointF(0, 0);
        public final PointF topRightBack = new PointF(0, 0);
        public final PointF bottomLeftBack = new PointF(0, 0);
        public final PointF bottomRightBack = new PointF(0, 0);

        public final CustomShadow customShadow;

        public Paint edgePaint = new Paint();
        public float[] prevSrc = new float[8];
        public int depthIndex = 0;
        public float depth;
        public boolean isCircle = false;

        private final View view;

        public DepthManager(View view) {
            this.view = view;
            customShadow = new CustomShadow(view);
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

        public Paint getEdgePaint() {
            return edgePaint;
        }
    }

    public DepthRelativeLayout(Context context) {
        super(context);
        depthManager = new DepthManager(this);
        initView(null);
    }

    public DepthRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        depthManager = new DepthManager(this);
        initView(attrs);

    }

    public DepthRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        depthManager = new DepthManager(this);
        initView(attrs);
    }

    public int getDepthIndex() {
        return this.depthManager.depthIndex;
    }

    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }

    public Paint getEdgePaint() {
        return this.depthManager.edgePaint;
    }

    public void setEdgePaint(Paint edgePaint) {
        this.depthManager.edgePaint = edgePaint;
    }

    private void initView(AttributeSet attrs) {

        setLayerType(LAYER_TYPE_HARDWARE, null);


        depthManager.edgePaint.setColor(DEFAULT_EDGE_COLOR);
        depthManager.edgePaint.setAntiAlias(true);
        if (attrs != null) {
            final TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.DepthRelativeLayout);
            depthManager.edgePaint.setColor(arr.getInt(R.styleable.DepthRelativeLayout_depth_edgeColor, DEFAULT_EDGE_COLOR));
            depthManager.setIsCircle(arr.getBoolean(R.styleable.DepthRelativeLayout_depth_isCircle, false));
            depthManager.depth = arr.getDimension(R.styleable.DepthRelativeLayout_depth_value, DEFAULT_THICKNESS * getResources().getDisplayMetrics().density);
            depthManager.depthIndex = arr.getInteger(R.styleable.DepthRelativeLayout_depth_zIndex, depthManager.depthIndex);
            setCustomShadowElevation(arr.getDimension(R.styleable.DepthRelativeLayout_depth_elevation, 0));
            arr.recycle();
        } else {
            depthManager.edgePaint.setColor(DEFAULT_EDGE_COLOR);
            depthManager.depth = DEFAULT_THICKNESS * getResources().getDisplayMetrics().density;
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
        return depthManager.depth;
    }

    public void setDepth(float depth) {
        this.depthManager.depth = depth;
        ((View) getParent()).invalidate();
    }



    public CustomShadow getCustomShadow() {
        return depthManager.customShadow;
    }

    @Override
    public DepthManager getDepthManager() {
        return depthManager;
    }

    public float getCustomShadowElevation() {
        return this.depthManager.customShadow.getCustomShadowElevation();
    }

    public void setCustomShadowElevation(float customShadowElevation) {
        this.depthManager.customShadow.setCustomShadowElevation(customShadowElevation);
        if (getParent() instanceof View) {
            ((View) getParent()).invalidate();
        }
    }
}
