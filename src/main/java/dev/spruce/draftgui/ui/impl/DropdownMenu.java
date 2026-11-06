package dev.spruce.draftgui.ui.impl;

import com.raylib.Colors;
import com.raylib.Raylib;
import dev.spruce.draftgui.ui.UIComponent;
import dev.spruce.draftgui.ui.UIConstants;
import dev.spruce.draftgui.utils.Colours;
import dev.spruce.draftgui.utils.Rectangle;
import dev.spruce.draftgui.utils.RenderUtils;

import java.util.Arrays;
import java.util.List;

public class DropdownMenu extends UIComponent {

    private String selectedOption;
    private List<String> options;
    private boolean open;

    public DropdownMenu(float x, float y, float width, float height, String... options) {
        super(x, y, width, height);
        this.options = Arrays.stream(options).toList();
        this.selectedOption = this.options.get(0);
    }

    @Override
    public void render() {
        Raylib.DrawRectangle((int) x, (int) y, (int) width, (int) height, Colours.DARKER);
        RenderUtils.DrawTextAShadow(this.selectedOption, (int) (x + 6), (int) (y + 6), 20, Colors.WHITE);

        if (open) {
            int i = 0;
            for (String option : this.options) {
                int yPos = (int) (y + ((i + 1) * UIConstants.BUTTON_HEIGHT));
                boolean isHovered = Raylib.CheckCollisionPointRec(Raylib.GetMousePosition(),
                        new Rectangle(x, yPos, width, UIConstants.BUTTON_HEIGHT).toRaylibRectangle());

                Raylib.DrawRectangle((int) x, yPos,
                        UIConstants.BUTTON_WIDTH, UIConstants.BUTTON_HEIGHT, isHovered ? Colours.DARKEST : Colours.DARKER);
                RenderUtils.DrawTextAShadow(option, (int) x + 6, yPos + 6, 20, Colors.WHITE);
                i++;
            }
        }
    }

    @Override
    public void update() {
        Rectangle closedRectangle = new Rectangle(x, y, width, UIConstants.BUTTON_HEIGHT);
        // Check if the mouse is clicked within the closed rectangle bounds to toggle the dropdown
        if (Raylib.IsMouseButtonPressed(0) && closedRectangle.contains(Raylib.GetMouseX(), Raylib.GetMouseY())) {
            this.open = !this.open;
        }

        if (open) {
            int i = 0;
            for (String option : this.options) {
                int yPos = (int) (y + ((i + 1) * UIConstants.BUTTON_HEIGHT));
                Rectangle optionRectangle = new Rectangle(x, yPos, width, UIConstants.BUTTON_HEIGHT);
                // Check if the mouse is clicked within the option rectangle bounds to select the option
                if (Raylib.IsMouseButtonPressed(0) && optionRectangle.contains(Raylib.GetMouseX(), Raylib.GetMouseY())) {
                    this.selectedOption = option;
                    this.open = false; // Close the dropdown after selection
                }
                i++;
            }
        }
    }
}
