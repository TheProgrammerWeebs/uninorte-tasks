package team.uninortetasks.uninortetasks.Database;

import android.content.Context;
import android.widget.Toast;

import javax.annotation.Nullable;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

public class Category extends RealmObject {

    private static RealmResults<Category> all;
    @PrimaryKey
    private int id;
    @Required
    private String name;
    private int icon;
    private int style;
    private RealmList<Task> tasks;
    int a;

    public Category() {
    }

    public Category(int id, Icon icon, Style style, String name) {
        this.id = id;
        this.icon = icon.toInt();
        this.style = style.toInt();
        this.name = name;
        this.tasks = new RealmList<>();
    }

    public Category getEditableInstance() {
        return Realm.getDefaultInstance().copyFromRealm(this);
    }

    /**
     * Inicializa la base de datos de tareas y obtiene la lista de todas las tareas registradas.
     */
    public static void init() {
        all = Realm.getDefaultInstance()
                .where(Category.class).sort("name").findAll();
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
        return category;
    }

    public Category addTask(Task task) {
        this.tasks.add(task);
        return this;
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

    public RealmList<Task> getTasks() {
        return this.tasks;
    }

    public Category save(Context context) {
        return edit(context, this);
    }

}
