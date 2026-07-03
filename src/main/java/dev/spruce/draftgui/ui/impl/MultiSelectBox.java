package dev.spruce.draftgui.ui.impl;

import com.raylib.Colors;
import com.raylib.Raylib;
import dev.spruce.draftgui.ui.UIComponent;
import dev.spruce.draftgui.utils.Colours;
import dev.spruce.draftgui.utils.Rectangle;

import java.util.List;

public class MultiSelectBox extends UIComponent {

    private final String title;
    private final List<String> options;
    private boolean[] selectedOptions;

    public MultiSelectBox(float x, float y, float width, float height, String title, List<String> options) {
        super(x, y, width, height);
        this.title = title;
        this.options = options;
        this.selectedOptions = new boolean[options.size()];
    }

    @Override
    public void render() {
        Rectangle rect = new Rectangle(getX(), getY(), getWidth(), getHeight());
        Raylib.DrawRectangleRounded(rect.toRaylibRectangle(), 0.1f, 10, Colours.MEDIUM);
        Raylib.DrawText(title, (int) getX() + 10, (int) getY() + 10, 20, Colors.WHITE);

        int optionHeight = (int) ((getHeight() - 36) / options.size());
        for (int i = 0; i < options.size(); i++) {
            String option = options.get(i);
            float optionY = getY() + i * optionHeight + 36;

            Rectangle optionRect = new Rectangle(getX() + 6, optionY + 6, getWidth() - 12, optionHeight - 12);
            Raylib.DrawRectangleRounded(optionRect.toRaylibRectangle(), 0.4f, 10, Colours.DARK);

            optionRect.setWidth(16);
            optionRect.setHeight(16);
            optionRect.setY(optionY + (optionHeight - optionRect.getHeight()) / 2);
            optionRect.setX(getX() + getWidth() - optionRect.getWidth() * 2);
            Raylib.DrawRectangleRounded(optionRect.toRaylibRectangle(), 1f, 10, selectedOptions[i] ? Colors.GREEN : Colors.DARKGRAY);

            Raylib.DrawText(option, (int) getX() + 10, (int) optionY + 14, 20, Colors.WHITE);
        }
    }

    @Override
    public void update() {
        int optionHeight = (int) ((getHeight() - 36) / options.size());
        for (int i = 0; i < options.size(); i++) {
            float optionY = getY() + i * optionHeight + 36;
            Rectangle optionRect = new Rectangle(getX(), optionY, getWidth(), getHeight() / options.size());

            if (Raylib.IsMouseButtonPressed(0) && optionRect.contains(Raylib.GetMousePosition().x(), Raylib.GetMousePosition().y())) {
                selectedOptions[i] = !selectedOptions[i];
            }
        }
    }

    public boolean[] getSelectedOptions() {
        return selectedOptions;
    }

    public List<String> getOptions() {
        return options;
    }
}
