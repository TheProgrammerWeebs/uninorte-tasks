package team.uninortetasks.uninortetasks.Database;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

public class Note extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String title;
    @Required
    private String content;
    private Task task;

    public Note() {
    }

    public Note(int id, String title, String content, Task task) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.task = task;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public Note setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return this.content;
    }

    public Note setContent(String content) {
        this.content = content;
        return this;
    }

    public Task getTask() {
        return this.task;
    }

    public Note setTask(Task task) {
        this.task = task;
        return this;
    }

    public void Save(Context context) {
        edit(context, this);
    }

    private static RealmResults<Note> all;

    public static void init(Context context) {
        all = Realm.getDefaultInstance()
                .where(Note.class).sort("title").findAll();
    }

    private static int generateId() {
        Number maxID = Realm.getDefaultInstance()
                .where(Note.class).max("id");
        return (maxID == null) ? 1 : maxID.intValue() + 1;
    }

    public static void add(final Context context,
                           String title,
                           String content,
                           Task task) {
        final Note note = new Note(Note.generateId(), title, content, task);
        Realm.getDefaultInstance()
                .executeTransaction(r -> {
                    try {
                        r.insert(note);
                    } catch (RealmPrimaryKeyConstraintException e) {
                        Toast.makeText(context, "El ID seleccionado ya existe.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Nullable
    public static Note get(int id) {
        return Realm.getDefaultInstance()
                .where(Note.class).equalTo("id", id).findFirst();
    }

    public static RealmResults<Note> getAll() {
        return all;
    }

    public static void remove(final Context context, final int id) {
        Realm.getDefaultInstance()
                .executeTransaction(r -> {
                    try {
                        all.where().equalTo("id", id).findFirst().deleteFromRealm();
                    } catch (NullPointerException ignored) {
                        Toast.makeText(context, "La nota no existe.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * @deprecated
     */
    public static void edit(final Context context, final Note note) {
        Realm.getDefaultInstance()
                .executeTransaction(r -> {
                            try {
                                r.copyToRealmOrUpdate(note);
                            } catch (RealmException ignored) {
                                Toast.makeText(context, "La nota no existe.", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
    }
}
