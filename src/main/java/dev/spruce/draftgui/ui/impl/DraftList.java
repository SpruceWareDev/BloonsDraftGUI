package dev.spruce.draftgui.ui.impl;

import com.raylib.Raylib;
import dev.spruce.draftgui.game.Player;
import dev.spruce.draftgui.game.PlayerDraft;
import dev.spruce.draftgui.game.Tower;
import dev.spruce.draftgui.game.Towers;
import dev.spruce.draftgui.ui.UIComponent;
import dev.spruce.draftgui.utils.Rectangle;

import static com.raylib.Colors.*;

public class DraftList extends UIComponent {

    private PlayerDraft playerDraft;

    public DraftList(float x, float y, float width, float height, PlayerDraft playerDraft) {
        super(x, y, width, height);
        this.playerDraft = playerDraft;
    }

    @Override
    public void render() {
        Rectangle componentBounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
        Raylib.DrawRectangleRounded(componentBounds.toRaylibRectangle(), 0.1f, 10, GRAY);

        final Player player = this.playerDraft.getPlayer();

        Raylib.DrawText(player.getName(), (int) (getX() + 6), (int) (getY() + 6), 20, RED);

        int i = 0;
        for (Tower tower : this.playerDraft.getTowers()) {
            Raylib.DrawText(tower.getName(), (int) (getX() + 6), (int) (getY() + 26 + (i * 20)), 20, BLACK);
            i++;
        }
    }

    @Override
    public void update() {

    }

    public void setPlayerDraft(PlayerDraft playerDraft) {
        this.playerDraft = playerDraft;
    }
}
