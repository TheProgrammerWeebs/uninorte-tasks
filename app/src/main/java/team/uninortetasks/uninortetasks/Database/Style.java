package team.uninortetasks.uninortetasks.Database;

import team.uninortetasks.uninortetasks.R;

public enum Style {

    darkRed(R.style.redTheme, 0, R.color.darkRed, R.color.darkRed2),
    orange(R.style.orangeTheme, 1, R.color.orange, R.color.orange2),
    green(R.style.greenTheme, 2, R.color.green, R.color.green2),
    cyan(R.style.cyanTheme, 3, R.color.cyan, R.color.cyan2),
    purple(R.style.purpleTheme, 4, R.color.purple, R.color.purple2);

    private int source;
    private int color1;
    private int color2;
    private int index;

    Style(int source, int index, int color1, int color2) {
        this.source = source;
        this.index = index;
        this.color1 = color1;
        this.color2 = color2;
    }

    public int getSrc() {
        return this.source;
    }

    public int getColor1() {
        return this.color1;
    }

    public int getColor2() {
        return this.color2;
    }

    public int toInt() {
        return this.index;
    }

    public static Style fromInt(int index) {
        return Style.values()[index];
    }
}
