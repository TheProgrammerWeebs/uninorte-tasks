package team.uninortetasks.uninortetasks.Activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageView;

import team.uninortetasks.uninortetasks.Database.Category;
import team.uninortetasks.uninortetasks.R;

public class Home extends AppCompatActivity {

    private DrawerLayout root;
    private ActionBarDrawerToggle toogle;
    private NavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen_old);

        nav = findViewById(R.id.navigator);
        root = findViewById(R.id.root);

        View header = nav.getHeaderView(0);
        ImageView headerImg = header.findViewById(R.id.headerimg);
        headerImg.setImageDrawable(getResources().getDrawable(getIntent().getExtras().getInt("header")));

        toogle = new ActionBarDrawerToggle(this, root, R.string.open, R.string.close);
        root.addDrawerListener(toogle);
        toogle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setElevation(1);

        loadCategories();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toogle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    private void loadCategories() {
//        SubMenu menu = nav.getMenu().findItem(R.id.tasksgroup).getSubMenu();
//        menu.clear();
//        menu.add("Para hoy").setIcon(R.drawable.ic_today).setOnMenuItemClickListener(item -> {
//            Class fClass = CategoryFragment.class;
//            try {
//                Fragment f = (Fragment) fClass.newInstance();
//                getSupportFragmentManager().beginTransaction().replace(R.id.content, f).commit();
//                item.setChecked(true);
//                setTitle(item.getTitle());
//                root.closeDrawers();
//            } catch (InstantiationException | IllegalAccessException e) {
//                e.printStackTrace();
//            }
//            return true;
//        });
//        for (Category cat : Category.getAll()) {
//            menu.add(cat.getName()).setIcon(R.drawable.ic_cat).setOnMenuItemClickListener(item -> {
//                Class fClass = CategoryFragment.class;
//                try {
//                    Fragment f = (Fragment) fClass.newInstance();
//                    getSupportFragmentManager().beginTransaction().replace(R.id.content, f).commit();
//                    item.setChecked(true);
//                    setTitle(item.getTitle());
//                    root.closeDrawers();
//                } catch (InstantiationException | IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//                return true;
//            });
//        }
//        menu.setGroupCheckable(0, true, true);
    }
}
