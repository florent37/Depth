package com.github.florent37.depth.sample.fullscreen;

import android.app.Fragment;

/**
 * Created by florentchampigny on 02/03/2017.
 */

public interface FragmentCallback {
    void changeFragment(Fragment oldFragment);

    void openResetFragment(Fragment fragment);
}
