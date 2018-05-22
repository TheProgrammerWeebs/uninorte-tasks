package team.uninortetasks.uninortetasks.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import io.realm.RealmList;
import io.realm.RealmResults;
import team.uninortetasks.uninortetasks.Database.Task;
import team.uninortetasks.uninortetasks.R;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    Context context;
    RealmList<Task> data;

    public TaskAdapter(Context context, RealmList<Task> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_banner, parent, false);
        ViewHolder v = new ViewHolder(view);
        final int pos = v.getAdapterPosition();
        v.root.setOnClickListener(vi -> {
            showDialog(pos);
        });
        return v;
    }

    private void showDialog(int pos) {
        Task task = data.get(pos);
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.task_dialog);
        TextView name = dialog.findViewById(R.id.name);
        TextView state = dialog.findViewById(R.id.state);
        TextView type = dialog.findViewById(R.id.type);
        TextView priority = dialog.findViewById(R.id.priority);
        TextView date = dialog.findViewById(R.id.date);
        TextView progress = dialog.findViewById(R.id.progress);
        Button report = dialog.findViewById(R.id.report);
        report.setEnabled(task.getMaxSteps() > task.getSteps());

        dialog.show();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(data.get(position).getName());
        holder.priority.setText(data.get(position).getPriority().toString());
        holder.state.setText(data.get(position).getState().toString());
        holder.date.setText(data.get(position).getLimit().toString());
        holder.progress.setText(data.get(position).getSteps() + "/" + data.get(position).getMaxSteps());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView root;
        private TextView name;
        private TextView priority;
        private TextView state;
        private TextView date;
        private TextView progress;

        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.root);
            name = itemView.findViewById(R.id.name);
            priority = itemView.findViewById(R.id.priority);
            state = itemView.findViewById(R.id.state);
            date = itemView.findViewById(R.id.date);
            progress = itemView.findViewById(R.id.progress);
        }
    }

}
