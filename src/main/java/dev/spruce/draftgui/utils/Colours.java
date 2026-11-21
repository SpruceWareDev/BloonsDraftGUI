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

    public static Raylib.Color rgba(int r, int g, int b, int a) {
        return new Raylib.Color().r((byte) r).g((byte) g).b((byte) b).a((byte) a);
    }

    /**
     * Darkens a given Color by a specified amount.
     *
     * @param color  The original color to darken.
     * @param amount The amount to darken the color, where 0 leaves the color unchanged
     *               and 1 makes it completely black. Values outside the range [0, 1]
     *               will be clamped.
     * @return A new Color that is darkened by the specified amount.
     */
    public static Raylib.Color darkenColor(Raylib.Color color, float amount) {
        // Clamp the amount to the range [0, 1]
        amount = Math.max(0, Math.min(1, amount));

        // Calculate the darkened RGB values
        int r = (int) (color.r() * (1 - amount));
        int g = (int) (color.g() * (1 - amount));
        int b = (int) (color.b() * (1 - amount));

        // Return the new darkened color with the same alpha value
        return new Raylib.Color().r((byte) r).g((byte) g).b((byte) b).a(color.a());
    }
}
