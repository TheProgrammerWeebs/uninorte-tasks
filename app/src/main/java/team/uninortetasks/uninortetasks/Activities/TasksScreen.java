package team.uninortetasks.uninortetasks.Activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.Toast;

import team.uninortetasks.uninortetasks.Database.Category;
import team.uninortetasks.uninortetasks.Database.Task;
import team.uninortetasks.uninortetasks.Fragments.TaskAdapter;
import team.uninortetasks.uninortetasks.R;

public class TasksScreen extends AppCompatActivity {

    private DrawerLayout root;
    private ActionBarDrawerToggle toogle;
    private NavigationView nav;
    private RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks_screen);
        getSupportActionBar().setTitle(R.string.tasks);

        nav = findViewById(R.id.categoriesBar);
        root = findViewById(R.id.tasks);
        list = findViewById(R.id.tasksContent);

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
            list.setAdapter(new TaskAdapter(this, Task.tasksForToday()));
            return;
        }
        list.setAdapter(new TaskAdapter(this, Task.getByCategory(category.getId())));
    }

    public void addTask(MenuItem item) {
        Toast.makeText(this, "Add a task", Toast.LENGTH_SHORT).show();
    }
}
