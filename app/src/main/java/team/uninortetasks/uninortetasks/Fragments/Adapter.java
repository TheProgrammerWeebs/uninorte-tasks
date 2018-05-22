package team.uninortetasks.uninortetasks.Fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.RealmResults;
import team.uninortetasks.uninortetasks.Database.Task;
import team.uninortetasks.uninortetasks.R;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    RealmResults<Task> tasks;

    public Adapter(Context context) {
        this.context = context;
        tasks = Task.getAll();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_banner, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
