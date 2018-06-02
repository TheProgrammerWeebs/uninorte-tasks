package team.uninortetasks.uninortetasks.Activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import team.uninortetasks.uninortetasks.Database.Category;
import team.uninortetasks.uninortetasks.Database.Task;
import team.uninortetasks.uninortetasks.Fragments.DashboardFragment;
import team.uninortetasks.uninortetasks.Fragments.ForTodayFragment;
import team.uninortetasks.uninortetasks.Others.TabsAdapter;
import team.uninortetasks.uninortetasks.R;
import team.uninortetasks.uninortetasks.Services.NotificationUtils;

public class HomeScreen extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_home);
        //Inicializa la conexion con la base de datos de Realm
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
        Task.init();
        Category.init();
//        startService(new Intent(this, NotificationService.class));
//        NotificationUtils.removeNotification(this);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        TabsAdapter adapter = new TabsAdapter(getSupportFragmentManager());

        final TextView tasks = findViewById(R.id.nForToday);
        tasks.setText("Tareas para hoy: " + Task.tasksForToday().size());

        Task.addDataChangeListener(HomeScreen.class, () -> tasks.setText("Tareas para hoy: " + Task.tasksForToday().size()));

        adapter.addFragment(new ForTodayFragment(), getResources().getString(R.string.for_today));
        adapter.addFragment(new DashboardFragment(), getResources().getString(R.string.all));
//        adapter.addFragment(new ForTodayFragment(), getResources().getString(R.string.for_today));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onPause() {
        super.onPause();
        NotificationUtils.displayNotification(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        NotificationUtils.removeNotification(this);
    }

}
