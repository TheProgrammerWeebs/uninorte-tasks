package team.uninortetasks.uninortetasks.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import team.uninortetasks.uninortetasks.Database.Category;
import team.uninortetasks.uninortetasks.Database.Task;
import team.uninortetasks.uninortetasks.Others.RecyclerItemTouchHelper;
import team.uninortetasks.uninortetasks.Others.TaskAdapter;
import team.uninortetasks.uninortetasks.R;

public class TasksFragment extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private int categoryId;
    private OnTasksCategoryListener mListener;
    private RecyclerView tasksList;
    private TaskAdapter adapter;
    private CoordinatorLayout coordinatorLayout;

    public TasksFragment() {
    }

    public static TasksFragment newInstance(Category category) {
        TasksFragment fragment = new TasksFragment();
        Bundle args = new Bundle();
        args.putInt("category", category.getId());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryId = getArguments().getInt("category");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks_category, container, false);
        initialize(view);
        return view;
    }

    private void initialize(View view) {
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa - task category");
        Category category = Category.get(categoryId);
        tasksList = view.findViewById(R.id.tasksList);
        coordinatorLayout = view.findViewById(R.id.coordinator_layout);
        adapter = new TaskAdapter(getContext(), category.getTasks());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext());
        tasksList.setLayoutManager(manager);
        tasksList.setItemAnimator(new DefaultItemAnimator());
        tasksList.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
        tasksList.setAdapter(adapter);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(tasksList);
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

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        System.out.println("Swiped");
        if (viewHolder instanceof TaskAdapter.ViewHolder) {
            System.out.println("Is a viewholder");
            Category category = Category.get(categoryId);
            int deletedPosition = viewHolder.getAdapterPosition();
            Task task = category.getTasks().get(deletedPosition);
            String name = task.getName();
            adapter.removeTask(deletedPosition);
            Snackbar snackbar = Snackbar.make(coordinatorLayout, name + getString(R.string.deleted), Snackbar.LENGTH_LONG);
            snackbar.show();
            snackbar.setAction(getString(R.string.undo), view -> adapter.restoreTask(task, deletedPosition));
        }
    }

    public interface OnTasksCategoryListener {
        public void onDisplayTask(Category category);
    }
}
