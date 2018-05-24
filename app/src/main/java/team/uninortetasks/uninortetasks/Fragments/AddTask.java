package team.uninortetasks.uninortetasks.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import team.uninortetasks.uninortetasks.Database.Category;
import team.uninortetasks.uninortetasks.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnAddTaskListener} interface
 * to handle interaction events.
 * Use the {@link AddTask#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTask extends Fragment {

    private OnAddTaskListener mListener;
    private Category category;

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

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnAddTaskListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnAddTaskListener {
        void onAddingFinished();
    }
}
