package team.uninortetasks.uninortetasks.CustomViews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import team.uninortetasks.uninortetasks.Database.Task;
import team.uninortetasks.uninortetasks.R;

public class TaskView extends FrameLayout implements Serializable {

    private Task task;
    private TextView taskName;
    private TextView taskDate;
    private View priority;


    public TaskView(@NonNull Context context, Task task) {
        super(context);
        init(context,task);
    }

    public TaskView(@NonNull Context context, @Nullable AttributeSet attrs, Task task) {
        super(context, attrs);
        init(context, task);
    }

    public TaskView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, Task task) {
        super(context, attrs, defStyleAttr);
        init(context, task);
    }

    public TaskView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes, Task task) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, task);
    }

    private void init(Context context, Task task){
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

    private void initialize() {
        this.taskName.setText(task.getName());
        this.taskDate.setText(getDate());
        this.priority.setBackgroundColor(task.getPriority().getColor());
    }

    private String getDate(){
        String dia = "";
        String mes = "";
        Calendar sieteDias = Calendar.getInstance();
        sieteDias.add(Calendar.DATE, 7);
        if (task.getLimit().equals(new Date())){
            dia = "hoy";
        } else if (isDateInRange(task.getLimit(), Calendar.getInstance().getTime(), sieteDias.getTime())) {
            switch (task.getLimit().getDay()) {
                case 0:
                    dia = getContext().getString(R.string.sunday);
                    break;
                case 1:
                    dia = getContext().getString(R.string.monday);
                    break;
                case 2:
                    dia = getContext().getString(R.string.tuesday);
                    break;
                case 3:
                    dia = getContext().getString(R.string.wednesday);
                    break;
                case 4:
                    dia = getContext().getString(R.string.thursday);
                    break;
                case 5:
                    dia = getContext().getString(R.string.friday);
                    break;
                case 6:
                    dia = getContext().getString(R.string.saturday);
                    break;
            }
        }else{
            dia = task.getLimit().getDate() + " de ";
            switch (task.getLimit().getMonth()){
                case 0:
                    mes = getContext().getString(R.string.january);
                    break;
                case 1:
                    mes = getContext().getString(R.string.february);
                    break;
                case 2:
                    mes = getContext().getString(R.string.march);
                    break;
                case 3:
                    mes = getContext().getString(R.string.april);
                    break;
                case 4:
                    mes = getContext().getString(R.string.may);
                    break;
                case 5:
                    mes = getContext().getString(R.string.june);
                    break;
                case 6:
                    mes = getContext().getString(R.string.july);
                    break;
                case 7:
                    mes = getContext().getString(R.string.august);
                    break;
                case 8:
                    mes = getContext().getString(R.string.september);
                    break;
                case 9:
                    mes = getContext().getString(R.string.october);
                    break;
                case 10:
                    mes = getContext().getString(R.string.november);
                    break;
                case 11:
                    mes = getContext().getString(R.string.august);
                    break;
            }
        }
        return dia + mes;
    }

    private boolean isDateInRange(Date date, Date first, Date last) {
        return (date.before(last) && date.after(first));
    }


}
