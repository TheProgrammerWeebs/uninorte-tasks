package team.uninortetasks.uninortetasks.Database;

import team.uninortetasks.uninortetasks.R;

public enum Priority {

    high(0, R.color.darkRed, R.string.high_priority),
    medium(1, R.color.orange, R.string.medium_priority_short),
    low(2, R.color.yellow, R.string.low_priority);

    private int index;
    private int color;
    private int source;

    Priority(int index, int color, int source) {
        this.index = index;
        this.color = color;
        this.source = source;
    }

    public static Priority fromInt(int index) {
        return Priority.values()[index];
    }

    public int getSrc() {
        return this.source;
    }

    public int toInt() {
        return this.index;
    }

    public int getColor() {
        return this.color;
    }
}
