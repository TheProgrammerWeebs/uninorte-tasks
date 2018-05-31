package team.uninortetasks.uninortetasks.Database;

import team.uninortetasks.uninortetasks.R;

public enum Icon {

    defaultt(R.drawable.ic_item, 0),
    money(R.drawable.ic_money, 1),
    audio(R.drawable.ic_audio, 2),
    book(R.drawable.ic_book, 3),
    laptop(R.drawable.ic_laptop, 4),
    flight(R.drawable.ic_flight, 5),
    bed(R.drawable.ic_bed, 6),
    gym(R.drawable.ic_gym, 7),
    home(R.drawable.ic_home, 8);

    private int resource;
    private int index;

    Icon(int resource, int index) {
        this.resource = resource;
        this.index = index;
    }

    public static Icon fromInt(int index) {
        return Icon.values()[index];
    }

    public int getsrc() {
        return resource;
    }

    public int toInt() {
        return index;
    }
}
