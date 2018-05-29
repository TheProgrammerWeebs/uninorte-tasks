package team.uninortetasks.uninortetasks.Database;

import team.uninortetasks.uninortetasks.R;

public enum Priority {

    high(0, R.color.darkRed),
    medium(1, R.color.orange),
    low(2, R.color.yellow);

    private int index;
    private int color;

    Priority(int index, int color) {
        this.index = index;
        this.color = color;
    }

    public int toInt() {
        return this.index;
    }

    public int getColor() {
        return color;
    }

    public static Priority fromInt(int index) {
        return Priority.values()[index];
    }
}
