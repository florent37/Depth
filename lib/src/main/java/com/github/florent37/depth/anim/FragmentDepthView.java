package com.github.florent37.depth.anim;

import android.view.View;
import android.widget.FrameLayout;

import no.agens.depth.lib.DepthLayout;
import no.agens.depth.lib.R;

/**
 * Created by florentchampigny on 02/03/2017.
 */

public class FragmentDepthView extends FrameLayout {

    private DepthLayout depthLayout;

    public FragmentDepthView(View fragmentView, float depth, float elevation) {
        super(fragmentView.getContext());
        inflate(getContext(), R.layout.fragment_depth_view, this);

        depthLayout = (DepthLayout) findViewById(R.id.root_depth_layout);
        //depthLayout.setDepth(depth * getResources().getDisplayMetrics().density);
        //depthLayout.setCustomShadowElevation(elevation * getResources().getDisplayMetrics().density);

        depthLayout.addView(fragmentView);

    }

}
