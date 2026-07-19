package dev.spruce.draftgui.game;

import java.util.ArrayList;
import java.util.List;

public class PlayerLoadout {

    private final Player player;
    private final List<Tower> towers;

    public PlayerLoadout(Player player) {
        this.player = player;
        this.towers = new ArrayList<>();
    }

    public PlayerLoadout(Player player, List<Tower> towers) {
        this.player = player;
        this.towers = towers;
    }

    public void addTower(Tower tower) {
        this.towers.add(tower);
    }

    public Player getPlayer() {
        return player;
    }

    public List<Tower> getTowers() {
        return towers;
    }
}
