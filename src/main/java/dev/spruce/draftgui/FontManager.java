package dev.spruce.draftgui;

import com.raylib.Raylib;

public class FontManager {

    private static FontManager instance;

    private Raylib.Font mainFont;

    private FontManager() {
        this.mainFont = Raylib.LoadFont("data/Comfortaa-Bold.ttf");
    }

    public Raylib.Font getMainFont() {
        return mainFont;
    }

    public static FontManager getInstance() {
        if (instance == null) {
            instance = new FontManager();
        }
        return instance;
    }
}
