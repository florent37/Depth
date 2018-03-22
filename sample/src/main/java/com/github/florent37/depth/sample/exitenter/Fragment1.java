package com.github.florent37.depth.sample.exitenter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.florent37.depth.Depth;
import com.github.florent37.depth.DepthListener;
import com.github.florent37.depth.DepthProvider;
import com.github.florent37.depth.animations.DepthAnimation;
import com.github.florent37.depth.sample.R;

public class Fragment1 extends Fragment {

    public static Fragment newInstance(boolean animateEnter) {
        final Bundle args = new Bundle();
        args.putBoolean("animateEnter", animateEnter);
        final Fragment waterFragment = new Fragment1();
        waterFragment.setArguments(args);
        return waterFragment;
    }

    private Depth depth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.depth = DepthProvider.getDepth(container);
        return depth.setupFragment(10f, 10f, inflater.inflate(R.layout.depth_fragment_1, container, false));
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        depth.onFragmentReady(this);

        depth.addListener(new DepthListener() {
            @Override
            public void onAnimationEnd(DepthAnimation depthAnimation, Fragment fragment) {
                Toast.makeText(getActivity(), depthAnimation.getClass().getCanonicalName(), Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FragmentCallback) getActivity()).openResetFragment(Fragment1.this);
            }
        });
    }

}
