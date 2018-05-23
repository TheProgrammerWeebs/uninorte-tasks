package team.uninortetasks.uninortetasks.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import team.uninortetasks.uninortetasks.R;

public class AddCategory extends Fragment {

    private OnFragmentInteractionListener listener;

    private BottomSheetBehavior colors;
    private BottomSheetBehavior icons;

    public AddCategory() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_category, container, false);
        colors = BottomSheetBehavior.from(view.findViewById(R.id.colorSelector));
        icons = BottomSheetBehavior.from(view.findViewById(R.id.iconSelector));
        colors.setState(BottomSheetBehavior.STATE_HIDDEN);
        icons.setState(BottomSheetBehavior.STATE_HIDDEN);
        Button color = view.findViewById(R.id.colorButton);
        color.setOnClickListener(e -> {
            colors.setState(BottomSheetBehavior.STATE_EXPANDED);
            icons.setState(BottomSheetBehavior.STATE_HIDDEN);
        });
        Button icon = view.findViewById(R.id.iconButton);
        icon.setOnClickListener(e -> {
            icons.setState(BottomSheetBehavior.STATE_EXPANDED);
            colors.setState(BottomSheetBehavior.STATE_HIDDEN);
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (OnFragmentInteractionListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnFragmentInteractionListener {
        public void onAddingFinished();
    }
}
