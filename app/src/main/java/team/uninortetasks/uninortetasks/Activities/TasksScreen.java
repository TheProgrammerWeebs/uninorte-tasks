package team.uninortetasks.uninortetasks.Activities;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Date;

import team.uninortetasks.uninortetasks.Database.Category;
import team.uninortetasks.uninortetasks.Database.Priority;
import team.uninortetasks.uninortetasks.Database.State;
import team.uninortetasks.uninortetasks.Database.Task;
import team.uninortetasks.uninortetasks.Database.Type;
import team.uninortetasks.uninortetasks.Fragments.TaskAdapter;
import team.uninortetasks.uninortetasks.R;

public class TasksScreen extends AppCompatActivity {

    private DrawerLayout root;
    private ActionBarDrawerToggle toogle;
    private NavigationView nav;
    private RecyclerView list;
    private int currentCategoryIndex = 0;

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
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.add_category_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            EditText categoryName = dialog.findViewById(R.id.categoryName);
            Button btCreateCategory = dialog.findViewById(R.id.btCreate);
            btCreateCategory.setOnClickListener(e -> {
                String name = categoryName.getText().toString();
                if (name.trim().isEmpty()){
                    return;
                }else{
                    Category.add(this, name);
                    dialog.cancel();
                }
            });
            dialog.show();
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
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_task_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final EditText name = dialog.findViewById(R.id.name);
        final Spinner category = dialog.findViewById(R.id.category);
        final Spinner priority = dialog.findViewById(R.id.priority);
        final Spinner type = dialog.findViewById(R.id.type);
        final DatePicker date = dialog.findViewById(R.id.date);
        final Button cancel = dialog.findViewById(R.id.cancel);
        final Button okay = dialog.findViewById(R.id.okay);

        cancel.setOnClickListener(view -> {
            dialog.cancel();
        });

        okay.setOnClickListener(view -> {
            if (name.getText().toString().isEmpty() || priority.getSelectedItem() == null || category.getSelectedItem() == null || type.getSelectedItem() == null) {
                return;
            }
            Task.add(this, name.getText().toString(), Priority.fromString(priority.getSelectedItem().toString()), State.pending, Type.fromString(type.getSelectedItem().toString()), new Date(date.getDayOfMonth(), date.getMonth(), date.getYear()), 0);
        });

        dialog.show();
    }
}
