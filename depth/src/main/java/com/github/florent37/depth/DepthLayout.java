package com.github.florent37.depth;

public interface DepthLayout {

    int getWidth();

    int getHeight();

    CustomShadow getCustomShadow();

    DepthRelativeLayout.DepthManager getDepthManager();

    float getRotationY();

    float getRotationX();
}
