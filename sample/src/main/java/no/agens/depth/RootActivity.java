package no.agens.depth;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.github.florent37.depth.anim.Depth;
import com.github.florent37.depth.anim.DepthBus;


public class RootActivity extends Activity implements Callback {

    private Depth depth;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        depth = DepthBus.getDepth(this);

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
        count++;
        if(count % 2 == 0) {
            depth.changeFragment(oldFragment, R.id.fragment_container, Fragment1.newInstance(true));
        } else {
            depth.changeFragment(oldFragment, R.id.fragment_container, Fragment2.newInstance(true));
        }
    }

    @Override
    public void openResetFragment(final Fragment fragment) {
        depth.openResetFragment(fragment);
    }

}
