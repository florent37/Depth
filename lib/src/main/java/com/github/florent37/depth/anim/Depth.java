package com.github.florent37.depth.anim;

import android.content.Context;
import android.support.annotation.FloatRange;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewTreeObserver;

import com.github.florent37.depth.anim.animations.DepthAnimation;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by florentchampigny on 02/03/2017.
 */

public class Depth {
    private FragmentManager fragmentManager = new FragmentManagerImpl();

    private DepthAnimator depthAnimator;
    private int fragmentContainer;
    private WeakReference<FragmentActivity> contextReference;
    private List<WeakReference<DepthListener>> listeners;

    Depth(Context context) {
        if (context instanceof FragmentActivity) {
            contextReference = new WeakReference<>((FragmentActivity) context);
        }
        listeners = new ArrayList<>();
    }

    public View setupFragment(@FloatRange(from = 0, to = 20) float depth, @FloatRange(from = 0, to = 20) float elevation, View view) {
        return new FragmentDepthView(view, depth, elevation);
    }

    public View setupFragment(View view) {
        return new FragmentDepthView(view);
    }

    public Depth setFragmentContainer(int fragmentContainer) {
        this.fragmentContainer = fragmentContainer;
        return this;
    }

    public DepthAnimator animate() {
        if (depthAnimator == null) {
            this.depthAnimator = new DepthAnimator(this);
        }
        return depthAnimator;
    }

    public void onFragmentReady(Fragment fragment) {
        if (fragment != null) {
            final View view = fragment.getView();
            if (view != null) {
                view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        view.getViewTreeObserver().removeOnPreDrawListener(this);
                        if (depthAnimator != null) {
                            depthAnimator.reloadFragmentsState();
                        }
                        return false;
                    }
                });
            }
        }
    }

    public void addFragment(Fragment fragment) {
        if (contextReference != null) {
            final FragmentActivity activity = contextReference.get();
            if (contextReference != null) {
                fragmentManager.addFragment(activity, fragmentContainer, fragment);
            }
        }
    }

    void onAnimationFinished() {
        depthAnimator = null;
    }

    public void addListener(DepthListener depthListener) {
        this.listeners.add(new WeakReference<>(depthListener));
    }

    void notifyListenersEnd(DepthAnimation depthAnimation, Fragment fragment) {
        for (WeakReference<DepthListener> reference : listeners) {
            final DepthListener depthListener = reference.get();
            if (depthListener != null) {
                depthListener.onAnimationEnd(depthAnimation, fragment);
            }
        }
    }
}
