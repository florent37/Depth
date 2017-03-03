package com.github.florent37.depth.anim;

import android.app.Activity;
import android.app.Fragment;

/**
 * Created by florentchampigny on 03/03/2017.
 */

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
