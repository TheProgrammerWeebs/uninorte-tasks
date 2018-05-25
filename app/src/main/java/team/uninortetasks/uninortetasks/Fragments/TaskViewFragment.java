package team.uninortetasks.uninortetasks.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import team.uninortetasks.uninortetasks.Database.Task;
import team.uninortetasks.uninortetasks.R;


public class TaskViewFragment extends Fragment {

    private Task task;
    private TextView taskName;
    private TextView taskDate;

    public TaskViewFragment() {

    }

    public static TaskViewFragment newInstance(Task task) {
        TaskViewFragment fragment = new TaskViewFragment();
        Bundle args = new Bundle();
        args.putSerializable("task", task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        task = (Task) getArguments().getSerializable("task");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_view, container, false);
        initialize(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void initialize(View view) {
        this.taskName = view.findViewById(R.id.task_name);
        this.taskDate = view.findViewById(R.id.task_date);
        taskName.setText(task.getName());
        String dia = "";
        Calendar sieteDias = Calendar.getInstance();
        sieteDias.add(Calendar.DATE, 7);
        if (isDateInRange(task.getLimit(), Calendar.getInstance().getTime(), sieteDias.getTime())) {
            switch (task.getLimit().getDay()) {
                case 0:
                    dia = getString(R.string.sunday);
                    break;
                case 1:
                    dia = getString(R.string.monday);
                    break;
                case 2:
                    dia = getString(R.string.tuesday);
                    break;
                case 3:
                    dia = getString(R.string.wednesday);
                    break;
                case 4:
                    dia = getString(R.string.thursday);
                    break;
                case 5:
                    dia = getString(R.string.friday);
                    break;
                case 6:
                    dia = getString(R.string.saturday);
                    break;
            }
        }
        this.taskDate.setText(dia);
    }

    private boolean isDateInRange(Date date, Date first, Date last) {
        return (date.before(last) && date.after(first));
    }
}
