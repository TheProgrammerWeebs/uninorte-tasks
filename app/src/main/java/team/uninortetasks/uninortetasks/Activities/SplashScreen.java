package team.uninortetasks.uninortetasks.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import team.uninortetasks.uninortetasks.Database.TasksDB;
import team.uninortetasks.uninortetasks.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        //Inicializa la conexion con la base de datos de Realm
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
        TasksDB.init();
        startApp();
    }

    private void startApp() {
        final Intent intent = new Intent(this, Home.class);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splashanimation);
        ImageView logo = findViewById(R.id.logo);
        logo.startAnimation(animation);
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(intent);
                    finish();
                }
            }
        }.start();
    }

}
