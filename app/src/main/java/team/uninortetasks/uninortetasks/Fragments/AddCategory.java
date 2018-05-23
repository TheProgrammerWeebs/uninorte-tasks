package team.uninortetasks.uninortetasks.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import team.uninortetasks.uninortetasks.Database.Category;
import team.uninortetasks.uninortetasks.R;

public class AddCategory extends Fragment {

    private OnFragmentInteractionListener listener;

    private BottomSheetBehavior colors;
    private BottomSheetBehavior icons;
    private LinearLayout colorsLayout;
    private LinearLayout iconsLayout;
    private View selectedColor;
    private ImageView selectedIcon;
    private EditText name;
    private int color;
    private int icon;

    public AddCategory() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_category, container, false);
        initialize(view);
        return view;
    }

    private void initialize(View view) {
        icon = R.drawable.ic_item;
        color = R.color.darkRed;

        colorsLayout = view.findViewById(R.id.colorSelector);
        iconsLayout = view.findViewById(R.id.iconSelector);
        name = view.findViewById(R.id.name);
        selectedColor = view.findViewById(R.id.selectedColor);
        selectedIcon = view.findViewById(R.id.selectedIcon);

        colors = BottomSheetBehavior.from(colorsLayout);
        icons = BottomSheetBehavior.from(iconsLayout);

        Button color = view.findViewById(R.id.colorButton);
        Button icon = view.findViewById(R.id.iconButton);
        View red = colorsLayout.findViewById(R.id.red);
        View orange = colorsLayout.findViewById(R.id.orange);
        View green = colorsLayout.findViewById(R.id.green);
        View cyan = colorsLayout.findViewById(R.id.cyan);
        View purple = colorsLayout.findViewById(R.id.purple);
        ImageButton backColors = colorsLayout.findViewById(R.id.back);
        View icon1 = iconsLayout.findViewById(R.id.icon1);
        View icon2 = iconsLayout.findViewById(R.id.icon2);
        View icon3 = iconsLayout.findViewById(R.id.icon3);
        View icon4 = iconsLayout.findViewById(R.id.icon4);
        View icon5 = iconsLayout.findViewById(R.id.icon5);
        View icon6 = iconsLayout.findViewById(R.id.icon6);
        View icon7 = iconsLayout.findViewById(R.id.icon7);
        View icon8 = iconsLayout.findViewById(R.id.icon8);
        View icon9 = iconsLayout.findViewById(R.id.icon9);
        ImageButton backIcons = iconsLayout.findViewById(R.id.back);

        colors.setState(BottomSheetBehavior.STATE_HIDDEN);
        icons.setState(BottomSheetBehavior.STATE_HIDDEN);

        color.setOnClickListener(e -> {
            colors.setState(BottomSheetBehavior.STATE_EXPANDED);
            icons.setState(BottomSheetBehavior.STATE_HIDDEN);
        });
        icon.setOnClickListener(e -> {
            icons.setState(BottomSheetBehavior.STATE_EXPANDED);
            colors.setState(BottomSheetBehavior.STATE_HIDDEN);
        });

        red.setOnClickListener(getDefaultColorListener(R.drawable.oval_dark_red, R.color.darkRed));
        orange.setOnClickListener(getDefaultColorListener(R.drawable.oval_orange, R.color.orange));
        green.setOnClickListener(getDefaultColorListener(R.drawable.oval_green, R.color.green));
        cyan.setOnClickListener(getDefaultColorListener(R.drawable.oval_cyan, R.color.cyan));
        purple.setOnClickListener(getDefaultColorListener(R.drawable.oval_purple, R.color.purple));
        backColors.setOnClickListener(e -> colors.setState(BottomSheetBehavior.STATE_HIDDEN));

        icon1.setOnClickListener(getDefaultIconClickListener(R.drawable.ic_item));
        icon2.setOnClickListener(getDefaultIconClickListener(R.drawable.ic_money));
        icon3.setOnClickListener(getDefaultIconClickListener(R.drawable.ic_audio));
        icon4.setOnClickListener(getDefaultIconClickListener(R.drawable.ic_book));
        icon5.setOnClickListener(getDefaultIconClickListener(R.drawable.ic_laptop));
        icon6.setOnClickListener(getDefaultIconClickListener(R.drawable.ic_flight));
        icon7.setOnClickListener(getDefaultIconClickListener(R.drawable.ic_bed));
        icon8.setOnClickListener(getDefaultIconClickListener(R.drawable.ic_gym));
        icon9.setOnClickListener(getDefaultIconClickListener(R.drawable.ic_home));
        backIcons.setOnClickListener(e -> icons.setState(BottomSheetBehavior.STATE_HIDDEN));

        view.findViewById(R.id.cancelButton).setOnClickListener(e -> listener.onAddingFinished());
        view.findViewById(R.id.createButton).setOnClickListener(e -> {
            String name = this.name.getText().toString().trim();
            if (name.isEmpty()) return;
            Category.add(getContext(), name, this.icon, this.color);
            listener.onAddingFinished();
        });
    }

    private View.OnClickListener getDefaultColorListener(int drawable, int color) {
        return (e -> {
            selectedColor.setBackground(getResources().getDrawable(drawable));
            this.color = color;
            colors.setState(BottomSheetBehavior.STATE_HIDDEN);
        });
    }

    private View.OnClickListener getDefaultIconClickListener(int drawable) {
        return (e -> {
            selectedIcon.setImageDrawable(getResources().getDrawable(drawable));
            this.icon = drawable;
            icons.setState(BottomSheetBehavior.STATE_HIDDEN);
        });
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
