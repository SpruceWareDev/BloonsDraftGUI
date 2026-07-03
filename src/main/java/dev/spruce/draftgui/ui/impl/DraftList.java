package dev.spruce.draftgui.ui.impl;

import com.raylib.Raylib;
import dev.spruce.draftgui.FontManager;
import dev.spruce.draftgui.game.Player;
import dev.spruce.draftgui.game.PlayerDraft;
import dev.spruce.draftgui.game.Tower;
import dev.spruce.draftgui.game.Towers;
import dev.spruce.draftgui.ui.UIComponent;
import dev.spruce.draftgui.utils.Colours;
import dev.spruce.draftgui.utils.Rectangle;
import dev.spruce.draftgui.utils.RenderUtils;

import static com.raylib.Colors.*;

public class DraftList extends UIComponent {

    private PlayerDraft playerDraft;
    private final TextBox roundTextBox;

    public DraftList(float x, float y, float width, float height, PlayerDraft playerDraft) {
        super(x, y, width, height);
        this.playerDraft = playerDraft;
        this.roundTextBox = new TextBox(x + width - 60, y + height - 35, 50, 25);
    }

    @Override
    public void render() {
        Rectangle componentBounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
        // Draw outer rectangle
        Raylib.DrawRectangleRounded(componentBounds.toRaylibRectangle(), 0.1f, 10, Colours.LIGHT);
        // Draw inner rectangle
        Raylib.DrawRectangleRounded(Rectangle.fromXYWH(getX() + 2, getY() + 2, getWidth() - 4, getHeight() - 4).toRaylibRectangle(), 0.1f, 10, Colours.MEDIUM);

        final Player player = this.playerDraft.getPlayer();

        RenderUtils.DrawTextAShadow(player.getName(), (int) (getX() + 6), (int) (getY() + 6), 20, 2, RED);

        int i = 0;
        for (Tower tower : this.playerDraft.getTowers()) {
            Raylib.Color towerColor = tower.getType().getColor();
            RenderUtils.DrawTextAShadow(tower.getName(), (int) (getX() + 6), (int) (getY() + 26 + (i * 20)), 20, towerColor);
            i++;
        }

        this.roundTextBox.render();
    }

    @Override
    public void update() {
        this.roundTextBox.update();
    }

    public void setPlayerDraft(PlayerDraft playerDraft) {
        this.playerDraft = playerDraft;
    }

    public void updateRound() {
        this.playerDraft.setRound(this.roundTextBox.getInteger());
    }
}
