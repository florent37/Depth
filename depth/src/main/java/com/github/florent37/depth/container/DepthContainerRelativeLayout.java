package com.github.florent37.depth.container;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.gihub.florent37.depth.R;
import com.github.florent37.depth.CustomShadow;
import com.github.florent37.depth.depthview.DepthLayout;

public class DepthContainerRelativeLayout extends RelativeLayout {

    private final DepthMotionHandler motionHandler;

    private final DepthContainerManager depthContainerManager;

    public DepthContainerRelativeLayout(Context context) {
        super(context);
        motionHandler = new DepthMotionHandler(this);
        depthContainerManager = new DepthContainerManager(this);
        depthContainerManager.setup();
    }

    public DepthContainerRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        motionHandler = new DepthMotionHandler(this);
        depthContainerManager = new DepthContainerManager(this);
        depthContainerManager.setup();
    }

    public DepthContainerRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        motionHandler = new DepthMotionHandler(this);
        depthContainerManager = new DepthContainerManager(this);
        depthContainerManager.setup();
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        if (!isInEditMode()) {
            depthContainerManager.drawChild(canvas, child, drawingTime);
        }
        return super.drawChild(canvas, child, drawingTime);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return motionHandler.onTouchEvent(event);
    }
}
