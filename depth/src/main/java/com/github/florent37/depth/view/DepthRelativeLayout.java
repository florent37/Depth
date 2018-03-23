package com.github.florent37.depth.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.gihub.florent37.depth.R;
import com.github.florent37.depth.view.core.DepthManager;
import com.github.florent37.depth.view.core.DepthLayout;

public class DepthRelativeLayout extends RelativeLayout implements DepthLayout {

    private final DepthManager depthManager;

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

    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }

    private void initView(AttributeSet attrs) {
        int edgeColor = -1;
        boolean isCircle = false;
        float depth = -1;
        int depthIndex = -1;
        float elevation = 0;
        boolean autoAnimate = false;

        if (attrs != null) {
            final TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.DepthRelativeLayout);
            edgeColor = arr.getInt(R.styleable.DepthRelativeLayout_depth_edgeColor, edgeColor);
            isCircle = arr.getBoolean(R.styleable.DepthRelativeLayout_depth_isCircle, isCircle);
            depth = arr.getDimension(R.styleable.DepthRelativeLayout_depth_value, depth);
            depthIndex = arr.getInt(R.styleable.DepthRelativeLayout_depth_zIndex, depthIndex);
            elevation = arr.getDimension(R.styleable.DepthRelativeLayout_depth_elevation, elevation);
            autoAnimate = arr.getBoolean(R.styleable.DepthRelativeLayout_depth_autoAnimate, autoAnimate);
            arr.recycle();
        }
        depthManager.init(edgeColor, isCircle, depth, depthIndex, elevation, autoAnimate);

    }

    @Override
    public DepthManager getDepthManager() {
        return depthManager;
    }

    public void setCustomShadowElevation(float customShadowElevation) {
        this.depthManager.setCustomShadowElevation(customShadowElevation);
    }

    @Override
    public void autoAnimate(boolean animate) {
        depthManager.autoAnimate(animate);
    }

    @Override
    public void setDepth(float depth) {
        this.depthManager.setDepth(depth);
    }
}
