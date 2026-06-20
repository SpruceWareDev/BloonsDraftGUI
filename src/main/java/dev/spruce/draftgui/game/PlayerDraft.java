package dev.spruce.draftgui.game;

import java.util.List;

public class PlayerDraft {

    private final Player player;
    private final List<Tower> towers;
    private int round;

    public PlayerDraft(Player player, List<Tower> towers, int round) {
        this.player = player;
        this.towers = towers;
        this.round = round;
    }


    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Tower> getTowers() {
        return towers;
    }
}
