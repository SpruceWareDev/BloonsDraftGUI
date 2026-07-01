package dev.spruce.draftgui.ui.impl;

import com.raylib.Colors;
import com.raylib.Raylib;
import dev.spruce.draftgui.ui.UIComponent;

import java.awt.*;

public class TextBox extends UIComponent {

    private String text;
    private boolean isFocused;

    public TextBox(float x, float y, float width, float height) {
        super(x, y, width, height);
        this.text = "";
    }

    @Override
    public void render() {
        Raylib.DrawRectangle((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), Colors.BLACK);
        if (isFocused) {
            Raylib.DrawRectangleLines((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), Colors.YELLOW);
        } else {
            Raylib.DrawRectangleLines((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), Colors.GRAY);
        }
        Raylib.DrawText(text, (int) getX() + 5, (int) getY() + 5, 20, Colors.RED);
    }

    @Override
    public void update() {
        if (Raylib.IsMouseButtonPressed(0)) {
            float mouseX = Raylib.GetMouseX();
            float mouseY = Raylib.GetMouseY();
            isFocused = mouseX >= getX() && mouseX <= getX() + getWidth() &&
                    mouseY >= getY() && mouseY <= getY() + getHeight();
        }

        if (isFocused) {
            char key = (char) Raylib.GetCharPressed();
            if (Character.isLetterOrDigit(key) || Character.isWhitespace(key)) {
                text += (char) key;
            } else if (Raylib.GetKeyPressed() == Raylib.KEY_BACKSPACE && !text.isEmpty()) {
                text = text.substring(0, text.length() - 1);
            }
        }
    }

    public String getText() {
        return text;
    }

    public int getInteger() {
        return Integer.parseInt(this.text);
    }
}
