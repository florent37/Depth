package com.github.florent37.depth.sample.fullscreen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.depth.Depth;
import com.github.florent37.depth.DepthProvider;
import com.github.florent37.depth.sample.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

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
        ButterKnife.bind(this, view);

        depth.onFragmentReady(this);
    }

    @OnClick(R.id.next)
    public void onNextClicked() {
        ((FragmentCallback) getActivity()).changeFragment(this);
    }

    @OnClick(R.id.open_reset)
    public void onOpenResetClicked() {
        ((FragmentCallback) getActivity()).openResetFragment(this);
    }

}
