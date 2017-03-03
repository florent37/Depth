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

    public DepthAnimator(Depth depth) {
        this.depth = depth;
        this.animations = new ArrayList<>();
    }

    public DepthAnimator reduce(Fragment fragment) {
        animations.add(new ReduceAnimation().setDepthLayout(getDepthLayout(fragment)));
        return this;
    }

    public DepthAnimator insertFragment(Fragment fragment) {
        animations.add(new EnterAnimation().setDepthLayout(getDepthLayout(fragment)));
        return this;
    }

    public DepthAnimator revert(Fragment fragment) {
        animations.add(new RevertAnimation().setDepthLayout(getDepthLayout(fragment)));
        return this;
    }

    public DepthAnimator exit(Fragment fragment) {
        animations.add(new ExitAnimation().setDepthLayout(getDepthLayout(fragment)));
        return this;
    }

    private DepthLayout getDepthLayout(Fragment fragment){
        return (DepthLayout) fragment.getView().findViewById(R.id.root_depth_layout);
    }

    public void start() {
        //setup listeners
        for (int i = 0; i < animations.size() - 1; ++i) { //-1
            final DepthAnimation thisAnimation = animations.get(i);
            final DepthAnimation nextAnimation = animations.get(i + 1);
            thisAnimation.setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    nextAnimation.start();
                }
            });
        }

        animations.get(0).start();
    }
}
