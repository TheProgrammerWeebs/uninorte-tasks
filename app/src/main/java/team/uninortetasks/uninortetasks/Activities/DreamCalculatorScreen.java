package team.uninortetasks.uninortetasks.Activities;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.lang.reflect.Array;

import team.uninortetasks.uninortetasks.R;

public class DreamCalculatorScreen extends AppCompatActivity {

    private Spinner cdSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_dreamcalculator);

        cdSpinner = findViewById(R.id.type_of_dreamer_Spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.valores_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        cdSpinner.setAdapter(adapter);


    }
    }