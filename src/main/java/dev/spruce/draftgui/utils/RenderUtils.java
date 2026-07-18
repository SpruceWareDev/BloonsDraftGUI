package dev.spruce.draftgui.utils;

import com.raylib.Colors;
import com.raylib.Raylib;

public class RenderUtils {
    
    public static double larp(double start, double end, double t) {
        return start + (end - start) * t;
    }

    public static void DrawTextAShadow(String text, int posX, int posY, int fontSize, Raylib.Color tint) {
        Raylib.DrawText(text, posX + 1, posY + 1, fontSize, Colors.BLACK);
        Raylib.DrawText(text, posX, posY, fontSize, tint);
    }

    public static void DrawTextAShadow(String text, int posX, int posY, int fontSize, int iterations, Raylib.Color tint) {
        for (int i = 0; i < iterations; i++) {
            Raylib.DrawText(text, posX + (i + 1), posY + (i + 1), fontSize, Colors.BLACK);
        }
        Raylib.DrawText(text, posX, posY, fontSize, tint);
    }
}
