package dev.spruce.draftgui.game;

import java.util.List;

public class PlayerDraft {

    private final Player player;
    private final List<Tower> towers;

    public PlayerDraft(Player player, List<Tower> towers) {
        this.player = player;
        this.towers = towers;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Tower> getTowers() {
        return towers;
    }
}
