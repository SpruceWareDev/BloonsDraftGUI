package dev.spruce.draftgui.utils;

public class RenderUtils {
    
    public static double larp(double start, double end, double t) {
        return start + (end - start) * t;
    }
}
