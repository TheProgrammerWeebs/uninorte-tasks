package team.uninortetasks.uninortetasks.Database;

import io.realm.RealmList;

public enum Day {
    sunday(0),
    monday(1),
    tuesday(2),
    wednesday(3),
    thursday(4),
    friday(5),
    saturday(6);

    private int day;

    Day(int day) {
        this.day = day;
    }

    public static Day fromInt(int day) {
        switch (day) {
            case 0:
                return sunday;
            case 1:
                return monday;
            case 2:
                return tuesday;
            case 3:
                return wednesday;
            case 4:
                return thursday;
            case 5:
                return friday;
            default:
                return saturday;
        }
    }

    public static Day[] toArray(RealmList<Integer> list) {
        Day[] days = new Day[list.size()];
        int pos = 0;
        for (int i : list) {
            days[pos] = Day.fromInt(i);
            pos++;
        }
        return days;
    }

    public int toInt() {
        return day;
    }
}
