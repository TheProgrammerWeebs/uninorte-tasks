package team.uninortetasks.uninortetasks.Others;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import io.realm.RealmList;
import team.uninortetasks.uninortetasks.Database.Day;
import team.uninortetasks.uninortetasks.Database.Month;
import team.uninortetasks.uninortetasks.Database.State;
import team.uninortetasks.uninortetasks.Database.Task;
import team.uninortetasks.uninortetasks.Database.Type;
import team.uninortetasks.uninortetasks.R;

public class DialogHelper {

    public static void taskDialog(Context context, Task task) {
        Dialog dialog = new Dialog(context);
        if (task.getType() == Type.goal) {
            goalDialog(context, dialog, task);
        } else {
            if (task.isDiaryTask()) {
                diaryTaskDialog(context, dialog, task);
            } else {
                uniqueTaskDialog(context, dialog, task);
            }
        }
        dialog.show();
    }

    private static void goalDialog(Context context, Dialog dialog, Task task) {
        final int actualProgress = task.getSteps();
        final int maxProgress = task.getMaxSteps();
        dialog.setContentView(R.layout.dialog_havesteps_task);
        final EditText name = dialog.findViewById(R.id.name);
        final TextView
                state = dialog.findViewById(R.id.state),
                priority = dialog.findViewById(R.id.priority),
                progress = dialog.findViewById(R.id.progress),
                dateLabel = dialog.findViewById(R.id.dateLabel);
        final EditText advance = dialog.findViewById(R.id.advance);
        name.setText(task.getName());
        state.setText(task.getState().getSrc());
        priority.setText(task.getPriority().getSrc());
        progress.setText("Progreso: " + actualProgress + "/" + maxProgress);
        Date limit = task.getLimit();
        dateLabel.setText(limit.getDay() + " " + context.getString(R.string.of) + " " + context.getString(Month.fromInt(limit.getMonth()).getSrc()) + " " + context.getString(R.string.of) + " " + limit.getYear());

        dialog.findViewById(R.id.noButton).setOnClickListener(e -> dialog.cancel());
        dialog.findViewById(R.id.reportAdvance).setOnClickListener(e -> {
            String adv = advance.getText().toString().trim();
            if (adv.isEmpty()) {
                Toast.makeText(context, "Digite una cantidad de progreso", Toast.LENGTH_SHORT).show();
            } else {
                progress.setText("Progreso: " + Math.min(actualProgress + Integer.parseInt(adv), maxProgress) + "/" + maxProgress);
            }
        });
        dialog.findViewById(R.id.yesButton).setOnClickListener(e -> {
            String n = name.getText().toString().trim();
            if (n.isEmpty()) {
                Toast.makeText(context, "Seleccione un nombre para la tarea", Toast.LENGTH_SHORT).show();
            } else {
                int adv = task.getSteps();
                String advStr = advance.getText().toString().trim();
                if (!advStr.isEmpty()) {
                    adv += Integer.parseInt(advStr);
                }
                if (adv >= maxProgress) {
                    task.getEditableInstance()
                            .setSteps(Math.min(adv, maxProgress))
                            .setName(n)
                            .setState(State.completed)
                            .save(context);
                } else {
                    task.getEditableInstance()
                            .setSteps(Math.min(adv, maxProgress))
                            .setName(n)
                            .save(context);
                }
                dialog.cancel();
                Toast.makeText(context, "Cambios guardados", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private static void diaryTaskDialog(Context context, Dialog dialog, Task task) {
        dialog.setContentView(R.layout.dialog_diary_task);
        final EditText name = dialog.findViewById(R.id.name);
        final TextView
                state = dialog.findViewById(R.id.state),
                priority = dialog.findViewById(R.id.priority);
        final CheckBox
                monday = dialog.findViewById(R.id.monday),
                tuesday = dialog.findViewById(R.id.tuesday),
                wednesday = dialog.findViewById(R.id.wednesday),
                thursday = dialog.findViewById(R.id.thursday),
                friday = dialog.findViewById(R.id.friday),
                saturday = dialog.findViewById(R.id.saturday),
                sunday = dialog.findViewById(R.id.sunday);

        for (Day day : task.getDays()) {
            switch (day) {
                case monday:
                    monday.setChecked(true);
                    break;
                case tuesday:
                    tuesday.setChecked(true);
                    break;
                case wednesday:
                    wednesday.setChecked(true);
                    break;
                case thursday:
                    thursday.setChecked(true);
                    break;
                case friday:
                    friday.setChecked(true);
                    break;
                case saturday:
                    saturday.setChecked(true);
                    break;
                case sunday:
                    sunday.setChecked(true);
                    break;
            }
        }
        name.setText(task.getName());
        state.setText(task.getState().getSrc());
        priority.setText(task.getPriority().getSrc());

        dialog.findViewById(R.id.noButton).setOnClickListener(e -> dialog.cancel());
        dialog.findViewById(R.id.yesButton).setOnClickListener(e -> {
            String n = name.getText().toString().trim();
            if (n.isEmpty()) {
                Toast.makeText(context, "Seleccione un nombre para la tarea", Toast.LENGTH_SHORT).show();
            } else {
                RealmList<Integer> days = new RealmList<>();
                if (monday.isChecked()) days.add(Day.monday.toInt());
                if (tuesday.isChecked()) days.add(Day.tuesday.toInt());
                if (wednesday.isChecked()) days.add(Day.wednesday.toInt());
                if (thursday.isChecked()) days.add(Day.thursday.toInt());
                if (friday.isChecked()) days.add(Day.friday.toInt());
                if (saturday.isChecked()) days.add(Day.saturday.toInt());
                if (sunday.isChecked()) days.add(Day.sunday.toInt());
                if (days.isEmpty()) {
                    Toast.makeText(context, "Seleccione por lo menos un día", Toast.LENGTH_SHORT).show();
                } else {
                    task.getEditableInstance().setName(name.getText().toString()).setDays(days).save(context);
                    dialog.cancel();
                }
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private static void uniqueTaskDialog(Context context, Dialog dialog, Task task) {
        dialog.setContentView(R.layout.dialog_unique_task);
        final EditText name = dialog.findViewById(R.id.name);
        final TextView
                state = dialog.findViewById(R.id.state),
                priority = dialog.findViewById(R.id.priority),
                dateLabel = dialog.findViewById(R.id.dateLabel);

        name.setText(task.getName());
        state.setText(task.getState().getSrc());
        priority.setText(task.getPriority().getSrc());
        Date limit = task.getLimit();
        dateLabel.setText(limit.getDay() + " " + context.getString(R.string.of) + " " + context.getString(Month.fromInt(limit.getMonth()).getSrc()) + " " + context.getString(R.string.of) + " " + limit.getYear());

        dialog.findViewById(R.id.noButton).setOnClickListener(e -> dialog.cancel());
        dialog.findViewById(R.id.yesButton).setOnClickListener(e -> {
            String n = name.getText().toString().trim();
            if (n.isEmpty()) {
                Toast.makeText(context, "Seleccione un nombre para la tarea", Toast.LENGTH_SHORT).show();
            } else {
                task.getEditableInstance().setName(n).save(context);
                dialog.cancel();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }



}
