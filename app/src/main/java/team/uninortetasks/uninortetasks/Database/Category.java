package team.uninortetasks.uninortetasks.Database;

import android.content.Context;
import android.widget.Toast;

import javax.annotation.Nullable;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

public class Category extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String name;
    @LinkingObjects("categories")
    private RealmList<Task> tasks;

    public Category() {
    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
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

    public RealmList<Task> getTasks() {
        return this.tasks;
    }

    public void save(Context context) {
        edit(context, this);
    }

    private static RealmResults<Category> all;

    /**
     * Inicializa la base de datos de tareas y obtiene la lista de todas las tareas registradas.
     */
    public static void init() {
        all = Realm.getDefaultInstance()
                .where(Category.class).findAll();
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
    public static void add(final Context context, String name) {
        final Category category = new Category(Category.generateId(), name);
        Realm.getDefaultInstance()
                .executeTransaction(r -> {
                    try {
                        r.insert(category);
                    } catch (RealmPrimaryKeyConstraintException ignored) {
                        Toast.makeText(context, "ID Ingresado ya existe.", Toast.LENGTH_SHORT).show();
                    }
                });
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
                        r.where(Category.class).equalTo("id", id).findFirst().deleteFromRealm();
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
    public static void edit(final Context context, final Category category) {
        Realm.getDefaultInstance()
                .executeTransaction(r -> {
                            try {
                                r.copyToRealmOrUpdate(category);
                            } catch (RealmException ignored) {
                                Toast.makeText(context, "La tarea no existe.", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
    }

}
