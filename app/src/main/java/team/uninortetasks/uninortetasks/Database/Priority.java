package team.uninortetasks.uninortetasks.Database;

import team.uninortetasks.uninortetasks.R;

public enum Priority {

    high("high", R.color.darkRed),
    medium("medium", R.color.orange),
    low("low", R.color.yellow);

    private String text;
    private int color;

    Priority(String text, int color) {
        this.text = text;
        this.color = color;
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

    public int getColor() {
        return color;
    }

}
