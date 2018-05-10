package team.uninortetasks.uninortetasks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import io.realm.Realm;
import team.uninortetasks.uninortetasks.Database.TasksDB;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        Realm.init(this);
//        RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
//        Realm.setDefaultConfiguration(config);
        TasksDB.init();
    }

    public void onClick(View view) {
        Toast.makeText(this, "Entra onclick", Toast.LENGTH_LONG).show();
        EditText id = findViewById(R.id.id);
        EditText name = findViewById(R.id.name);
        EditText extra = findViewById(R.id.extra);
        TextView date = findViewById(R.id.textView);
        System.out.println("add");
        TasksDB.add(this, Integer.parseInt(id.getText().toString()), name.getText().toString(), new Date());
        System.out.println("get");
        date.setText(TasksDB.get(Integer.parseInt(id.getText().toString())).getName());
    }
}
