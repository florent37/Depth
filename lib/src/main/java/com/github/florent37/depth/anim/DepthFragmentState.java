package com.github.florent37.depth.anim;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

/**
 * Created by florentchampigny on 03/03/2017.
 */

public class DepthFragmentState {
    private final WeakReference<Fragment> fragmentReference;
    private boolean isReady = false;

    public DepthFragmentState(Fragment fragment) {
        this.fragmentReference = new WeakReference<>(fragment);
        this.isReady = fragment.getView() != null;
    }

    public void reloadReady() {
        final Fragment fragment = fragmentReference.get();
        if (fragment != null) {
            this.isReady = fragment.getView() != null;
        }
    }

    public boolean isReady() {
        return isReady;
    }

    @Nullable
    public Fragment getFragment() {
        return fragmentReference.get();
    }
}
