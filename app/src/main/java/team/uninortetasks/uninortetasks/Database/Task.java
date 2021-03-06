package team.uninortetasks.uninortetasks.Database;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

public class Task extends RealmObject {

    private static RealmResults<Task> all;
    private static ArrayList<OnDataChangeListener> listeners;
    private static ArrayList<Class> listenerFathers;
    @LinkingObjects("task")
    private final RealmResults<Note> notes;
    @PrimaryKey
    private int id;
    @Required
    private String name;
    private RealmList<Integer> days;
    private Category category;
    private int priority;
    private int state;
    private int type;
    @Required
    private Date limit;
    private boolean haveSteps;
    private boolean diaryTask;
    private int steps;
    private int maxSteps;

    public Task() {
        notes = null;
    }

    public Task(Task task) {
        notes = task.notes;
        id = generateId();
        name = task.name;
        days = task.days;
        category = task.category;
        priority = task.priority;
        state = task.state;
        type = task.type;
        limit = task.limit;
        haveSteps = task.haveSteps;
        diaryTask = task.diaryTask;
        steps = task.steps;
        maxSteps = task.maxSteps;
    }

    public Task(int id, String name, Priority priority, State state, Type type, Date limit, boolean haveSteps, boolean diaryTask, int maxSteps, Category category) {
        this.id = id;
        this.name = name;
        this.priority = priority.toInt();
        this.state = state.toInt();
        this.type = type.toInt();
        this.limit = limit;
        this.haveSteps = haveSteps;
        this.diaryTask = diaryTask;
        this.category = category;
        this.steps = 0;
        this.maxSteps = maxSteps;
        this.days = new RealmList<>();
        notes = null;
    }

    /**
     * Inicializa la base de datos de tareas y obtiene la lista de todas las tareas registradas.
     */
    public static void init() {
        listeners = new ArrayList<>();
        listenerFathers = new ArrayList<>();
        all = Realm.getDefaultInstance()
                .where(Task.class).sort("limit").findAll();
    }

    /**
     * Método que se llama cuando se realizan cambios en la base de datos, llama a todos los listeners añadidos
     */
    static void dataChanged() {
        ArrayList<OnDataChangeListener> toRemove;
        for (OnDataChangeListener listener : listeners) {
            listener.onChange();
        }
    }

    /**
     * Genera un Id aleatorio no existente para la creación de nuevas tareas.
     *
     * @return ID generado.
     */
    public static int generateId() {
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
     * @param maxSteps Número de pasos necesarios para completar la tarea;
     */
    public static void add(
            final Context context,
            String name,
            Priority priority,
            State state,
            Type type,
            Date limit,
            boolean haveSteps,
            boolean diaryTask,
            int maxSteps,
            Category category,
            ArrayList<Integer> days) {
        final Task task = new Task(Task.generateId(), name, priority, state, type, limit, haveSteps, diaryTask, maxSteps, category);
        Realm.getDefaultInstance()
                .executeTransaction(r -> {
                    try {
                        r.copyToRealm(task);
                        for (int i : days) {
                            task.addDay(i);
                        }
                    } catch (RealmPrimaryKeyConstraintException ignored) {
                        Toast.makeText(context, "ID Ingresado ya existe.", Toast.LENGTH_SHORT).show();
                    }
                });
        dataChanged();
    }

    /**
     * Agregar una tarea a la base de datos
     *
     * @param context Contexto del que se llama al método
     * @param task Tarea a agregar
     */
    public static void add(final Context context, final Task task) {
        Realm.getDefaultInstance()
                .executeTransaction(r -> {
                    try {
                        r.copyToRealm(task);
                        for (Day i : task.getDays()) {
                            task.addDay(i.toInt());
                        }
                    } catch (RealmPrimaryKeyConstraintException ignored) {
                        Toast.makeText(context, "ID Ingresado ya existe.", Toast.LENGTH_SHORT).show();
                    }
                });
        dataChanged();
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
     * Obtiene una lista de tareas segun una categoría
     *
     * @param id ID de la categoría
     * @return Retorna un RealmList de tareas que cuya clase es la indicada
     */
    public static RealmList<Task> getByCategory(int id) {
        RealmList<Task> tasks = new RealmList<>();
        tasks.addAll(Realm.getDefaultInstance()
                .where(Task.class).equalTo("id", id).sort("limit").findAll());
        return tasks;
    }

    /**
     * Obtiene una lista de tareas para hoy
     *
     * @return Retorna una RealmList de tareas para el día actual
     */
    public static RealmResults<Task> tasksForToday() {
        Date today = Calendar.getInstance().getTime();
        Date now = Calendar.getInstance().getTime();
        today.setHours(0);
        today.setMinutes(0);
        today.setSeconds(0);
        return Realm.getDefaultInstance()
                .where(Task.class).between("limit", today, now).sort("priority").findAll();
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
        dataChanged();
    }

    /**
     * Elimia una tarea de la base de datos
     *
     * @param context Contexto del que se llama al método
     * @param task Tarea a eliminar
     */
    public static void remove(Context context, Task task) {
        Realm.getDefaultInstance()
                .executeTransaction(r -> {
                    try {
                        task.deleteFromRealm();
                    } catch (NullPointerException ignored) {
                        Toast.makeText(context, "La tarea no existe.", Toast.LENGTH_SHORT).show();
                    }
                });
        dataChanged();
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
        dataChanged();
    }

    /**
     * Agregar un listener de cambios en la base de datos
     *
     * @param father Clase padre del listener
     * @param listener ChangeListener para cambios
     */
    public static void addDataChangeListener(Class father, OnDataChangeListener listener) {
        listeners.add(listener);
        listenerFathers.add(father);
    }

    /**
     * Elimina todos los listener pertenecientes a una clase padre
     *
     * @param father Clase padre
     */
    public static void removeChangeListener(Class father) {
        for (int i = 0; i < listenerFathers.size(); i++) {
            if (father == listenerFathers.get(i)) {
                listenerFathers.remove(i);
                listeners.remove(i);
            }
        }
    }

    /**
     * Obtiene una instancia para edición
     *
     * @return Tarea lista para editar
     */
    public Task getEditableInstance() {
        return Realm.getDefaultInstance().copyFromRealm(this);
    }

    /**
     * Agrega una nota a la tarea
     *
     * @param note Nota a agregar
     * @return Retorna una nota a la tarea
     */
    public Task addNote(Note note) {
        this.notes.add(note);
        return this;
    }

    /**
     * Agrega un día a las tareas de tipo diarias
     *
     * @param day Día a agregar
     * @return Retorna la tarea
     */
    public Task addDay(int day) {
        days.add(day);
        return this;
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

    public RealmResults<Note> getNotes() {
        return this.notes;
    }

    public Day[] getDays() {
        return Day.toArray(this.days);
    }

    public Task setDays(RealmList<Integer> days) {
        this.days = days;
        return this;
    }

    public Category getCategory() {
        return this.category;
    }

    public Priority getPriority() {
        return Priority.fromInt(this.priority);
    }

    public Task setPriority(Priority priority) {
        this.priority = priority.toInt();
        return this;
    }

    public State getState() {
        return State.fromInt(this.state);
    }

    public Task setState(State state) {
        this.state = state.toInt();
        return this;
    }

    public Type getType() {
        return Type.fromInt(this.type);
    }

    public Task setType(Type type) {
        this.type = type.toInt();
        return this;
    }

    public Date getLimit() {
        return this.limit;
    }

    public Task setLimit(Date limit) {
        this.limit = limit;
        return this;
    }

    public boolean haveSteps() {
        return haveSteps;
    }

    public Task setHaveSteps(boolean haveSteps) {
        this.haveSteps = haveSteps;
        return this;
    }

    public boolean isDiaryTask() {
        return this.diaryTask;
    }

    public Task setDiaryTask(boolean diaryTask) {
        this.diaryTask = diaryTask;
        return this;
    }

    public int getSteps() {
        return this.steps;
    }

    public Task setSteps(int steps) {
        this.steps = steps;
        return this;
    }

    public int getMaxSteps() {
        return maxSteps;
    }

    public void setMaxSteps(int maxSteps) {
        this.maxSteps = maxSteps;
    }

    public Task nextStep() {
        this.steps--;
        return this;
    }

    /**
     * Guarda los cambios realizados
     *
     * @param context
     */
    public void save(Context context) {
        edit(context, this);
    }

    /**
     *
     */
    public interface OnDataChangeListener {
        void onChange();
    }

}
