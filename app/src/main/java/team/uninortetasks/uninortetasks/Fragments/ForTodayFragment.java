package team.uninortetasks.uninortetasks.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import team.uninortetasks.uninortetasks.Database.Task;
import team.uninortetasks.uninortetasks.Others.TaskForTodayAdapter;
import team.uninortetasks.uninortetasks.R;

public class ForTodayFragment extends Fragment {

    private RecyclerView tasksList;

    public ForTodayFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_for_today, container, false);
        initialize(view);
        return view;
    }

    private void initialize(View view) {
        tasksList = view.findViewById(R.id.tasksList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        tasksList.setLayoutManager(manager);
        tasksList.setItemAnimator(new DefaultItemAnimator());
        tasksList.setAdapter(new TaskForTodayAdapter(getContext(), Task.tasksForToday()));

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
