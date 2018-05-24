package team.uninortetasks.uninortetasks.Activities;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import team.uninortetasks.uninortetasks.Database.Category;
import team.uninortetasks.uninortetasks.Fragments.AddCategory;
import team.uninortetasks.uninortetasks.Fragments.TasksCategory;
import team.uninortetasks.uninortetasks.R;

public class TasksScreen extends AppCompatActivity implements AddCategory.OnAddCategoryListener, TasksCategory.OnTasksCategoryListener {

    private DrawerLayout root;
    private ActionBarDrawerToggle toogle;
    private NavigationView nav;
    private ActionBar actionBar;
    private Window window;
    private FragmentManager fragmentManager;
    private int currentCategoryIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_tasks);

        actionBar = getSupportActionBar();
        window = getWindow();
        fragmentManager = getSupportFragmentManager();

        actionBar.setTitle(R.string.tasks);

        nav = findViewById(R.id.categoriesBar);
        root = findViewById(R.id.tasks);

        toogle = new ActionBarDrawerToggle(this, root, R.string.open, R.string.close);
        root.addDrawerListener(toogle);
        toogle.syncState();

        actionBar.setDisplayHomeAsUpEnabled(true);

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
        Menu menu = nav.getMenu();
        menu.clear();
        menu.add(R.string.for_today).setIcon(R.drawable.ic_today).setOnMenuItemClickListener(item -> {

            actionBar.setTitle(R.string.for_today);
            window.setStatusBarColor(getResources().getColor(R.color.darkRed2));
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.darkRed)));
            loadView(null);
            item.setChecked(true);
            root.closeDrawers();
            return true;
        }).setChecked(currentCategoryIndex == -1);
        System.out.println("........................." + currentCategoryIndex);
        int pos = 0;
        for (Category cat : Category.getAll()) {
            final int temp = pos;
            menu.add(cat.getName()).setIcon(cat.getIcon()).setOnMenuItemClickListener(item -> {
                currentCategoryIndex = temp;
                System.out.println("Seteado a " + currentCategoryIndex);
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(cat.getColor())));
                window.setStatusBarColor(getResources().getColor(cat.getColor2()));
                actionBar.setTitle(cat.getName());
                loadView(cat);
                item.setChecked(true);
                root.closeDrawers();
                return true;
            }).setChecked(currentCategoryIndex == pos);
            System.out.println("........................." + currentCategoryIndex);
            System.out.println("........................." + pos);
            pos++;
        }
        menu.setGroupCheckable(0, true, true);

        menu.add("");
        menu.add(R.string.add_category).setIcon(R.drawable.ic_add).setOnMenuItemClickListener(item -> {
            actionBar.setTitle(R.string.add_category);
            window.setStatusBarColor(getResources().getColor(R.color.dark2));
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.dark)));
            fragmentManager.beginTransaction().replace(R.id.tasksContent, new AddCategory()).commit();
            root.closeDrawers();
            return true;
        });
        menu.add(R.string.edit_category).setIcon(R.drawable.ic_edit).setOnMenuItemClickListener(item -> {

            actionBar.setTitle(R.string.edit_category);
            window.setStatusBarColor(getResources().getColor(R.color.dark2));
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.dark)));
            root.closeDrawers();
            return true;
        });
        menu.add(R.string.delete_category).setIcon(R.drawable.ic_delete).setOnMenuItemClickListener(item -> {

            actionBar.setTitle(R.string.delete_category);
            window.setStatusBarColor(getResources().getColor(R.color.dark2));
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.dark)));
            root.closeDrawers();
            return true;
        });
    }

    private void loadView(Category category) {
        if (category == null) {

        } else {
            //fragmentManager.beginTransaction().replace(R.id.tasksContent, TasksCategory.newInstance(category));
        }

    }

    public void addTask(MenuItem item) {

    }

    @Override
    public void onAddingOkay(Category category) {
        Toast.makeText(this, "Categoría agregada con éxito", Toast.LENGTH_SHORT).show();
        currentCategoryIndex = category.getPositionInList();
        System.out.println("Nueva posicion " + currentCategoryIndex);
        loadCategories();
        //loadView(Category.getAll().get(currentCategoryIndex - 1));
    }

    @Override
    public void onAddingCanceled() {
        if (currentCategoryIndex >= 0) {
            loadView(Category.getAll().get(currentCategoryIndex));
        } else {
            currentCategoryIndex = -1;
            loadView(null);
        }
    }

    @Override
    public void onDisplayTask(Category category) {

    }
}
