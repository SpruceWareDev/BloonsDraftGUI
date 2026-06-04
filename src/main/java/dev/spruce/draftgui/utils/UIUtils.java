package dev.spruce.draftgui.utils;

import com.raylib.Raylib;

public class UIUtils {

    public static final int BUTTON_WIDTH = 200;
    public static final int BUTTON_HEIGHT = 50;

    public static int button(int buttonIndex, String text) {
        return Raylib.GuiButton(new Raylib.Rectangle()
                .x(Raylib.GetScreenWidth() / 2f - BUTTON_WIDTH / 2f)
                .y(Raylib.GetScreenHeight() / 6f + (BUTTON_HEIGHT + 6f) * buttonIndex)
                .width(BUTTON_WIDTH).height(BUTTON_HEIGHT), text);
    }

    public static int button(float x, float y, String text) {
        return Raylib.GuiButton(new Raylib.Rectangle().x(x).y(y).width(BUTTON_WIDTH).height(BUTTON_HEIGHT), text);
    }
}
