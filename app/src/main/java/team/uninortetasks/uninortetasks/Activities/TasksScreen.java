package team.uninortetasks.uninortetasks.Activities;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import team.uninortetasks.uninortetasks.Database.Category;
import team.uninortetasks.uninortetasks.Fragments.AddCategory;
import team.uninortetasks.uninortetasks.R;

public class TasksScreen extends AppCompatActivity implements AddCategory.OnFragmentInteractionListener {

    private DrawerLayout root;
    private ActionBarDrawerToggle toogle;
    private NavigationView nav;
    private int currentCategoryIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_tasks);
        getSupportActionBar().setTitle(R.string.tasks);

        nav = findViewById(R.id.categoriesBar);
        root = findViewById(R.id.tasks);

        toogle = new ActionBarDrawerToggle(this, root, R.string.open, R.string.close);
        root.addDrawerListener(toogle);
        toogle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadCategories();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tasks_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toogle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadCategories() {
        SubMenu menu = nav.getMenu().findItem(R.id.categories).getSubMenu();
        menu.clear();
        menu.add(R.string.for_today).setIcon(R.drawable.ic_today).setOnMenuItemClickListener(item -> {
            getSupportActionBar().setTitle(R.string.for_today);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.darkRed)));
            loadView(null);
            item.setChecked(true);
            root.closeDrawers();
            return true;
        });
        for (Category cat : Category.getAll()) {
            menu.add(cat.getName()).setIcon(cat.getIcon()).setOnMenuItemClickListener(item -> {
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(cat.getColor()));
                getSupportActionBar().setTitle(cat.getName());

                loadView(cat);
                item.setChecked(true);
                root.closeDrawers();
                return true;
            });
        }
        nav.getMenu().findItem(R.id.add).setOnMenuItemClickListener(item -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.tasksContent, new AddCategory()).commit();
            root.closeDrawers();
            return true;
        });
        nav.getMenu().findItem(R.id.edit).setOnMenuItemClickListener(item -> {


            root.closeDrawers();
            return true;
        });
        nav.getMenu().findItem(R.id.delete).setOnMenuItemClickListener(item -> {


            root.closeDrawers();
            return true;
        });
        menu.setGroupCheckable(0, true, true);
    }

    private void loadView(Category category) {
//        if (category == null) {
//
//
//            list.setAdapter(new TaskAdapter(this, Task.tasksForToday()));
//            return;
//        }
//        list.setAdapter(new TaskAdapter(this, Task.getByCategory(category.getId())));
    }

    public void addTask(MenuItem item) {


    }

    @Override
    public void onAddingFinished() {
        loadCategories();
    }
}
