package com.github.florent37.depth.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.View;
import android.view.ViewTreeObserver;

import java.lang.ref.WeakReference;

import no.agens.depth.lib.DepthLayout;
import no.agens.depth.lib.R;

/**
 * Created by florentchampigny on 02/03/2017.
 */

public class Depth {
    private static final int root_depth_layout = R.id.root_depth_layout;
    private FragmentManager fragmentManager = new FragmentManagerImpl();

    private DepthAnimator depthAnimator;
    private int fragmentContainer;
    private WeakReference<Activity> contextReference;

    Depth(Context context) {
        if (context instanceof Activity) {
            contextReference = new WeakReference<>((Activity) context);
        }
    }

    public View setupFragment(/*0-20*/ float depth, /*0-20*/ float elevation, View view) {
        return new FragmentDepthView(view, depth, elevation);
    }

    public Depth setFragmentContainer(int fragmentContainer) {
        this.fragmentContainer = fragmentContainer;
        return this;
    }

    public void animateEnter(Fragment fragment) {
        final View view = fragment.getView();
        if (view != null) {
            final DepthLayout depthLayout = (DepthLayout) view.findViewById(root_depth_layout);
            if (depthLayout != null) {
                view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        new EnterAnimation()
                                .setDepthLayout(depthLayout)
                                .start();
                    }
                });
            }
        }

    }

    public Depth setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        return this;
    }

    private void changeFragment(final Fragment oldFragment, final int fragment_container, final Fragment newFragment) {
        final DepthLayout depthLayout = (DepthLayout) oldFragment.getView().findViewById(root_depth_layout);
        final Activity activity = oldFragment.getActivity();

        new ReduceAnimation()
                .setDepthLayout(depthLayout)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        fragmentManager.addFragment(activity, fragment_container, newFragment);
                        new ExitAnimation()
                                .setDepthLayout(depthLayout)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        fragmentManager.removeFragment(activity, oldFragment);
                                    }
                                })
                                .start();
                    }
                })
                .start();
    }

    private void changeFragmentWithoutRotation(final Fragment oldFragment, final int fragment_container, final Fragment newFragment) {
        final DepthLayout depthLayout = (DepthLayout) oldFragment.getView().findViewById(root_depth_layout);
        final Activity activity = oldFragment.getActivity();

        new ReduceAnimation()
                .setDepthLayout(depthLayout)

                .setFinalRotationZ(0f)
                .setFinalRotationX(0f)

                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        fragmentManager.addFragment(activity, fragment_container, newFragment);
                        new ExitAnimation()

                                .setFinalXPercent(0f)
                                .setFinalYPercent(-1f)

                                .setDepthLayout(depthLayout)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        fragmentManager.removeFragment(activity, oldFragment);
                                    }
                                })
                                .start();
                    }
                })
                .start();
    }

    private void openResetFragment(final Fragment fragment) {
        final DepthLayout depthLayout = (DepthLayout) fragment.getView().findViewById(root_depth_layout);

        new ReduceAnimation()
                .setDepthLayout(depthLayout)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        new RevertAnimation()
                                .setDepthLayout(depthLayout)
                                .start();
                    }
                }).start();
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
