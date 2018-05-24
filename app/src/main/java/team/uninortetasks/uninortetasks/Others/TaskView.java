package team.uninortetasks.uninortetasks.Others;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.Date;

import team.uninortetasks.uninortetasks.Database.Priority;
import team.uninortetasks.uninortetasks.Database.Task;
import team.uninortetasks.uninortetasks.R;

public class TaskView extends LinearLayout {
    private Task task;
    private TextView taskName;
    private TextView taskDate;
    private View priority;

    public TaskView(Context context, Task task) {
        super(context);
        inflate(context, task);
    }

    public TaskView(Context context, AttributeSet attrs, Task task) {
        super(context, attrs);
        inflate(context, task);
    }

    public TaskView(Context context,
                    AttributeSet attrs,
                    int defStyle,
                    Task task) {
        super(context, attrs, defStyle);
        inflate(context, task);
    }

    private void inflate(Context context, Task task){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.task_view_layout, this);
        this.task = task;
    }

    @Override
    protected void onFinishInflate(){
        super.onFinishInflate();
        this.taskName = this.findViewById(R.id.task_name);
        this.taskDate = this.findViewById(R.id.task_date);
        this.priority = this.findViewById(R.id.priority);
        initialize();
    }

    private void initialize(){
        priority.setBackgroundColor(ContextCompat.getColor(getContext(),
                task.getPriority() == Priority.high ?   R.color.darkRed :
                task.getPriority() == Priority.medium ? R.color.yellow:
                                                        R.color.deer));
        taskName.setText(task.getName());
        taskDate.setText(task.getLimit().toString());
    }



}
