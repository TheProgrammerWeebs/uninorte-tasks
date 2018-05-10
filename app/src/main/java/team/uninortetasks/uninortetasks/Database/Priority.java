package team.uninortetasks.uninortetasks.Database;

public enum Priority {

    high("high"),
    medium("medium"),
    low("low");

    private String text;

    Priority(String text) {
        this.text = text;
    }

    public static Priority fromString(String priority) {
        for (Priority p : Priority.values()) {
            if (p.text.equalsIgnoreCase(priority)) return p;
        }
        return high;
    }

    @Override
    public String toString() {
        return this.text;
    }

}
