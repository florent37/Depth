package com.github.florent37.depth;

import android.view.View;
import android.widget.FrameLayout;

import com.gihub.florent37.depth.R;

/**
 * Created by florentchampigny on 02/03/2017.
 */

public class FragmentDepthView extends FrameLayout {

    private final DepthRelativeLayout depthLayout;

    public FragmentDepthView(View fragmentView, Float depth, Float elevation) {
        super(fragmentView.getContext());
        inflate(getContext(), R.layout.fragment_depth_view, this);

        depthLayout = (DepthRelativeLayout) findViewById(R.id.root_depth_layout);
        if (depth != null) {
            depthLayout.setDepth(depth * getResources().getDisplayMetrics().density);
        }
        if (elevation != null) {
            depthLayout.setCustomShadowElevation(elevation * getResources().getDisplayMetrics().density);
        }

        depthLayout.addView(fragmentView);
    }

    public FragmentDepthView(View fragmentView) {
        this(fragmentView, null, null);
    }

}
