package team.uninortetasks.uninortetasks.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import team.uninortetasks.uninortetasks.Database.Category;
import team.uninortetasks.uninortetasks.Others.TaskAdapter;
import team.uninortetasks.uninortetasks.R;

public class TasksCategory extends Fragment {

    private Category category;
    private OnTasksCategoryListener mListener;
    private RecyclerView tasksList;

    public TasksCategory() {
    }

    public static TasksCategory newInstance(Category category) {
        TasksCategory fragment = new TasksCategory();
        Bundle args = new Bundle();
        args.putSerializable("category", category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        category = (Category) getArguments().getSerializable("category");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks_category, container, false);
        initialize(view);
        return view;
    }

    private void initialize(View view) {
        tasksList = view.findViewById(R.id.tasksList);

        tasksList.setAdapter(new TaskAdapter(getContext(), category.getTasks()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnTasksCategoryListener {
        public void onDisplayTask(Category category);
    }
}
