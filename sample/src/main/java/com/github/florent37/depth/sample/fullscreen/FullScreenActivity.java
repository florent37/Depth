package com.github.florent37.depth.sample.fullscreen;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.github.florent37.depth.anim.Depth;
import com.github.florent37.depth.anim.DepthProvider;
import com.github.florent37.depth.anim.animations.EnterConfiguration;
import com.github.florent37.depth.anim.animations.ExitConfiguration;
import com.github.florent37.depth.anim.animations.ReduceConfiguration;
import com.github.florent37.depth.sample.R;

public class FullScreenActivity extends Activity implements FragmentCallback {

    int count = 0;
    private Depth depth;

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

        setContentView(R.layout.activity_fullscreen);
        makeAppFullscreen();
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.fragment_container, Fragment1.newInstance(false)).commit();
        }

        depth.setFragmentContainer(R.id.fragment_container);
    }

    private void makeAppFullscreen() {
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    @Override
    public void changeFragment(final Fragment oldFragment) {
        final Fragment newFragment = (++count % 2 == 0) ? Fragment1.newInstance(true) : Fragment2.newInstance(true);

        switch (count % 3) {
            case 0:
                animateDefault(oldFragment, newFragment);
                break;
            case 1:
                animateOnTop(oldFragment, newFragment);
                break;
            case 2:
                animateOnLeft(oldFragment, newFragment);
                break;
        }
    }

    private void animateDefault(final Fragment oldFragment, final Fragment newFragment){
        depth
                .animate()
                .reduce(oldFragment)

                .exit(oldFragment)

                .enter(newFragment)
                .start();
    }

    private void animateOnTop(final Fragment oldFragment, final Fragment newFragment){
        depth
                .animate()
                .reduce(oldFragment, new ReduceConfiguration()
                        .setRotationZ(0f)
                        .setRotationX(30f)
                )

                .exit(oldFragment, new ExitConfiguration()
                        .setFinalXPercent(0f)
                        .setFinalYPercent(-1f)
                )
                .enter(newFragment, new EnterConfiguration()
                        .setInitialXPercent(0f)
                        .setInitialYPercent(1f)
                        .setInitialRotationZ(0f)
                        .setInitialRotationX(30f)
                )
                .start();
    }

    private void animateOnLeft(final Fragment oldFragment, final Fragment newFragment){
        depth
                .animate()
                .reduce(oldFragment, new ReduceConfiguration()
                        .setRotationZ(0f)
                        .setRotationX(30f)
                )

                .exit(oldFragment, new ExitConfiguration()
                        .setFinalXPercent(-1f)
                        .setFinalYPercent(0f)
                )
                .enter(newFragment, new EnterConfiguration()
                        .setInitialXPercent(1f)
                        .setInitialYPercent(0f)
                        .setInitialRotationZ(0f)
                        .setInitialRotationX(30f)
                )
                .start();
    }

    @Override
    public void openResetFragment(final Fragment fragment) {
        depth
                .animate()
                .reduce(fragment, new ReduceConfiguration().setScale(0.5f))
                .revert(fragment)
                .start();
    }

}
