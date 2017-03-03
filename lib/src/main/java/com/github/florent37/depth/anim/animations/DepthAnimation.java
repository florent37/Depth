package com.github.florent37.depth.anim.animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

import no.agens.depth.lib.DepthLayout;

/**
 * Created by florentchampigny on 03/03/2017.
 */

public abstract class DepthAnimation<THIS> {

    protected DepthLayout depthLayout;

    protected Animator.AnimatorListener listener = new AnimatorListenerAdapter() {};

    public THIS setDepthLayout(DepthLayout depthLayout) {
        this.depthLayout = depthLayout;
        return (THIS) this;
    }

    public THIS setListener(Animator.AnimatorListener listener) {
        this.listener = listener;
        return (THIS)this;
    }

    public abstract void start();

}
