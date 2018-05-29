package team.uninortetasks.uninortetasks.Activities;

import android.app.Dialog;
import android.graphics.Color;
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
import team.uninortetasks.uninortetasks.Database.Style;
import team.uninortetasks.uninortetasks.Fragments.AddCategoryFragment;
import team.uninortetasks.uninortetasks.Fragments.AddTaskFragment;
import team.uninortetasks.uninortetasks.Fragments.NoCategoriesFragment;
import team.uninortetasks.uninortetasks.Fragments.TasksFragment;
import team.uninortetasks.uninortetasks.R;

public class TasksScreen extends AppCompatActivity implements
        AddCategoryFragment.OnAddCategoryListener,
        TasksFragment.OnTasksCategoryListener,
        AddTaskFragment.OnAddingTaskListener {

    private DrawerLayout root;
    private ActionBarDrawerToggle toogle;
    private NavigationView nav;
    private ActionBar actionBar;
    private Window window;
    private FragmentManager fragmentManager;
    private int currentCategoryIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Category.getAll().isEmpty()) {
            currentCategoryIndex = -1;
        }
        setContentView(R.layout.screen_tasks);

        actionBar = getSupportActionBar();
        window = getWindow();
        fragmentManager = getSupportFragmentManager();

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
        int pos = 0;
        if (currentCategoryIndex == -1) {
            loadView(null);
        } else {
            for (Category cat : Category.getAll()) {
                final int temp = pos;
                menu.add(cat.getName()).setIcon(cat.getIcon().getsrc()).setOnMenuItemClickListener(item -> {
                    currentCategoryIndex = temp;
                    setStyle(cat.getStyle());
                    loadView(cat);
                    item.setChecked(true);
                    actionBar.setTitle(cat.getName());
                    root.closeDrawers();
                    return true;
                }).setChecked(currentCategoryIndex == pos);
                pos++;
            }
            menu.setGroupCheckable(0, true, true);

            menu.add("");
            Category c = Category.getAll().get(currentCategoryIndex);
            setStyle(c.getStyle());
            loadView(c);
        }
        menu.add(R.string.add_category).setIcon(R.drawable.ic_add).setOnMenuItemClickListener(item -> {
            fragmentManager.beginTransaction().replace(R.id.tasksContent, new AddCategoryFragment()).commit();
            actionBar.setTitle(R.string.add_category);
            root.closeDrawers();
            return true;
        });
        if (currentCategoryIndex != -1) {
            menu.add(R.string.edit_category).setIcon(R.drawable.ic_edit).setOnMenuItemClickListener(item -> {
                if (currentCategoryIndex == -1) {
                    Toast.makeText(this, "No hay categorías registradas", Toast.LENGTH_SHORT).show();
                } else {
                    fragmentManager.beginTransaction().replace(R.id.tasksContent, AddCategoryFragment.newEditInstance(Category.getAll().get(currentCategoryIndex).getId())).commit();
                }
                actionBar.setTitle(R.string.edit_category);
                root.closeDrawers();
                return true;
            });
            menu.add(R.string.delete_category).setIcon(R.drawable.ic_delete).setOnMenuItemClickListener(item -> {
                if (currentCategoryIndex == -1) {
                    Toast.makeText(this, "No hay categorías registradas", Toast.LENGTH_SHORT).show();
                } else {
                    showRemoveDialog();
                }
                actionBar.setTitle(R.string.delete_category);
                root.closeDrawers();
                return true;
            });
        }
    }

    private void showRemoveDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_delete_category);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.findViewById(R.id.noButton).setOnClickListener(e -> dialog.cancel());
        dialog.findViewById(R.id.yesButton).setOnClickListener(e -> {
            Category.remove(this, Category.getAll().get(currentCategoryIndex));
            Toast.makeText(this, "Categoría eliminada con éxito", Toast.LENGTH_SHORT).show();
            if (Category.getAll().isEmpty()) {
                currentCategoryIndex = -1;
            } else {
                currentCategoryIndex = 0;
            }
            loadCategories();
            dialog.cancel();
        });
        dialog.show();
    }

    private void setStyle(Style style) {
        setTheme(style.getSrc());
        window.setStatusBarColor(getResources().getColor(style.getColor2()));
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(style.getColor1())));
    }

    private void loadView(Category category) {
        if (category == null) {
            actionBar.setTitle(R.string.tasks);
            fragmentManager.beginTransaction().replace(R.id.tasksContent, new NoCategoriesFragment()).commit();
        } else {
            actionBar.setTitle(category.getName());
            fragmentManager.beginTransaction().replace(R.id.tasksContent, TasksFragment.newInstance(category)).commit();
        }

    }

    public void addTask(MenuItem item) {
        if (currentCategoryIndex == -1) {
            Toast.makeText(this, "No hay categorias registradas", Toast.LENGTH_SHORT).show();
        } else {
            fragmentManager.beginTransaction().replace(R.id.tasksContent, AddTaskFragment.newInstance(Category.getAll().get(currentCategoryIndex))).commit();
        }
    }

    @Override
    public void onOperationOkay(Category category, boolean editMode) {
        if (editMode) {
            Toast.makeText(this, "Categoría editada con éxito", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Categoría agregada con éxito", Toast.LENGTH_SHORT).show();
        }
        currentCategoryIndex = category.getPositionInList();
        loadCategories();
    }

    @Override
    public void onOperationCanceled() {
        if (currentCategoryIndex >= 0) {
            loadView(Category.getAll().get(currentCategoryIndex));
        } else {
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
