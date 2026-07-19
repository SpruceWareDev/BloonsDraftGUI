package dev.spruce.draftgui.game;

import java.util.List;

public class PlayerDraft extends PlayerLoadout {

    private int round;

    public PlayerDraft(Player player, List<Tower> towers, int round) {
        super(player, towers);
        this.round = round;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }
}
