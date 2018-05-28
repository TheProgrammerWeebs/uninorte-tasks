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
    @LinkingObjects("task")
    private final RealmResults<Note> notes;
    @PrimaryKey
    private int id;
    @Required
    private String name;
    private RealmList<Integer> days;
    private Category category;
    @Required
    private String priority;
    @Required
    private String state;
    @Required
    private String type;
    @Required
    private Date limit;
    private boolean haveSteps;
    private boolean diaryTask;
    private int steps;
    private int maxSteps;

    public Task() {
        notes = null;
    }

    public Task(int id, String name, Priority priority, State state, Type type, Date limit, boolean haveSteps, boolean diaryTask, int maxSteps, Category category) {
        this.id = id;
        this.name = name;
        this.priority = priority.toString();
        this.state = state.toString();
        this.type = type.toString();
        this.limit = limit;
        this.haveSteps = haveSteps;
        this.diaryTask = diaryTask;
        this.category = category;
        this.steps = 0;
        this.maxSteps = maxSteps;
        this.days = new RealmList<>();
        notes = null;
    }

    public Task getEditableInstance() {
        return Realm.getDefaultInstance().copyFromRealm(this);
    }

    /**
     * Inicializa la base de datos de tareas y obtiene la lista de todas las tareas registradas.
     */
    public static void init() {
        all = Realm.getDefaultInstance()
                .where(Task.class).sort("limit").findAll();
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
                        category.addTask(task);
                        for (int i : days) {
                            task.addDay(i);
                        }
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

    public static RealmList<Task> getByCategory(int id) {
        RealmList<Task> tasks = new RealmList<>();
        tasks.addAll(Realm.getDefaultInstance()
                .where(Task.class).equalTo("id", id).sort("limit").findAll());
        return tasks;
    }

    public static RealmList<Task> tasksForToday() {
        RealmList<Task> tasks = new RealmList<>();
        Date hoy = Calendar.getInstance().getTime();
        for (Task task : all) {
            Date date = task.getLimit();
            if (hoy.getDay() == date.getDay() && hoy.getMonth() == date.getMonth() && hoy.getYear() == date.getYear()) {
                tasks.add(task);
            }
        }
        return tasks;
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

    public Task addNote(Note note) {
        this.notes.add(note);
        return this;
    }

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

    public Category getCategory() {
        return this.category;
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

    public boolean haveStepes() {
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

    public void save(Context context) {
        edit(context, this);
    }

}
