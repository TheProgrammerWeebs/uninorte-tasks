package team.uninortetasks.uninortetasks.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import team.uninortetasks.uninortetasks.Activities.DreamCalculatorScreen;
import team.uninortetasks.uninortetasks.Activities.TasksScreen;
import team.uninortetasks.uninortetasks.R;

public class DashboardFragment extends Fragment {

    public DashboardFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        initialize(view);
        return view;
    }

    private void initialize(View view) {
        view.findViewById(R.id.tasks).setOnClickListener(e -> startActivity(new Intent(getActivity(), TasksScreen.class)));
        view.findViewById(R.id.sleep).setOnClickListener(e -> startActivity(new Intent(getActivity(), DreamCalculatorScreen.class)));
        //Categories listener...
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void onTasksClick(View view) {
        startActivity(new Intent(getActivity(), TasksScreen.class));
    }

    public void onDreamCalculatorClick(View view) {
        startActivity(new Intent(getActivity(), DreamCalculatorScreen.class));
    }

    public void onCategoriesClick(View view) {
    }
}
