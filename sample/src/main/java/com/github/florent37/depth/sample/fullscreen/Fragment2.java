package com.github.florent37.depth.sample.fullscreen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.depth.anim.Depth;
import com.github.florent37.depth.anim.DepthProvider;
import com.github.florent37.depth.sample.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class Fragment2 extends Fragment {

    public static Fragment newInstance(boolean animateEnter) {
        final Bundle args = new Bundle();
        args.putBoolean("animateEnter", animateEnter);
        final Fragment waterFragment = new Fragment2();
        waterFragment.setArguments(args);
        return waterFragment;
    }

    private Depth depth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        depth = DepthProvider.getDepth(container);
        return depth.setupFragment(10, 20f, inflater.inflate(R.layout.fragment_2, container, false));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
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
