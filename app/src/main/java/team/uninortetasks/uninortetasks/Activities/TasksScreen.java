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
import team.uninortetasks.uninortetasks.Fragments.AddTask;
import team.uninortetasks.uninortetasks.Fragments.TasksCategory;
import team.uninortetasks.uninortetasks.R;

public class TasksScreen extends AppCompatActivity implements
        AddCategory.OnAddCategoryListener,
        TasksCategory.OnTasksCategoryListener,
        AddTask.OnAddingTaskListener {

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
        setTheme(R.style.redTheme);
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
            currentCategoryIndex = -1;
            actionBar.setTitle(R.string.for_today);
            setStyle(R.style.redTheme);
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
                setStyle(cat.getStyle());
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
            setStyle(R.style.themeDark);
            fragmentManager.beginTransaction().replace(R.id.tasksContent, new AddCategory()).commit();
            root.closeDrawers();
            return true;
        });
        menu.add(R.string.edit_category).setIcon(R.drawable.ic_edit).setOnMenuItemClickListener(item -> {

            actionBar.setTitle(R.string.edit_category);
            root.closeDrawers();
            return true;
        });
        menu.add(R.string.delete_category).setIcon(R.drawable.ic_delete).setOnMenuItemClickListener(item -> {

            actionBar.setTitle(R.string.delete_category);
            root.closeDrawers();
            return true;
        });
    }

    private void setStyle(int resource) {
        setTheme(resource);
        switch (resource) {
            case R.style.greenTheme:
                window.setStatusBarColor(getResources().getColor(R.color.green2));
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.green)));
                break;
            case R.style.redTheme:
                window.setStatusBarColor(getResources().getColor(R.color.darkRed2));
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.darkRed)));
                break;
            case R.style.cyanTheme:
                window.setStatusBarColor(getResources().getColor(R.color.cyan2));
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.cyan)));
                break;
            case R.style.purpleTheme:
                window.setStatusBarColor(getResources().getColor(R.color.purple2));
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple)));
                break;
            case R.style.orangeTheme:
                window.setStatusBarColor(getResources().getColor(R.color.orange2));
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.orange)));
                break;
        }
    }

    private void loadView(Category category) {
        if (category == null) {

        } else {
            fragmentManager.beginTransaction().replace(R.id.tasksContent, TasksCategory.newInstance(category)).commit();
        }

    }

    public void addTask(MenuItem item) {
        if (currentCategoryIndex == -1) {
            Toast.makeText(this, "Seleccione primero una categoría", Toast.LENGTH_SHORT).show();
        } else {
            fragmentManager.beginTransaction().replace(R.id.tasksContent, AddTask.newInstance(Category.getAll().get(currentCategoryIndex))).commit();
        }
    }

    @Override
    public void onAddingOkay(Category category) {
        Toast.makeText(this, "Categoría agregada con éxito", Toast.LENGTH_SHORT).show();
        currentCategoryIndex = category.getPositionInList();
        System.out.println("Nueva posicion " + currentCategoryIndex);
        loadCategories();
        loadView(Category.getAll().get(currentCategoryIndex));
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

    @Override
    public void addingFinished(Category category) {
        loadView(category);
    }
}
