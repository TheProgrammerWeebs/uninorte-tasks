package team.uninortetasks.uninortetasks.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import team.uninortetasks.uninortetasks.Database.Category;
import team.uninortetasks.uninortetasks.R;

public class AddCategory extends Fragment {

    private OnAddCategoryListener listener;

    private BottomSheetBehavior colors;
    private BottomSheetBehavior icons;
    private LinearLayout colorsLayout;
    private LinearLayout iconsLayout;

    private View selectedColor;
    private ImageView selectedIcon;
    private EditText name;
    private int style;
    private int icon;

    public AddCategory() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_category, container, false);
        initialize(view);
        return view;
    }

    private void initialize(View view) {
        icon = R.drawable.ic_item;
        style = R.style.redTheme;

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

        final InputMethodManager input = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        color.setOnClickListener(e -> {
            input.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            icons.setState(BottomSheetBehavior.STATE_HIDDEN);
            colors.setState(BottomSheetBehavior.STATE_EXPANDED);
        });
        icon.setOnClickListener(e -> {
            input.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            colors.setState(BottomSheetBehavior.STATE_HIDDEN);
            icons.setState(BottomSheetBehavior.STATE_EXPANDED);
        });

        red.setOnClickListener(getDefaultColorListener(R.drawable.oval_dark_red, R.style.redTheme));
        orange.setOnClickListener(getDefaultColorListener(R.drawable.oval_orange, R.style.orangeTheme));
        green.setOnClickListener(getDefaultColorListener(R.drawable.oval_green, R.style.greenTheme));
        cyan.setOnClickListener(getDefaultColorListener(R.drawable.oval_cyan, R.style.cyanTheme));
        purple.setOnClickListener(getDefaultColorListener(R.drawable.oval_purple, R.style.purpleTheme));
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

        view.findViewById(R.id.cancelButton).setOnClickListener(e -> listener.onAddingCanceled());
        view.findViewById(R.id.createButton).setOnClickListener(e -> {
            String name = this.name.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(getContext(), "Seleccione un nombre para la categoría", Toast.LENGTH_SHORT).show();
                return;
            }
            listener.onAddingOkay(Category.add(getContext(), name, this.icon, this.style));
        });
    }

    private View.OnClickListener getDefaultColorListener(int drawable, int style) {
        return (e -> {
            selectedColor.setBackground(getResources().getDrawable(drawable));
            this.style = style;
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
        listener = (OnAddCategoryListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnAddCategoryListener {
        void onAddingOkay(Category category);

        void onAddingCanceled();
    }
}
