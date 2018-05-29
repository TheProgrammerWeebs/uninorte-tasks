package team.uninortetasks.uninortetasks.Database;

import team.uninortetasks.uninortetasks.R;

public enum Month {
    january(R.string.january),
    february(R.string.february),
    march(R.string.march),
    april(R.string.april),
    may(R.string.may),
    june(R.string.june),
    july(R.string.july),
    august(R.string.august),
    september(R.string.september),
    october(R.string.october),
    november(R.string.november),
    december(R.string.december);

    private int text;

    Month(int text) {
        this.text = text;
    }

    public static Month fromInt(int month) {
        switch (month) {
            case 0:
                return january;
            case 1:
                return february;
            case 2:
                return march;
            case 3:
                return april;
            case 4:
                return may;
            case 5:
                return june;
            case 6:
                return july;
            case 7:
                return august;
            case 8:
                return september;
            case 9:
                return october;
            case 10:
                return november;
            default:
                return december;
        }
    }

    public int toInt() {
        return text;
    }
}
