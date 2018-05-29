package team.uninortetasks.uninortetasks.Database;

public enum Type {

    goal(0),
    activity(1),
    homework(2),
    other(3);

    private int index;

    Type(int index) {
        this.index = index;
    }

    public int toInt() {
        return index;
    }

    public static Type fromInt(int index) {
        return Type.values()[index];
    }
}
