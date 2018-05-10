package team.uninortetasks.uninortetasks.Database;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Task extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String name;
    @Required
    private String priority;
    @Required
    private String state;
    @Required
    private String type;
    @Required
    private Date limit;

    public Task() {
    }

    Task(int id, String name, Priority priority, State state, Type type, Date limit) {
        this.id = id;
        this.name = name;
        this.priority = priority.toString();
        this.state = state.toString();
        this.type = type.toString();
        this.limit = limit;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Priority getPriority() {
        return Priority.fromString(this.priority);
    }

    public void setPriority(Priority priority) {
        this.priority = priority.toString();
    }

    public State getState() {
        return State.fromString(this.state);
    }

    public void setState(State state) {
        this.state = state.toString();
    }

    public Type getType() {
        return Type.fromString(this.type);
    }

    public void setType(Type type) {
        this.type = type.toString();
    }

    public Date getLimit() {
        return this.limit;
    }

    public void setLimit(Date limit) {
        this.limit = limit;
    }
}
