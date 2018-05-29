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
import team.uninortetasks.uninortetasks.Database.Icon;
import team.uninortetasks.uninortetasks.Database.Style;
import team.uninortetasks.uninortetasks.R;

public class AddCategoryFragment extends Fragment {

    private OnAddCategoryListener listener;

    private BottomSheetBehavior colors;
    private BottomSheetBehavior icons;
    private LinearLayout colorsLayout;
    private LinearLayout iconsLayout;

    private View selectedColor;
    private ImageView selectedIcon;
    private EditText name;
    private Style style;
    private Icon icon;

    private boolean editMode = false;
    private int editId = 0;

    public AddCategoryFragment() {
    }

    public static AddCategoryFragment newEditInstance(int id) {
        Bundle b = new Bundle();
        b.putInt("id", id);
        AddCategoryFragment fragment = new AddCategoryFragment();
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        if (b != null) {
            editMode = true;
            editId = b.getInt("id");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_category, container, false);
        initialize(view);
        return view;
    }

    private void initialize(View view) {
        icon = Icon.defaultt;
        style = Style.darkRed;

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

        red.setOnClickListener(getDefaultColorListener(R.drawable.oval_dark_red, Style.darkRed));
        orange.setOnClickListener(getDefaultColorListener(R.drawable.oval_orange, Style.orange));
        green.setOnClickListener(getDefaultColorListener(R.drawable.oval_green, Style.green));
        cyan.setOnClickListener(getDefaultColorListener(R.drawable.oval_cyan, Style.cyan));
        purple.setOnClickListener(getDefaultColorListener(R.drawable.oval_purple, Style.purple));
        backColors.setOnClickListener(e -> colors.setState(BottomSheetBehavior.STATE_HIDDEN));

        icon1.setOnClickListener(getDefaultIconClickListener(Icon.defaultt));
        icon2.setOnClickListener(getDefaultIconClickListener(Icon.money));
        icon3.setOnClickListener(getDefaultIconClickListener(Icon.audio));
        icon4.setOnClickListener(getDefaultIconClickListener(Icon.book));
        icon5.setOnClickListener(getDefaultIconClickListener(Icon.laptop));
        icon6.setOnClickListener(getDefaultIconClickListener(Icon.flight));
        icon7.setOnClickListener(getDefaultIconClickListener(Icon.bed));
        icon8.setOnClickListener(getDefaultIconClickListener(Icon.gym));
        icon9.setOnClickListener(getDefaultIconClickListener(Icon.home));
        backIcons.setOnClickListener(e -> icons.setState(BottomSheetBehavior.STATE_HIDDEN));

        view.findViewById(R.id.cancelButton).setOnClickListener(e -> listener.onOperationCanceled());
        view.findViewById(R.id.createButton).setOnClickListener(e -> {
            String name = this.name.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(getContext(), "Seleccione un nombre para la categoría", Toast.LENGTH_SHORT).show();
                return;
            }
            if (editMode) {
                listener.onOperationOkay(Category.get(editId).getEditableInstance().setName(name).setStyle(this.style).setIcon(this.icon).save(getContext()), editMode);
            } else {
                listener.onOperationOkay(Category.add(getContext(), name, this.icon, this.style), editMode);
            }
        });

        if (editMode) {
            Category category = Category.get(this.editId);
            selectColor(category);
            selectedIcon.setImageDrawable(getResources().getDrawable(category.getIcon().getsrc()));
            this.icon = category.getIcon();
            name.setText(category.getName());
            ((Button) view.findViewById(R.id.createButton)).setText("Editar categoría");
        }
    }

    private void selectColor(Category category) {
        Style style = category.getStyle();
        switch (style) {
            case darkRed:
                selectedColor.setBackground(getResources().getDrawable(R.drawable.oval_dark_red));
                this.style = style;
                break;
            case orange:
                selectedColor.setBackground(getResources().getDrawable(R.drawable.oval_orange));
                this.style = style;
                break;
            case green:
                selectedColor.setBackground(getResources().getDrawable(R.drawable.oval_green));
                this.style = style;
                break;
            case cyan:
                selectedColor.setBackground(getResources().getDrawable(R.drawable.oval_cyan));
                this.style = style;
                break;
            case purple:
                selectedColor.setBackground(getResources().getDrawable(R.drawable.oval_purple));
                this.style = style;
                break;
        }
    }

    private View.OnClickListener getDefaultColorListener(int drawable, Style style) {
        return (e -> {
            selectedColor.setBackground(getResources().getDrawable(drawable));
            this.style = style;
            colors.setState(BottomSheetBehavior.STATE_HIDDEN);
        });
    }

    private View.OnClickListener getDefaultIconClickListener(Icon icon) {
        return (e -> {
            selectedIcon.setImageDrawable(getResources().getDrawable(icon.getsrc()));
            this.icon = icon;
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
        void onOperationOkay(Category category, boolean editMode);

        void onOperationCanceled();
    }
}
