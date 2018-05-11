package team.uninortetasks.uninortetasks.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

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
        LinearLayout view = findViewById(R.id.splashb);
        TextView splashText = findViewById(R.id.splashtext);
        int color;
        int img;
        String text;
        switch (new Random().nextInt(5)) {
            case 0:
                color = R.color.deer;
                img = R.drawable.deer;
                text = getResources().getString(R.string.deer);
                break;
            case 1:
                color = R.color.fish;
                img = R.drawable.fish;
                text = getResources().getString(R.string.fish);
                break;
            case 2:
                color = R.color.flyingowl;
                img = R.drawable.flyingowl;
                text = getResources().getString(R.string.flyingowl);
                break;
            case 3:
                color = R.color.rooster;
                img = R.drawable.rooster;
                text = getResources().getString(R.string.rooster);
                break;
            default:
                color = R.color.wolf;
                img = R.drawable.wolf;
                splashText.setTextColor(getResources().getColor(R.color.lighttext));
                text = getResources().getString(R.string.wolf);
        }
        view.setBackgroundColor(getResources().getColor(color));
        getWindow().setStatusBarColor(getResources().getColor(color));
        logo.setImageDrawable(getResources().getDrawable(img));
        splashText.setText(text);
        logo.startAnimation(animation);
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
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
