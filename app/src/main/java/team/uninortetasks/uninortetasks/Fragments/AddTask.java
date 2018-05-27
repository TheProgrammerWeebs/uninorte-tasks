package team.uninortetasks.uninortetasks.Fragments;

import android.app.DatePickerDialog;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import team.uninortetasks.uninortetasks.Database.Category;
import team.uninortetasks.uninortetasks.Database.Month;
import team.uninortetasks.uninortetasks.Database.Priority;
import team.uninortetasks.uninortetasks.Database.State;
import team.uninortetasks.uninortetasks.Database.Task;
import team.uninortetasks.uninortetasks.Database.Type;
import team.uninortetasks.uninortetasks.R;

public class AddTask extends Fragment {

    private OnAddingTaskListener listener;

    private Calendar today = Calendar.getInstance();

    private int categoryId;
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
    private Button createButton;
    private Button cancelButton;

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
        args.putInt("category", category.getId());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.categoryId = getArguments().getInt("category");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_task, container, false);
        initialize(view);
        return view;
    }

    private void initialize(View view) {
        show = AnimationUtils.loadAnimation(getContext(), R.anim.animation_show);
        hide = AnimationUtils.loadAnimation(getContext(), R.anim.animation_hide);

        DatePickerDialog datePicker = new DatePickerDialog(
                getContext(),
                (view1, year, month, dayOfMonth) -> {
                    limit = new Date(year, month, dayOfMonth);
                    dateLabel.setText(dayOfMonth + " " + getResources().getString(R.string.of) + " " + getResources().getString(Month.fromInt(month).getTextResource()) + " " + getResources().getString(R.string.of) + " " + year);
                },
                today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH)
        );
        datePicker.getDatePicker().setMinDate(today.getTimeInMillis());

        days = new CheckBox[7];

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

        dateLabel.setText(today.get(Calendar.DAY_OF_MONTH) + " " + getResources().getString(R.string.of) + " " + getResources().getString(Month.fromInt(today.get(Calendar.MONTH)).getTextResource()) + " " + getResources().getString(R.string.of2) + " " + today.get(Calendar.YEAR));

        daysLayout = view.findViewById(R.id.daysLayout);
        dateLayout = view.findViewById(R.id.dateLayout);

        days[0] = view.findViewById(R.id.sunday);
        days[1] = view.findViewById(R.id.monday);
        days[2] = view.findViewById(R.id.tuesday);
        days[3] = view.findViewById(R.id.wednesday);
        days[4] = view.findViewById(R.id.thursday);
        days[5] = view.findViewById(R.id.friday);
        days[6] = view.findViewById(R.id.saturday);

        createButton = view.findViewById(R.id.createButton);
        cancelButton = view.findViewById(R.id.cancelButton);

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

        diary.setEnabled(false);
        unique.setEnabled(false);

        final InputMethodManager input = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        //No se expande con teclado abierto

        priorityButton.setOnClickListener(e -> {
            input.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            types.setState(BottomSheetBehavior.STATE_HIDDEN);
            priorities.setState(BottomSheetBehavior.STATE_EXPANDED);
        });
        typeButton.setOnClickListener(e -> {
            input.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            priorities.setState(BottomSheetBehavior.STATE_HIDDEN);
            types.setState(BottomSheetBehavior.STATE_EXPANDED);
        });

        dateButton.setOnClickListener(e -> datePicker.show());

        high.setOnClickListener(e -> {
            priorities.setState(BottomSheetBehavior.STATE_HIDDEN);
            priority = Priority.high;
            priorityLabel.setText(R.string.high_priority);
        });
        medium.setOnClickListener(e -> {
            priorities.setState(BottomSheetBehavior.STATE_HIDDEN);
            priority = Priority.medium;
            priorityLabel.setText(R.string.medium_priority_short);
        });
        low.setOnClickListener(e -> {
            priorities.setState(BottomSheetBehavior.STATE_HIDDEN);
            priority = Priority.low;
            priorityLabel.setText(R.string.low_priority);
        });

        goal.setOnClickListener(e -> {
            diary.setEnabled(false);
            unique.setEnabled(false);
            types.setState(BottomSheetBehavior.STATE_HIDDEN);
            type = Type.goal;
            animate(goalEditText, View.VISIBLE);
            animate(daysLayout, View.INVISIBLE);
            animate(dateLayout, View.VISIBLE);
            typeLabel.setText(R.string.goal);
        });
        activity.setOnClickListener(e -> {
            if (!diary.isEnabled()) diary.setChecked(true);
            diary.setEnabled(true);
            unique.setEnabled(true);
            types.setState(BottomSheetBehavior.STATE_HIDDEN);
            type = Type.activity;
            animate(goalEditText, View.INVISIBLE);
            typeLabel.setText(R.string.activity);
        });
        homework.setOnClickListener(e -> {
            if (!diary.isEnabled()) diary.setChecked(true);
            diary.setEnabled(true);
            unique.setEnabled(true);
            types.setState(BottomSheetBehavior.STATE_HIDDEN);
            type = Type.homework;
            animate(goalEditText, View.INVISIBLE);
            typeLabel.setText(R.string.homework);
        });
        other.setOnClickListener(e -> {
            if (!diary.isEnabled()) diary.setChecked(true);
            diary.setEnabled(true);
            unique.setEnabled(true);
            types.setState(BottomSheetBehavior.STATE_HIDDEN);
            type = Type.other;
            animate(goalEditText, View.INVISIBLE);
            typeLabel.setText(R.string.other);
        });
        diary.setOnCheckedChangeListener((buttonView, isChecked) -> {
            animate(diaryOrUnique, View.VISIBLE);
            if (isChecked) {
                animate(daysLayout, View.VISIBLE);
                animate(dateLayout, View.INVISIBLE);
            }
        });
        unique.setOnCheckedChangeListener((buttonView, isChecked) -> {
            animate(diaryOrUnique, View.VISIBLE);
            if (isChecked) {
                animate(dateLayout, View.VISIBLE);
                animate(daysLayout, View.INVISIBLE);
            }
        });

        createButton.setOnClickListener(e -> {
            if (!taskAdded()) return;
            listener.addingFinished(Category.get(categoryId));
        });
        cancelButton.setOnClickListener(e -> listener.addingFinished(Category.get(categoryId)));
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

    private boolean taskAdded() {
        String name = this.name.getText().toString().trim();
        String goalStr = goalEditText.getText().toString().trim();
        int goal;
        if (name.isEmpty()) {
            Toast.makeText(getContext(), "Complete el campo de nombre", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (type == Type.goal && goalStr.isEmpty()) {
            Toast.makeText(getContext(), "Complete el campo de meta", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (type == Type.goal) {
                goal = Integer.parseInt(goalStr);
            } else {
                goal = 0;
            }
        }
        if (type != Type.goal && diary.isChecked()) {
            boolean oneChecked = false;
            for (int i = 0; i < 7; i++) {
                if (this.days[i].isChecked()) {
                    oneChecked = true;
                    break;
                }
            }
            if (!oneChecked) {
                Toast.makeText(getContext(), "Seleccione por lo menos un día", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        ArrayList<Integer> days = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            if (this.days[i].isChecked()) {
                days.add(i);
            }
        }
        Task.add(
                getContext(),
                name,
                this.priority,
                State.pending,
                this.type,
                this.limit,
                (this.type == Type.goal),
                (this.diary.isChecked()),
                goal,
                Category.get(categoryId),
                days
        );
        Toast.makeText(getContext(), "Tarea agregada exitosamente", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (OnAddingTaskListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnAddingTaskListener {
        void addingFinished(Category category);
    }
}
