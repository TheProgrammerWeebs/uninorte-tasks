package team.uninortetasks.uninortetasks.Database;

public enum State {

    completed("completed"),
    pending("pending"),
    expired("expired");

    private String text;

    State(String text) {
        this.text = text;
    }

    public static State fromString(String state) {
        for (State s : State.values()) {
            if (s.text.equalsIgnoreCase(state)) return s;
        }
        return expired;
    }

    @Override
    public String toString() {
        return this.text;
    }

}
