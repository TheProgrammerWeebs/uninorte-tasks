package team.uninortetasks.uninortetasks.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Date;

import team.uninortetasks.uninortetasks.Database.Category;
import team.uninortetasks.uninortetasks.Database.Priority;
import team.uninortetasks.uninortetasks.Database.Type;
import team.uninortetasks.uninortetasks.R;

public class AddTask extends Fragment {

    private Category category;
    private BottomSheetBehavior priorities;
    private BottomSheetBehavior types;
    private LinearLayout priorityLayout;
    private LinearLayout typeLayout;

    private View dateLayout;
    private View daysLayout;

    private EditText name;
    private Button priorityButton;
    private TextView priorityLabel;
    private Button typeButton;
    private TextView typeLabel;
    private EditText goalEditText;
    private RadioGroup diaryOrUnique;
    private RadioButton diary;
    private RadioButton unique;
    private CheckBox[] days;
    private Button dateButton;
    private TextView dateLabel;

    private Priority priority;
    private Type type;
    private Date limit;

    private Animation show;
    private Animation hide;

    public AddTask() {
    }

    public static AddTask newInstance(Category category) {
        AddTask fragment = new AddTask();
        Bundle args = new Bundle();
        args.putSerializable("category", category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.category = (Category) getArguments().getSerializable("category");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_task, container, false);
        initialize(view);
        return view;
    }

    private void initialize(View view) {
        days = new CheckBox[7];

        show = AnimationUtils.loadAnimation(getContext(), R.anim.animation_show);
        hide = AnimationUtils.loadAnimation(getContext(), R.anim.animation_hide);

        priority = Priority.high;
        type = Type.goal;
        limit = new Date();

        priorityLayout = view.findViewById(R.id.prioritySelector);
        typeLayout = view.findViewById(R.id.typeSelector);

        name = view.findViewById(R.id.name);
        priorityButton = view.findViewById(R.id.priorityButton);
        priorityLabel = view.findViewById(R.id.priorityLabel);
        typeButton = view.findViewById(R.id.typeButton);
        typeLabel = view.findViewById(R.id.typeLabel);
        dateButton = view.findViewById(R.id.dateButton);
        dateLabel = view.findViewById(R.id.dateLabel);
        goalEditText = view.findViewById(R.id.goal);
        diaryOrUnique = view.findViewById(R.id.diaryOrUnique);
        diary = diaryOrUnique.findViewById(R.id.diary);
        unique = diaryOrUnique.findViewById(R.id.unique);

        daysLayout = view.findViewById(R.id.daysLayout);
        dateLayout = view.findViewById(R.id.dateLayout);

        days[0] = view.findViewById(R.id.sunday);
        days[1] = view.findViewById(R.id.monday);
        days[2] = view.findViewById(R.id.tuesday);
        days[3] = view.findViewById(R.id.wednesday);
        days[4] = view.findViewById(R.id.thursday);
        days[5] = view.findViewById(R.id.friday);
        days[6] = view.findViewById(R.id.saturday);

        priorities = BottomSheetBehavior.from(priorityLayout);
        types = BottomSheetBehavior.from(typeLayout);

        priorities.setState(BottomSheetBehavior.STATE_HIDDEN);
        types.setState(BottomSheetBehavior.STATE_HIDDEN);

        priorityLayout.findViewById(R.id.back).setOnClickListener(e -> priorities.setState(BottomSheetBehavior.STATE_HIDDEN));
        typeLayout.findViewById(R.id.back).setOnClickListener(e -> types.setState(BottomSheetBehavior.STATE_HIDDEN));

        View high = priorityLayout.findViewById(R.id.high);
        View medium = priorityLayout.findViewById(R.id.medium);
        View low = priorityLayout.findViewById(R.id.low);

        View goal = typeLayout.findViewById(R.id.goal);
        View activity = typeLayout.findViewById(R.id.activity);
        View homework = typeLayout.findViewById(R.id.homework);
        View other = typeLayout.findViewById(R.id.other);


        final InputMethodManager input = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        priorityButton.setOnClickListener(e -> {
            priorities.setState(BottomSheetBehavior.STATE_EXPANDED);
            types.setState(BottomSheetBehavior.STATE_HIDDEN);
            input.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        });
        typeButton.setOnClickListener(e -> {
            types.setState(BottomSheetBehavior.STATE_EXPANDED);
            priorities.setState(BottomSheetBehavior.STATE_HIDDEN);
            input.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        });

        high.setOnClickListener(e -> {
            priorities.setState(BottomSheetBehavior.STATE_HIDDEN);
            priority = Priority.high;
            priorityLabel.setText(R.string.high_priority);
        });
        medium.setOnClickListener(e -> {
            priorities.setState(BottomSheetBehavior.STATE_HIDDEN);
            priority = Priority.medium;
            priorityLabel.setText(R.string.medium_priority);
        });
        low.setOnClickListener(e -> {
            priorities.setState(BottomSheetBehavior.STATE_HIDDEN);
            priority = Priority.low;
            priorityLabel.setText(R.string.low_priority);
        });

        goal.setOnClickListener(e -> {
            types.setState(BottomSheetBehavior.STATE_HIDDEN);
            type = Type.goal;
            goalEditText.setVisibility(View.VISIBLE);
            diaryOrUnique.setVisibility(View.INVISIBLE);
            daysLayout.setVisibility(View.INVISIBLE);
            dateLayout.setVisibility(View.VISIBLE);
        });
        activity.setOnClickListener(e -> diary.setChecked(true));
        homework.setOnClickListener(e -> diary.setChecked(true));
        other.setOnClickListener(e -> diary.setChecked(true));
        diary.setOnCheckedChangeListener((buttonView, isChecked) -> {
            diaryOrUnique.setVisibility(View.VISIBLE);
            if (isChecked) {
                animate(daysLayout, View.VISIBLE);
                animate(dateLayout, View.INVISIBLE);
            }
        });
        unique.setOnCheckedChangeListener((buttonView, isChecked) -> {
            diaryOrUnique.setVisibility(View.VISIBLE);
            if (isChecked) {
                animate(dateLayout, View.VISIBLE);
                animate(daysLayout, View.INVISIBLE);
            }
        });
    }

    private void animate(View view, int state) {
        if (view.getVisibility() != state) {
            switch (state) {
                case View.VISIBLE:
                    view.startAnimation(show);
                    view.setVisibility(state);
                    break;
                case View.INVISIBLE:
                    view.startAnimation(hide);
                    view.setVisibility(state);
                    break;
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
