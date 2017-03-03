package com.github.florent37.depth.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v4.app.Fragment;

import com.gihub.florent37.depth.R;
import com.github.florent37.depth.anim.animations.DepthAnimation;
import com.github.florent37.depth.anim.animations.EnterAnimation;
import com.github.florent37.depth.anim.animations.EnterConfiguration;
import com.github.florent37.depth.anim.animations.ExitAnimation;
import com.github.florent37.depth.anim.animations.ExitConfiguration;
import com.github.florent37.depth.anim.animations.ReduceAnimation;
import com.github.florent37.depth.anim.animations.ReduceConfiguration;
import com.github.florent37.depth.anim.animations.RevertAnimation;
import com.github.florent37.depth.anim.animations.RevertConfiguration;

import java.util.ArrayList;
import java.util.List;

import no.agens.depth.lib.DepthLayout;

/**
 * Created by florentchampigny on 03/03/2017.
 */

public class DepthAnimator {

    private Depth depth;
    private List<DepthAnimation> animations;
    private int currentIndex = -1;

    private List<DepthFragmentState> fragmentsState;

    public DepthAnimator(Depth depth) {
        this.depth = depth;
        this.animations = new ArrayList<>();
        this.fragmentsState = new ArrayList<>();
    }

    public DepthAnimator reduce(Fragment fragment, ReduceConfiguration reduceConfiguration) {
        this.add(new ReduceAnimation().setReduceConfiguration(reduceConfiguration), fragment);
        return this;
    }

    public DepthAnimator reduce(Fragment fragment) {
        return reduce(fragment, null);
    }

    public DepthAnimator enter(Fragment fragment, EnterConfiguration enterConfiguration) {
        this.add(new EnterAnimation().setEnterConfiguration(enterConfiguration), fragment);
        return this;
    }

    public DepthAnimator enter(Fragment fragment) {
        return enter(fragment, null);
    }

    public DepthAnimator revert(Fragment fragment, RevertConfiguration revertConfiguration) {
        this.add(new RevertAnimation().setRevertConfiguration(revertConfiguration), fragment);
        return this;
    }

    public DepthAnimator revert(Fragment fragment) {
        return revert(fragment, null);
    }

    public DepthAnimator exit(Fragment fragment, ExitConfiguration exitConfiguration) {
        this.add(new ExitAnimation().setExitConfiguration(exitConfiguration), fragment);
        return this;
    }

    public DepthAnimator exit(Fragment fragment) {
        return exit(fragment, null);
    }

    private void add(DepthAnimation depthAnimation, Fragment fragment) {
        animations.add(depthAnimation);
        fragmentsState.add(new DepthFragmentState(fragment));
    }

    private DepthLayout findDepthLayout(Fragment fragment) {
        return (DepthLayout) fragment.getView().findViewById(R.id.root_depth_layout);
    }

    private void afterAnimationEnd(int index) {
        depth.notifyListenersEnd(animations.get(index), fragmentsState.get(index).getFragment());
        this.currentIndex = index + 1;
        if (currentIndex < animations.size()) {
            startAnimation(currentIndex);
        } else {
            depth.onAnimationFinished();
        }
    }

    void reloadFragmentsState() {
        for (DepthFragmentState depthFragmentState : fragmentsState) {
            depthFragmentState.reloadReady();
        }
        startAnimation(currentIndex);
    }

    private void startAnimation(final int index) {
        this.currentIndex = index;
        final DepthFragmentState depthFragmentState = fragmentsState.get(index);

        final Fragment fragment = depthFragmentState.getFragment();
        if (fragment != null) {
            final boolean ready = depthFragmentState.isReady();
            if (ready) {

                final DepthAnimation animation = animations.get(index);
                animation.setDepthLayout(findDepthLayout(fragment));
                animation.setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        afterAnimationEnd(index);
                    }
                });
                animation.start();
            } else {
                depth.addFragment(depthFragmentState.getFragment());
            }
        }
    }

    public void start() {
        //setup listeners
        if (!animations.isEmpty() && currentIndex == -1) {
            final int index = 0;
            startAnimation(index);
        }
    }
}
