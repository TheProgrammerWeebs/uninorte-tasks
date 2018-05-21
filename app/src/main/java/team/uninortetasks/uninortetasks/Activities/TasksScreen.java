package team.uninortetasks.uninortetasks.Activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.SubMenu;

import team.uninortetasks.uninortetasks.Database.Category;
import team.uninortetasks.uninortetasks.Fragments.CategoryFragment;
import team.uninortetasks.uninortetasks.R;

public class TasksScreen extends AppCompatActivity {

    private DrawerLayout root;
    private ActionBarDrawerToggle toogle;
    private NavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks_screen);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toogle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadCategories() {
        SubMenu menu = nav.getMenu().findItem(R.id.categories).getSubMenu();
        menu.clear();
        menu.add("Para hoy").setIcon(R.drawable.ic_today).setOnMenuItemClickListener(item -> {
            loadView(null);
            item.setChecked(true);
            setTitle(item.getTitle());
            root.closeDrawers();
            return true;
        });
        for (Category cat : Category.getAll()) {
            menu.add(cat.getName()).setIcon(R.drawable.ic_item).setOnMenuItemClickListener(item -> {
                loadView(cat);
                item.setChecked(true);
                setTitle(item.getTitle());
                root.closeDrawers();
                return true;
            });
        }
        nav.getMenu().add("Agregar categoría").setIcon(R.drawable.ic_add).setOnMenuItemClickListener(item -> {
//Agregar categoria
            root.closeDrawers();
            return true;
        });
        nav.getMenu().add("Editar categoría").setIcon(R.drawable.ic_edit).setOnMenuItemClickListener(item -> {
//Editar categoria
            root.closeDrawers();
            return true;
        });
        nav.getMenu().add("Eliminar categoría").setIcon(R.drawable.ic_delete).setOnMenuItemClickListener(item -> {
//Eliminar categoria
            root.closeDrawers();
            return true;
        });
        menu.setGroupCheckable(0, true, true);
    }

    private void loadView(Category category) {
        if (category == null) {
            //Usar @paramc ategory para cargar cada categoria
        }
        Class fClass = CategoryFragment.class;
        try {
            Fragment f = (Fragment) fClass.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.tasksContent, f).commit();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
