package no.agens.depth;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.github.florent37.depth.anim.Depth;
import com.github.florent37.depth.anim.DepthProvider;


public class RootActivity extends Activity implements Callback {

    private Depth depth;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        depth = DepthProvider.getDepth(this);

        //  depth.setFragmentManager(new Depth.FragmentManager() {
        //      @Override
        //      public void addFragment(Activity activity, int fragmentContainer, Fragment fragment) {
        //          activity.getFragmentManager().beginTransaction()
        //                  .replace(fragmentContainer, fragment)
        //                  .commitAllowingStateLoss();
        //      }
        //
        //      @Override
        //      public void removeFragment(Activity activity, Fragment fragment) {
        //          activity.getFragmentManager().beginTransaction()
        //                  .remove(fragment)
        //                  .commitAllowingStateLoss();
        //      }
        //  });

        setContentView(R.layout.activity_root);
        makeAppFullscreen();
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.fragment_container, Fragment1.newInstance(false)).commit();
        }
    }

    private void makeAppFullscreen() {
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    @Override
    public void changeFragment(final Fragment oldFragment) {
        final Fragment newFragment = (count++ %2 == 0) ? Fragment1.newInstance(true) : Fragment2.newInstance(true);

        depth
                .animate()
                .reduce(oldFragment)
                .exit(oldFragment)
                .start();
    }

    @Override
    public void openResetFragment(final Fragment fragment) {
        depth
                .animate()
                .reduce(fragment)
                .revert(fragment)
                .start();
    }

}
