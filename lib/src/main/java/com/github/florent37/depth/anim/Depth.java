package com.github.florent37.depth.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Fragment;
import android.view.View;
import android.view.ViewTreeObserver;

import no.agens.depth.lib.DepthLayout;
import no.agens.depth.lib.R;

/**
 * Created by florentchampigny on 02/03/2017.
 */

public class Depth {
    private static final int root_depth_layout = R.id.root_depth_layout;
    private FragmentManager fragmentManager = new FragmentManagerImpl();

    public static View setup(/*0-20*/ float depth, /*0-20*/ float elevation, View view) {
        return new FragmentDepthView(view, depth, elevation);
    }

    public static void animateEnter(Fragment fragment) {
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

    public void changeFragment(final Fragment oldFragment, final int fragment_container, final Fragment newFragment) {
        final DepthLayout depthLayout = (DepthLayout) oldFragment.getView().findViewById(root_depth_layout);
        final Activity activity = oldFragment.getActivity();

        new ReduceAnimation()
                .setDepthLayout(depthLayout)
                .start(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        fragmentManager.addFragment(activity, fragment_container, newFragment);
                        new ExitAnimation()
                                .setDepthLayout(depthLayout)
                                .start(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        fragmentManager.removeFragment(activity, oldFragment);
                                    }
                                });
                    }
                });
    }

    public void openResetFragment(final Fragment fragment) {
        final DepthLayout depthLayout = (DepthLayout) fragment.getView().findViewById(root_depth_layout);

        new ReduceAnimation()
                .setDepthLayout(depthLayout)
                .start(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        new RevertAnimation()
                                .setDepthLayout(depthLayout)
                                .start();
                    }
                });
    }

    public interface FragmentManager {
        void addFragment(Activity activity, int fragmentContainer, Fragment fragment);

        void removeFragment(Activity activity, Fragment fragment);
    }

    public class FragmentManagerImpl implements FragmentManager {

        @Override
        public void addFragment(Activity activity, int fragmentContainer, Fragment fragment) {
            activity.getFragmentManager().beginTransaction().add(fragmentContainer, fragment).commitAllowingStateLoss();
        }

        @Override
        public void removeFragment(Activity activity, Fragment fragment) {
            activity.getFragmentManager().beginTransaction().remove(fragment).commit();
        }

    }
}
