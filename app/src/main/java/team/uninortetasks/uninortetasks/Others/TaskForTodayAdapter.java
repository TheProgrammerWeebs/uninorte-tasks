package team.uninortetasks.uninortetasks.Others;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import team.uninortetasks.uninortetasks.Database.Task;
import team.uninortetasks.uninortetasks.Database.Type;
import team.uninortetasks.uninortetasks.R;

public class TaskForTodayAdapter extends RecyclerView.Adapter<TaskForTodayAdapter.ViewHolder> {

    private Context context;
    private RealmResults<Task> data;

    public TaskForTodayAdapter(Context context, RealmResults<Task> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.for_today_task_view, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = data.get(position);
        holder.categoryColor.setBackgroundColor(context.getResources().getColor(task.getCategory().getStyle().getColor1()));
        holder.name.setText(task.getName());
        holder.progress.setText((task.getType() == Type.goal) ? ("Progreso: " + task.getSteps() + "/" + task.getMaxSteps()) : "");
        holder.state.setText(context.getResources().getString(task.getState().getSrc()));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View categoryColor;
        private TextView name;
        private TextView state;
        private TextView progress;

        public ViewHolder(View view) {
            super(view);
            categoryColor = view.findViewById(R.id.categoryColor);
            name = view.findViewById(R.id.name);
            state = view.findViewById(R.id.state);
            progress = view.findViewById(R.id.progress);
        }
    }

}
