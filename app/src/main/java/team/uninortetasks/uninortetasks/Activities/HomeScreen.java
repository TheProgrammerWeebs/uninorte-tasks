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
        setContentView(R.layout.home_screen);
        setSupportActionBar(findViewById(R.id.homeToolbar));
    }

    public void onTasksClick(View view) {
        startActivity(new Intent(this, TasksScreen.class));
    }

    public void onCategoriesClick(View view) {
        startActivity(new Intent(this, CategoriesScreen.class));
    }

    public void onForTodayClick(View view) {
        startActivity(new Intent(this, ForTodayScreen.class));
    }
}
