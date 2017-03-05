package com.github.florent37.depth;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * Created by florentchampigny on 03/03/2017.
 */

public class FragmentManagerImpl implements FragmentManager {

    @Override
    public void addFragment(FragmentActivity activity, int fragmentContainer, Fragment fragment) {
        activity.getSupportFragmentManager().beginTransaction().add(fragmentContainer, fragment).commitAllowingStateLoss();
    }

    @Override
    public void removeFragment(FragmentActivity activity, Fragment fragment) {
        activity.getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

}
