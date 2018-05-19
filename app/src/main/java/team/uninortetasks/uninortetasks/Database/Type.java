package team.uninortetasks.uninortetasks.Database;

public enum Type {

    goal("goal"),
    activity("activity"),
    homework("homework"),
    other("other");

    private String text;

    Type(String text) {
        this.text = text;
    }

    public static Type fromString(String type) {
        for (Type t : Type.values()) {
            if (t.text.equalsIgnoreCase(type)) return t;
        }
        return other;
    }

    @Override
    public String toString() {
        return this.text;
    }

}
