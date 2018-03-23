package com.github.florent37.depth.depthview;

import com.github.florent37.depth.DepthManager;

public interface DepthLayout {

    int getWidth();

    int getHeight();

    DepthManager getDepthManager();

    float getRotationY();

    float getRotationX();

    void setDepth(float depth);
}
