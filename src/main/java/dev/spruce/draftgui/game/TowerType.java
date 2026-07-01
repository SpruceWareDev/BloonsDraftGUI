package dev.spruce.draftgui.game;

import com.raylib.Colors;
import com.raylib.Raylib;

public enum TowerType {
    PRIMARY("Primary", Colors.SKYBLUE),
    MILITARY("Military", Colors.LIME),
    MAGIC("Magic", Colors.PURPLE),
    SUPPORT("Support", Colors.ORANGE);

    private final String displayName;
    private final Raylib.Color color;

    TowerType(String displayName, Raylib.Color color) {
        this.displayName = displayName;
        this.color = color;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Raylib.Color getColor() {
        return color;
    }
}
