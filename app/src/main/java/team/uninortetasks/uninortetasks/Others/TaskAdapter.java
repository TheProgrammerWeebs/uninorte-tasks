package team.uninortetasks.uninortetasks.Others;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import io.realm.RealmList;
import team.uninortetasks.uninortetasks.Database.Task;
import team.uninortetasks.uninortetasks.R;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private Context context;
    private RealmList<Task> data;

    public TaskAdapter(Context context, RealmList<Task> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.banner_task, parent, false);
        ViewHolder v = new ViewHolder(view);
        final int pos = v.getAdapterPosition();
        v.root.setOnClickListener(e -> showDialog(pos));
        return v;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.taskName.setText(data.get(position).getName());
        holder.taskDate.setText(getDate(data.get(position)));
        holder.priority.setBackgroundColor(data.get(position).getPriority().getColor());
        //holder.priority.setText(data.get(position).getPriority().toString());
        //holder.state.setText(data.get(position).getState().toString());
        //holder.date.setText(data.get(position).getLimit().toString());
        //holder.progress.setText(data.get(position).getSteps() + "/" + data.get(position).getMaxSteps());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void removeTask(int position){
        data.remove(position);
        this.notifyItemRemoved(position);
    }

    public void restoreTask(Task task, int position){
        data.add(position, task);
        notifyItemInserted(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private FrameLayout root;
        private TextView taskName;
        private TextView priority;
        //private TextView state;
        private TextView taskDate;
        //private TextView progress;

        public ViewHolder(View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.task_name);
            taskDate = itemView.findViewById(R.id.task_date);
            priority = itemView.findViewById(R.id.priority);
            root = itemView.findViewById(R.id.parent);
            //state = itemView.findViewById(R.id.state);
            //date = itemView.findViewById(R.id.date);
            //progress = itemView.findViewById(R.id.progress);
        }
    }

    private String getDate(Task task){
        String dia = "";
        String mes = "";
        Calendar sieteDias = Calendar.getInstance();
        sieteDias.add(Calendar.DATE, 7);
        if (task.getLimit().equals(new Date())){
            dia = "hoy";
        } else if (isDateInRange(task.getLimit(), Calendar.getInstance().getTime(), sieteDias.getTime())) {
            switch (task.getLimit().getDay()) {
                case 0:
                    dia = context.getString(R.string.sunday);
                    break;
                case 1:
                    dia = context.getString(R.string.monday);
                    break;
                case 2:
                    dia = context.getString(R.string.tuesday);
                    break;
                case 3:
                    dia = context.getString(R.string.wednesday);
                    break;
                case 4:
                    dia = context.getString(R.string.thursday);
                    break;
                case 5:
                    dia = context.getString(R.string.friday);
                    break;
                case 6:
                    dia = context.getString(R.string.saturday);
                    break;
            }
        }else{
            dia = task.getLimit().getDate() + " de ";
            switch (task.getLimit().getMonth()){
                case 0:
                    mes = context.getString(R.string.january);
                    break;
                case 1:
                    mes = context.getString(R.string.february);
                    break;
                case 2:
                    mes = context.getString(R.string.march);
                    break;
                case 3:
                    mes = context.getString(R.string.april);
                    break;
                case 4:
                    mes = context.getString(R.string.may);
                    break;
                case 5:
                    mes = context.getString(R.string.june);
                    break;
                case 6:
                    mes = context.getString(R.string.july);
                    break;
                case 7:
                    mes = context.getString(R.string.august);
                    break;
                case 8:
                    mes = context.getString(R.string.september);
                    break;
                case 9:
                    mes = context.getString(R.string.october);
                    break;
                case 10:
                    mes = context.getString(R.string.november);
                    break;
                case 11:
                    mes = context.getString(R.string.august);
                    break;
            }
        }
        return dia + mes;
    }

    private boolean isDateInRange(Date date, Date first, Date last) {
        return (date.before(last) && date.after(first));
    }
    private void showDialog(int pos) {
        Task task = data.get(pos);
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_task);
        TextView name = dialog.findViewById(R.id.name);
        TextView state = dialog.findViewById(R.id.state);
        TextView type = dialog.findViewById(R.id.type);
        TextView priority = dialog.findViewById(R.id.task_priority);
        TextView date = dialog.findViewById(R.id.date);
        TextView progress = dialog.findViewById(R.id.progress);
        Button report = dialog.findViewById(R.id.report);
        report.setEnabled(task.getMaxSteps() > task.getSteps());
        dialog.show();
    }

}
