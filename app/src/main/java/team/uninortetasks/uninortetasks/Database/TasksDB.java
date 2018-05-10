package team.uninortetasks.uninortetasks.Database;

import android.content.Context;
import android.widget.Toast;

import java.util.Date;
import java.util.Random;

import javax.annotation.Nullable;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

public class TasksDB {

    private static RealmResults<Task> all;

    /**
     * Inicializa la base de datos de tareas y obtiene la lista de todas las tareas registradas.
     */
    public static void init() {
        Realm realm = Realm.getDefaultInstance();
        all = realm.where(Task.class).findAll();
    }

    /**
     * Genera un Id aleatorio no existente para la creación de nuevas tareas.
     *
     * @return ID generado.
     */
    private static int generateId() {
        Realm realm = Realm.getDefaultInstance();
        Random random = new Random();
        int id;
        do {
            id = random.nextInt(9999) + 1;
        } while (realm.where(Task.class).equalTo("id", id).count() == 0);
        return id;
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
     */
    public static void add(final Context context, String name, Priority priority, State state, Type type, Date limit) {
        final Task task = new Task(TasksDB.generateId(), name, priority, state, type, limit);
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                    realm.insert(task);
                } catch (RealmPrimaryKeyConstraintException ignored) {
                    Toast.makeText(context, "ID Ingresado ya existe.", Toast.LENGTH_SHORT).show();
                }
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
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Task> results = realm.where(Task.class).equalTo("id", id).findAll();
        if (!results.isEmpty()) {
            return results.first();
        }
        return null;
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
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                    all.where().equalTo("id", id).findFirst().deleteFromRealm();
                } catch (NullPointerException ignored) {
                    Toast.makeText(context, "La tarea no existe.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Editar una tarea
     *
     * @param task Tarea a editar, la tarea debe tener el id de la que se va a editar
     */
    public static void edit(final Context context, final Task task) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                    realm.copyToRealmOrUpdate(task);
                } catch (RealmException ignored) {
                    Toast.makeText(context, "La tarea no existe.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
