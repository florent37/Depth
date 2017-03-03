package com.github.florent37.depth.anim;

import android.app.Activity;
import android.app.Fragment;

/**
 * Created by florentchampigny on 03/03/2017.
 */

public interface FragmentManager {
    void addFragment(Activity activity, int fragmentContainer, Fragment fragment);

    void removeFragment(Activity activity, Fragment fragment);
}
