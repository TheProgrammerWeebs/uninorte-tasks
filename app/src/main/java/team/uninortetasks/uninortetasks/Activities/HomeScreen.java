package team.uninortetasks.uninortetasks.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import team.uninortetasks.uninortetasks.R;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_home);
        setSupportActionBar(findViewById(R.id.homeToolbar));
    }

    public void onTasksClick(View view) {
        startActivity(new Intent(this, TasksScreen.class));
    }

    public void onCategoriesClick(View view) {
    }

    public void onForTodayClick(View view) {
    }
}
