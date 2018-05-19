package team.uninortetasks.uninortetasks.Database;

import android.content.Context;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Date;

import javax.annotation.Nullable;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

public class Task extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String name;
    @Required
    private RealmList<Category> categories;
    @Required
    private String priority;
    @Required
    private String state;
    @Required
    private String type;
    @Required
    private Date limit;
    @Required
    private int steps;

    public Task() {
    }

    Task(int id, String name, Priority priority, State state, Type type, Date limit, int steps, Category... categories) {
        this.id = id;
        this.name = name;
        this.priority = priority.toString();
        this.state = state.toString();
        this.type = type.toString();
        this.limit = limit;
        this.categories = new RealmList<>();
        this.categories.addAll(Arrays.asList(categories));
        this.steps = steps;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Task setName(String name) {
        this.name = name;
        return this;
    }

    public RealmList<Category> getCategories() {
        return this.categories;
    }

    public Priority getPriority() {
        return Priority.fromString(this.priority);
    }

    public Task setPriority(Priority priority) {
        this.priority = priority.toString();
        return this;
    }

    public State getState() {
        return State.fromString(this.state);
    }

    public Task setState(State state) {
        this.state = state.toString();
        return this;
    }

    public Type getType() {
        return Type.fromString(this.type);
    }

    public Task setType(Type type) {
        this.type = type.toString();
        return this;
    }

    public Date getLimit() {
        return this.limit;
    }

    public Task setLimit(Date limit) {
        this.limit = limit;
        return this;
    }

    public int getSteps() {
        return this.steps;
    }

    public Task setSteps(int steps) {
        this.steps = steps;
        return this;
    }

    public Task nextStep() {
        this.steps--;
        return this;
    }

    public void saveChanges(Context context) {
        edit(context, this);
    }

    private static RealmResults<Task> all;

    /**
     * Inicializa la base de datos de tareas y obtiene la lista de todas las tareas registradas.
     */
    public static void init() {
        all = Realm.getDefaultInstance()
                .where(Task.class).findAll();
    }

    /**
     * Genera un Id aleatorio no existente para la creación de nuevas tareas.
     *
     * @return ID generado.
     */
    private static int generateId() {
        Number maxID = Realm.getDefaultInstance()
                .where(Task.class).max("id");
        return (maxID == null) ? 1 : maxID.intValue() + 1;
    }

    /**
     * Agrega una nueva tarea a la base de datos.
     *
     * @param context  Contexto del que se llama al método (Activity).
     * @param name     Nombre de la tarea.
     * @param priority Prioridad de la tarea.
     * @param state    Estado actual de la tarea.
     * @param type     Tipo de tarea.
     * @param limit    Tiempo límite.
     * @param steps    Número de pasos necesarios para completar la tarea;
     */
    public static void add(final Context context, String name, Priority priority, State state, Type type, Date limit, int steps) {
        final Task task = new Task(Task.generateId(), name, priority, state, type, limit, steps);
        Realm.getDefaultInstance()
                .executeTransaction(r -> {
                    try {
                        r.insert(task);
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
    public static Task get(int id) {
        return Realm.getDefaultInstance()
                .where(Task.class).equalTo("id", id).findFirst();
    }

    /**
     * Obtiene todas las tareas registradas en la base de datos.
     *
     * @return Lista de tareas.
     */
    public static RealmResults<Task> getAll() {
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
                        all.where().equalTo("id", id).findFirst().deleteFromRealm();
                    } catch (NullPointerException ignored) {
                        Toast.makeText(context, "La tarea no existe.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Editar una tarea
     *
     * @param task Tarea a editar, la tarea debe tener el id de la que se va a editar
     * @deprecated
     */
    public static void edit(final Context context, final Task task) {
        Realm.getDefaultInstance()
                .executeTransaction(r -> {
                            try {
                                r.copyToRealmOrUpdate(task);
                            } catch (RealmException ignored) {
                                Toast.makeText(context, "La tarea no existe.", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
    }

}
