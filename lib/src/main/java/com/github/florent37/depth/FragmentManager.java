package com.github.florent37.depth;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * Created by florentchampigny on 03/03/2017.
 */

public interface FragmentManager {
    void addFragment(FragmentActivity activity, int fragmentContainer, Fragment fragment);

    void removeFragment(FragmentActivity activity, Fragment fragment);
}
