package com.github.florent37.depth;

public interface DepthLayout {

    int getWidth();

    int getHeight();

    DepthManager getDepthManager();

    float getRotationY();

    float getRotationX();

    void setDepth(float depth);
}
