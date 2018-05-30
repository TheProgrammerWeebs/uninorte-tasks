package team.uninortetasks.uninortetasks.Others;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.RealmResults;
import team.uninortetasks.uninortetasks.Database.Category;
import team.uninortetasks.uninortetasks.Database.Day;
import team.uninortetasks.uninortetasks.Database.Month;
import team.uninortetasks.uninortetasks.Database.Priority;
import team.uninortetasks.uninortetasks.Database.State;
import team.uninortetasks.uninortetasks.Database.Task;
import team.uninortetasks.uninortetasks.Database.Type;
import team.uninortetasks.uninortetasks.R;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private Context context;
    private RealmResults<Task> data;

    public TaskAdapter(Context context, RealmResults<Task> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_view_layout, parent, false);
        ViewHolder v = new ViewHolder(view);
        final int pos = v.getAdapterPosition();
        v.root.setOnClickListener(e -> showDialog(pos));
        return v;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.taskName.setText(data.get(position).getName());
        holder.taskDate.setText(getDate(data.get(position)));
        holder.priority.setBackgroundColor(context.getResources().getColor(data.get(position).getPriority().getColor()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void removeTask(int position) {
        Task.remove(context, this.data.get(position));
        this.notifyItemRemoved(position);
    }

    public void restoreTask(String name,
                            Priority priority,
                            State state,
                            Type type,
                            Date limit,
                            boolean haveSteps,
                            boolean diaryTask,
                            int maxSteps,
                            Category category,
                            ArrayList<Integer> days,
                            int position) {
        Task.add(context, name, priority, state, type, limit, haveSteps, diaryTask, maxSteps, category, days);
        this.notifyItemInserted(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private FrameLayout root;
        private RelativeLayout foreground;
        private TextView taskName;
        private View priority;
        private TextView taskDate;

        public ViewHolder(View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.task_name);
            taskDate = itemView.findViewById(R.id.task_date);
            priority = itemView.findViewById(R.id.priority);
            root = itemView.findViewById(R.id.parent);
            foreground = itemView.findViewById(R.id.foreground);
        }

        public RelativeLayout getForeground() {
            return foreground;
        }
    }

    private String getDate(Task task) {
        String dia;
        Calendar sieteDias = Calendar.getInstance();
        sieteDias.add(Calendar.DATE, 7);
        if (task.getLimit().equals(new Date())) {
            dia = "hoy";
        } else if (isDateInRange(task.getLimit(), Calendar.getInstance().getTime(), sieteDias.getTime())) {
            dia = context.getResources().getString(Day.fromInt(task.getLimit().getDay()).toInt());
        } else {
            dia = task.getLimit().getDate() + " de ";
        }
        return dia + context.getResources().getString(Month.fromInt(task.getLimit().getMonth()).toInt());
    }

    private boolean isDateInRange(Date date, Date first, Date last) {
        return (date.before(last) && date.after(first));
    }

    private void showDialog(int pos) {


    }

}
