package no.agens.depth;

import android.app.Fragment;

/**
 * Created by florentchampigny on 02/03/2017.
 */

public interface Callback {
    void changeFragment(Fragment oldFragment);

    void openResetFragment(Fragment fragment);
}
