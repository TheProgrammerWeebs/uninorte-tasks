package team.uninortetasks.uninortetasks.Database;

import team.uninortetasks.uninortetasks.R;

public enum State {

    completed(0, R.string.completed),
    pending(1, R.string.pending),
    expired(2, R.string.expired);

    private int index;
    private int source;

    State(int index, int source) {
        this.index = index;
        this.source = source;
    }

    public int getSrc() {
        return source;
    }

    public int toInt() {
        return this.index;
    }

    public static State fromInt(int index) {
        return State.values()[index];
    }
}
