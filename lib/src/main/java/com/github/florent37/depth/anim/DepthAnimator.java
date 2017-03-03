package com.github.florent37.depth.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import no.agens.depth.lib.DepthLayout;
import no.agens.depth.lib.R;

/**
 * Created by florentchampigny on 03/03/2017.
 */

public class DepthAnimator {

    private Depth depth;
    private List<DepthAnimation> animations;
    private int currentIndex;

    private List<DepthFragmentState> fragmentsState;

    public DepthAnimator(Depth depth) {
        this.depth = depth;
        this.animations = new ArrayList<>();
        this.fragmentsState = new ArrayList<>();
    }

    public DepthAnimator reduce(Fragment fragment) {
        this.add(new ReduceAnimation(), fragment);
        return this;
    }

    public DepthAnimator enter(Fragment fragment) {
        this.add(new EnterAnimation(), fragment);
        return this;
    }

    public DepthAnimator revert(Fragment fragment) {
        this.add(new RevertAnimation(), fragment);
        return this;
    }

    public DepthAnimator exit(Fragment fragment) {
        this.add(new ExitAnimation(), fragment);
        return this;
    }

    private void add(DepthAnimation depthAnimation, Fragment fragment){
        animations.add(depthAnimation);
        fragmentsState.add(new DepthFragmentState(fragment));
    }

    private DepthLayout findDepthLayout(Fragment fragment) {
        return (DepthLayout) fragment.getView().findViewById(R.id.root_depth_layout);
    }

    private void afterAnimationEnd(int index) {
        this.currentIndex = index + 1;
        if(currentIndex < animations.size()) {
            startAnimation(currentIndex);
        } else{
            depth.onAnimationFinished();
        }
    }

    void reloadFragmentsState(){
        for (DepthFragmentState depthFragmentState : fragmentsState) {
            depthFragmentState.reloadReady();
        }
        startAnimation(currentIndex);
    }

    private void startAnimation(final int index){
        final DepthFragmentState depthFragmentState = fragmentsState.get(index);

        final Fragment fragment = depthFragmentState.getFragment();
        if(fragment != null){
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
        if (!animations.isEmpty()) {
            final int index = 0;
            startAnimation(index);
        }
    }
}
