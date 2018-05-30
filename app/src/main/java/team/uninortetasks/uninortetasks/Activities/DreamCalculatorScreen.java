package team.uninortetasks.uninortetasks.Activities;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import team.uninortetasks.uninortetasks.R;

public class DreamCalculatorScreen extends AppCompatActivity {
    private Button btCalculate;
    private Button sleepWake;
    private Button selectTime;
    private TextView fiveCicles;
    private Button btFiveButton;
    private TextView fourCicles;
    private Button btFourButton;
    private TextView threeCicles;
    private Button btThreeButton;
    private TextView twoCicles;
    private Button btTwoButton;
    private TextView youShould;

    private boolean dormir;
    private Calendar hour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_dreamcalculator);
        hour = null;
        dormir = false;
        bindControls();
    }

    private void bindControls(){
        youShould = findViewById(R.id.you_should);

        btCalculate = findViewById(R.id.calculate);
        btCalculate.setOnClickListener(e -> onCalculateClick());

        selectTime = findViewById(R.id.select_time);
        selectTime.setOnClickListener(e -> onTimeClick());

        sleepWake = findViewById(R.id.bt_wake_sleep);
        sleepWake.setOnClickListener(e -> onSleepWakeClick());

        fiveCicles = findViewById(R.id.five_cycles);
        btFiveButton = findViewById(R.id.bt_five_cycles);
        btFiveButton.setVisibility(View.INVISIBLE);
        fourCicles = findViewById(R.id.four_cycles);
        btFourButton = findViewById(R.id.bt_four_cycles);
        btFourButton.setVisibility(View.INVISIBLE);
        threeCicles = findViewById(R.id.three_cycles);
        btThreeButton = findViewById(R.id.bt_three_cycles);
        btThreeButton.setVisibility(View.INVISIBLE);
        twoCicles = findViewById(R.id.two_cycles);
        btTwoButton = findViewById(R.id.bt_two_cycles);
        btTwoButton.setVisibility(View.INVISIBLE);
    }

    private void onCalculateClick(){
        if (hour == null){
            Toast.makeText(this, "Debe ingresar una hora", Toast.LENGTH_SHORT);
        }else{
            Calendar[] horas = new Calendar[4];
            youShould.setText(getString(R.string.you_should)
                    + " " + (dormir ? getString(R.string.wake_up) : getString(R.string.sleep))
                    + " " +getString(R.string.next_hours));
            for (int i = 0; i < horas.length; i++){
                horas[i] = (Calendar) hour.clone();
                //Cada hora tiene i+3 ciclos de 90 minutos. Si se tiene marcada la opción
                //"ir a dormir", se le agregan a la hora actual. Sino, se le restan
                horas[i].add(Calendar.MINUTE, (dormir ?  1 :-1) * (i+3) * 90);
                System.out.println("minutos: " + (dormir ? 1 : -1) * (i+3) * 90);
                System.out.println(horas[i].getTime().toString());
                if (i > 0){
                    System.out.println("Hora anterior: " + horas[i-1].getTime().toString());
                }
            }
            fiveCicles.setText(new SimpleDateFormat("hh:mm a").format(horas[3].getTime()));
            btFiveButton.setVisibility(View.VISIBLE);
            fourCicles.setText(new SimpleDateFormat("hh:mm a").format(horas[2].getTime()));
            btFourButton.setVisibility(View.VISIBLE);
            threeCicles.setText(new SimpleDateFormat("hh:mm a").format(horas[1].getTime()));
            btThreeButton.setVisibility(View.VISIBLE);
            twoCicles.setText(new SimpleDateFormat("hh:mm a").format(horas[0].getTime()));
            btTwoButton.setVisibility(View.VISIBLE);
        }
    }

    private void onSleepWakeClick(){
        dormir = !dormir;
        if (dormir){
            sleepWake.setText(getString(R.string.sleep));
        }else{
            sleepWake.setText(getString(R.string.wake_up));
        }
    }

    private void onTimeClick(){
        Calendar currentTime = Calendar.getInstance();
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour = Calendar.getInstance();
                hour.set(Calendar.HOUR, hourOfDay);
                hour.set(Calendar.MINUTE, minute);
                if (hour.before(Calendar.getInstance())){
                    hour.add(Calendar.DATE, 1);
                }
                System.out.println(hour.getTime().toString());
                selectTime.setText(new SimpleDateFormat("hh:mm a").format(hour.getTime()));
            }
        }, currentTime.get(Calendar.HOUR), currentTime.get(Calendar.MINUTE), false).show();
    }
}