package team.uninortetasks.uninortetasks.Activities;

import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import team.uninortetasks.uninortetasks.R;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        NavigationView nav = findViewById(R.id.navigator);
        View header = nav.getHeaderView(0);
        ImageView headerImg = header.findViewById(R.id.headerimg);
        Bundle b = getIntent().getExtras();
        headerImg.setImageDrawable(getResources().getDrawable(b.getInt("header")));
    }
}
