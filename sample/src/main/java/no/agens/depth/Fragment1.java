package no.agens.depth;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.github.florent37.depth.anim.Depth;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return Depth.setup(10f, 10f, inflater.inflate(R.layout.fragment_water, container, false));
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        final boolean animateEnter = getArguments().getBoolean("animateEnter");
        if (true || animateEnter) {
            Depth.animateEnter(Fragment1.this);
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
