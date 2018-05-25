package team.uninortetasks.uninortetasks.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import team.uninortetasks.uninortetasks.Database.Task;
import team.uninortetasks.uninortetasks.R;


public class TaskViewFragment extends Fragment {
    private Task task;


    public TaskViewFragment() {
        // Required empty public constructor
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

    private void initialize(View view){

    }
}
