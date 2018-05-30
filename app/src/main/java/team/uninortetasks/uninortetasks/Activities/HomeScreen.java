package team.uninortetasks.uninortetasks.Activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import team.uninortetasks.uninortetasks.Database.Task;
import team.uninortetasks.uninortetasks.Fragments.DashboardFragment;
import team.uninortetasks.uninortetasks.Fragments.ForTodayFragment;
import team.uninortetasks.uninortetasks.Others.TabsAdapter;
import team.uninortetasks.uninortetasks.R;

public class HomeScreen extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_home);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        TabsAdapter adapter = new TabsAdapter(getSupportFragmentManager());

        ((TextView) findViewById(R.id.nForToday)).setText("Tareas para hoy: " + Task.tasksForToday().size());

        adapter.addFragment(new ForTodayFragment(), getResources().getString(R.string.for_today));
        adapter.addFragment(new DashboardFragment(), getResources().getString(R.string.all));
//        adapter.addFragment(new ForTodayFragment(), getResources().getString(R.string.for_today));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
