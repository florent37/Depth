package com.github.florent37.depth.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.annotation.FloatRange;
import android.view.View;
import android.view.ViewTreeObserver;

import com.gihub.florent37.depth.R;

import java.lang.ref.WeakReference;

/**
 * Created by florentchampigny on 02/03/2017.
 */

public class Depth {
    private FragmentManager fragmentManager = new FragmentManagerImpl();

    private DepthAnimator depthAnimator;
    private int fragmentContainer;
    private WeakReference<Activity> contextReference;

    Depth(Context context) {
        if (context instanceof Activity) {
            contextReference = new WeakReference<>((Activity) context);
        }
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
        this.depthAnimator = new DepthAnimator(this);
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
            final Activity activity = contextReference.get();
            if (contextReference != null) {
                fragmentManager.addFragment(activity, fragmentContainer, fragment);
            }
        }
    }

    void onAnimationFinished() {
        depthAnimator = null;
    }
}
