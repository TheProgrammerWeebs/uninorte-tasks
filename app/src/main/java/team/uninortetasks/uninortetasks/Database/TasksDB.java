package team.uninortetasks.uninortetasks.Database;

import android.content.Context;
import android.widget.Toast;

import java.util.Date;

import javax.annotation.Nullable;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

public class TasksDB {

    private static RealmResults<Task> all;

    public static void init() {
        Realm realm = Realm.getDefaultInstance();
        all = realm.where(Task.class).findAll();
    }

    public static void add(final Context context, int id, String name, Date limit) {
        final Task task = new Task(id, name, Priority.high, State.pending, Type.goal, limit);
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

    @Nullable
    public static Task get(int id) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Task> results = realm.where(Task.class).equalTo("id", id).findAll();
        if (!results.isEmpty()) {
            return results.first();
        }
        return null;
    }

    public static RealmResults<Task> getAll() {
        return all;
    }

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

    public static void edit(final Task task) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(task);
            }
        });
    }

}
