package no.agens.depth;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.depth.anim.Depth;
import com.github.florent37.depth.anim.DepthBus;

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
        depth = DepthBus.getDepth(container);
        return depth.setup(10, 20f, inflater.inflate(R.layout.fragment_water_2, container, false));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        final boolean animateEnter = getArguments().getBoolean("animateEnter");
        if (animateEnter) {
            depth.animateEnter(this);
        }
    }

    @OnClick(R.id.next)
    public void onNextClicked() {
        ((Callback) getActivity()).changeFragment(this);
    }

    @OnClick(R.id.open_reset)
    public void onOpenResetClicked() {
        ((Callback) getActivity()).openResetFragment(this);
    }

}
