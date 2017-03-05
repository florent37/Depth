package com.github.florent37.depth.animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

import com.github.florent37.depth.DepthLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florentchampigny on 03/03/2017.
 */

public abstract class DepthAnimation<THIS> {

    protected DepthLayout depthLayout;
    protected List<Animator> animators = new ArrayList<>();

    protected Animator.AnimatorListener listener = new AnimatorListenerAdapter() {};

    public THIS setDepthLayout(DepthLayout depthLayout) {
        this.depthLayout = depthLayout;
        return (THIS) this;
    }

    public THIS setListener(Animator.AnimatorListener listener) {
        this.listener = listener;
        return (THIS)this;
    }

    public abstract void prepareAnimators(DepthLayout target);

    protected void add(Animator animator){
        this.animators.add(animator);
    }

    public void start(){
        prepareAnimators(depthLayout);

        for(Animator animator : animators){
            animator.start();
        }
    }

}
