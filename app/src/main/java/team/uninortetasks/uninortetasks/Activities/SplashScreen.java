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

import team.uninortetasks.uninortetasks.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_splash);
        int header = setSplash();
        startApp(header);
    }

    private void startApp(int header) {
        final Intent intent = new Intent(this, HomeScreen.class);
        Bundle params = new Bundle();
        params.putInt("header", header);
        intent.putExtras(params);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_splash);
        ImageView logo = findViewById(R.id.logo);
        LinearLayout view = findViewById(R.id.splashb);
        TextView splashText = findViewById(R.id.splashtext);
        logo.startAnimation(animation);
        splashText.startAnimation(animation);
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(intent);
                    finish();
                }
            }
        }.start();
    }

    private int setSplash() {
        ImageView logo = findViewById(R.id.logo);
        LinearLayout view = findViewById(R.id.splashb);
        TextView splashText = findViewById(R.id.splashtext);
        int color;
        int img;
        int header;
        String text;
        switch (new Random().nextInt(3)) {
            case 0:
                color = R.color.deer;
                img = R.drawable.deer;
                header = R.drawable.deerimg;
                text = getResources().getString(R.string.deer);
                break;
            case 1:
                color = R.color.fish;
                img = R.drawable.fish;
                header = R.drawable.fishimg;
                text = getResources().getString(R.string.fish);
                break;
            case 2:
                color = R.color.flyingowl;
                img = R.drawable.flyingowl;
                header = R.drawable.flyingowlimg;
                text = getResources().getString(R.string.flyingowl);
                break;
            default:
                color = R.color.rooster;
                img = R.drawable.rooster;
                header = R.drawable.roosterimg;
                text = getResources().getString(R.string.rooster);
        }
        view.setBackgroundColor(getResources().getColor(color));
        getWindow().setStatusBarColor(getResources().getColor(color));
        logo.setImageDrawable(getResources().getDrawable(img));
        splashText.setText(text);
        return header;
    }

}
