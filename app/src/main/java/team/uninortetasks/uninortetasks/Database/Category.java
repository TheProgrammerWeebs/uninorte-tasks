package team.uninortetasks.uninortetasks.Database;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

import javax.annotation.Nullable;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

public class Category extends RealmObject {

    private static RealmResults<Category> all;
    private static ArrayList<OnDataChangeListener> listeners;
    private static ArrayList<Class> listenerFathers;
    @PrimaryKey
    private int id;
    @Required
    private String name;
    private int icon;
    private int style;
    @LinkingObjects("category")
    private final RealmResults<Task> tasks;

    public Category() {
        tasks = null;
    }

    public Category(int id, Icon icon, Style style, String name) {
        this.id = id;
        this.icon = icon.toInt();
        this.style = style.toInt();
        this.name = name;
        tasks = null;
    }

    public Category getEditableInstance() {
        return Realm.getDefaultInstance().copyFromRealm(this);
    }

    /**
     * Inicializa la base de datos de tareas y obtiene la lista de todas las tareas registradas.
     */
    public static void init() {
        listeners = new ArrayList<>();
        listenerFathers = new ArrayList<>();
        all = Realm.getDefaultInstance()
                .where(Category.class).sort("name").findAll();
    }

    static void dataChanged() {
        ArrayList<OnDataChangeListener> toRemove;
        for (OnDataChangeListener listener : listeners) {
            listener.onChange();
        }
    }

    public static void addDataChangeListener(Class father, OnDataChangeListener listener) {
        listeners.add(listener);
        listenerFathers.add(father);
    }

    public static void removeChangeListener(Class father) {
        for (int i = 0; i < listenerFathers.size(); i++) {
            if (father == listenerFathers.get(i)) {
                listenerFathers.remove(i);
                listeners.remove(i);
            }
        }
    }

    /**
     * Genera un Id aleatorio no existente para la creación de nuevas categorías.
     *
     * @return ID generado.
     */
    private static int generateId() {
        Number maxID = Realm.getDefaultInstance()
                .where(Category.class).max("id");
        return (maxID == null) ? 1 : maxID.intValue() + 1;
    }

    /**
     * Agrega una nueva tarea a la base de datos.
     *
     * @param context Contexto del que se llama al método (Activity).
     * @param name    Nombre de la tarea.
     */
    public static Category add(final Context context, String name, Icon icon, Style style) {
        final Category category = new Category(Category.generateId(), icon, style, name);
        Realm.getDefaultInstance()
                .executeTransaction(r -> {
                    try {
                        r.copyToRealmOrUpdate(category);
                    } catch (RealmPrimaryKeyConstraintException ignored) {
                        Toast.makeText(context, "ID Ingresado ya existe.", Toast.LENGTH_SHORT).show();
                    }
                });
        dataChanged();
        return category;
    }

    /**
     * Obtiene la tarea con el ID pasado como parámetro.
     *
     * @param id ID de la tarea.
     * @return Tarea encontrada o null si el ID no existe.
     */
    @Nullable
    public static Category get(int id) {
        return Realm.getDefaultInstance()
                .where(Category.class).equalTo("id", id).findFirst();
    }

    /**
     * Obtiene todas las categorías registradas en la base de datos.
     *
     * @return Lista de categorías.
     */
    public static RealmResults<Category> getAll() {
        return all;
    }

    /**
     * Eliminar una tarea
     *
     * @param context Contexto en el que se realiza la operación (Activity)
     * @param id      ID De la tarea a eliminar
     */
    public static void remove(final Context context, final int id) {
        Realm.getDefaultInstance()
                .executeTransaction(r -> {
                    try {
                        Category cat = r.where(Category.class).equalTo("id", id).findFirst();
                        cat.getTasks().deleteAllFromRealm();
                        cat.deleteFromRealm();
                    } catch (NullPointerException ignored) {
                        Toast.makeText(context, "La tarea no existe.", Toast.LENGTH_SHORT).show();
                    }
                });
        dataChanged();
    }

    public static void remove(Context context, Category category) {
        Realm.getDefaultInstance()
                .executeTransaction(r -> {
                    try {
                        category.getTasks().deleteAllFromRealm();
                        category.deleteFromRealm();
                    } catch (NullPointerException ignored) {
                        Toast.makeText(context, "La tarea no existe.", Toast.LENGTH_SHORT).show();
                    }
                });
        dataChanged();
    }

    /**
     * Editar una tarea
     *
     * @param category Categoría a editar, la categoría debe tener el id de la que se va a editar
     * @deprecated
     */
    public static Category edit(final Context context, final Category category) {
        Realm.getDefaultInstance()
                .executeTransaction(r -> {
                            try {
                                r.copyToRealmOrUpdate(category);
                            } catch (RealmException ignored) {
                                Toast.makeText(context, "La tarea no existe.", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
        dataChanged();
        return category;
    }

    public int cleanDb(Context context, boolean all, boolean completed, boolean expired) {
        int count = 0;
        if (all) {
            count = this.tasks.size();
            Realm.getDefaultInstance()
                    .executeTransaction(r -> {
                        try {
                            this.tasks.deleteAllFromRealm();
                        } catch (NullPointerException ignored) {
                            Toast.makeText(context, "La tarea no existe.", Toast.LENGTH_SHORT).show();
                        }
                    });
            Task.dataChanged();
        } else {
            if (completed) {
                final RealmResults<Task> toDelete = this.tasks.where().equalTo("type", Type.goal.toInt()).equalTo("state", State.completed.toInt()).findAll();
                count += toDelete.size();
                Realm.getDefaultInstance()
                        .executeTransaction(r -> {
                            try {
                                toDelete.deleteAllFromRealm();
                            } catch (NullPointerException ignored) {
                                Toast.makeText(context, "La tarea no existe.", Toast.LENGTH_SHORT).show();
                            }
                        });
                Task.dataChanged();
            }
            if (expired) {
                final RealmResults<Task> toDelete = this.tasks.where().equalTo("diaryTask", false).equalTo("state", State.expired.toInt()).findAll();
                count += toDelete.size();
                Realm.getDefaultInstance()
                        .executeTransaction(r -> {
                            try {
                                toDelete.deleteAllFromRealm();
                            } catch (NullPointerException ignored) {
                                Toast.makeText(context, "La tarea no existe.", Toast.LENGTH_SHORT).show();
                            }
                        });
                Task.dataChanged();
            }
        }
        return count;
    }

    public int getPositionInList() {
        int pos = 0;
        for (Category c : all) {
            if (c.getId() == id) {
                return pos;
            }
            pos++;
        }
        return -1;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Category setName(String name) {
        this.name = name;
        return this;
    }

    public Icon getIcon() {
        return Icon.fromInt(this.icon);
    }

    public Category setIcon(Icon icon) {
        this.icon = icon.toInt();
        return this;
    }

    public Style getStyle() {
        return Style.fromInt(this.style);
    }

    public Category setStyle(Style style) {
        this.style = style.toInt();
        return this;
    }

    public RealmResults<Task> getTasks() {
        return this.tasks;
    }

    public Category save(Context context) {
        return edit(context, this);
    }

    public interface OnDataChangeListener {
        void onChange();
    }

}
