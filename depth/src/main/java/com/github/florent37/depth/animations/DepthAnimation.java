package com.github.florent37.depth.animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.annotation.Nullable;
import android.view.View;

import com.github.florent37.depth.DepthRelativeLayout;
import com.github.florent37.depth.DepthRelativeLayoutContainer;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by florentchampigny on 03/03/2017.
 */

public abstract class DepthAnimation<THIS> {

    protected WeakReference<DepthRelativeLayoutContainer> depthLayoutContainer;
    protected List<Animator> animators = new ArrayList<>();
    protected boolean listenerAdded = false;

    protected WeakReference<Animator.AnimatorListener> listener = new WeakReference<Animator.AnimatorListener>(new AnimatorListenerAdapter() {
    });

    @Nullable
    protected DepthRelativeLayoutContainer getDepthLayoutContainer(){
        return depthLayoutContainer.get();
    }

    @Nullable
    protected Animator.AnimatorListener getListener(){
        return listener.get();
    }

    public THIS setDepthLayoutContainer(DepthRelativeLayoutContainer depthLayoutContainer) {
        this.depthLayoutContainer = new WeakReference<>(depthLayoutContainer);
        return (THIS) this;
    }

    public THIS setListener(Animator.AnimatorListener listener) {
        this.listener = new WeakReference<>(listener);
        return (THIS) this;
    }

    public abstract void prepareAnimators(DepthRelativeLayout target, int index, int animationDelay);

    protected void add(Animator animator) {
        this.animators.add(animator);
    }

    public void start() {
        final DepthRelativeLayoutContainer depthLayoutContainer = getDepthLayoutContainer();
        if (depthLayoutContainer != null) {

            for (int i = 0; i < depthLayoutContainer.getChildCount(); ++i) {
                final View child = depthLayoutContainer.getChildAt(i);
                if (child instanceof DepthRelativeLayout) {
                    final DepthRelativeLayout depthRelativeLayout = (DepthRelativeLayout) child;
                    prepareAnimators((DepthRelativeLayout) child, depthRelativeLayout.getDepthIndex(), depthRelativeLayout.getAnimationDelay());
                }
            }

            for (Animator animator : animators) {
                animator.start();
            }
        }
    }

    protected void attachListener(Animator animator){
        if(!listenerAdded){
            listenerAdded = true;
            animator.addListener(getListener());
        }
    }

    public void clear() {
        this.depthLayoutContainer.clear();
        this.listener.clear();
        this.animators.clear();
    }
}
