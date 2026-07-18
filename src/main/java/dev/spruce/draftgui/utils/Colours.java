package dev.spruce.draftgui.utils;

import com.raylib.Raylib;

public class Colours {

    public static final Raylib.Color DARKEST = rgb(40, 50, 90);
    public static final Raylib.Color DARKER = rgb(60, 70, 123);
    public static final Raylib.Color DARK = rgb(80, 88, 156);
    public static final Raylib.Color MEDIUM = rgb(99, 108, 203);
    public static final Raylib.Color LIGHT = rgb(110, 140, 251);

    public static Raylib.Color rgb(int r, int g, int b) {
        return new Raylib.Color().r((byte) r).g((byte) g).b((byte) b).a((byte) 255);
    }
}
