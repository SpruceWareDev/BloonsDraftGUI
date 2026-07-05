package dev.spruce.draftgui.ui.impl;

import com.raylib.Raylib;
import dev.spruce.draftgui.ui.UIComponent;
import dev.spruce.draftgui.utils.Colours;
import dev.spruce.draftgui.utils.Rectangle;
import dev.spruce.draftgui.utils.RenderUtils;

import static com.raylib.Colors.DARKGRAY;
import static com.raylib.Colors.WHITE;

public class Button extends UIComponent {

    private String text;
    private Runnable clickAction;

    private double radius = 0.1f;

    public Button(String text, float x, float y, float width, float height, Runnable clickAction) {
        super(x, y, width, height);
        this.text = text;
        this.clickAction = clickAction;
    }

    @Override
    public void render() {
        Raylib.DrawRectangleRounded(
                new Rectangle(getX(), getY(), getWidth(), getHeight()).toRaylibRectangle(), (float) radius, 4, Colours.DARKER);
        RenderUtils.DrawTextAShadow(text, (int) (getX() + getWidth() / 2 - Raylib.MeasureText(text, 20) / 2), (int) (getY() + getHeight() / 2 - 10), 20, WHITE);
    }

    @Override
    public void update() {
        Rectangle bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
        boolean mouseOver = bounds.contains(Raylib.GetMouseX(), Raylib.GetMouseY());

        this.radius = RenderUtils.larp(this.radius, mouseOver ? 0.4f : 0.1f, Raylib.GetFrameTime() * 10f);

        if (Raylib.IsMouseButtonPressed(Raylib.MOUSE_BUTTON_LEFT)) {
            float mouseX = Raylib.GetMouseX();
            float mouseY = Raylib.GetMouseY();
            if (mouseOver) {
                clickAction.run();
            }
        }
    }

    public String getText() {
        return text;
    }
}
