package com.github.florent37.depth;

import android.support.v4.app.Fragment;

import com.github.florent37.depth.animations.DepthAnimation;

/**
 * Created by florentchampigny on 03/03/2017.
 */

public interface DepthListener {
    void onAnimationEnd(DepthAnimation depthAnimation, Fragment fragment);
}
