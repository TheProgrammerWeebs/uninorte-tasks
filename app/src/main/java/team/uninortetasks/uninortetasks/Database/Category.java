package team.uninortetasks.uninortetasks.Database;

import java.util.Arrays;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Category extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String name;
    private RealmList<Task> tasks;

    public Category() {
    }

    public Category(int id, String name, Task... tasks) {
        this.id = id;
        this.name = name;
        this.tasks = new RealmList<>();
        this.tasks.addAll(Arrays.asList(tasks));
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<Task> getTasks() {
        return this.tasks;
    }

    public void setTasks(RealmList<Task> tasks) {
        this.tasks = tasks;
    }
}
