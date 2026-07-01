package dev.spruce.draftgui.game;

public class Tower {

    private final String name;
    private final TowerType type;

    public Tower(String name, TowerType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

public TowerType getType() {
        return type;
    }
}
