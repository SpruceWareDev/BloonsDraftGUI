package dev.spruce.draftgui.game;

import com.raylib.Raylib;

public class Tower {

    private final String name;
    private final TowerType type;
    private final String texturePath;
    private Raylib.Texture texture;

    public Tower(String name, TowerType type, String texturePath) {
        this.name = name;
        this.type = type;
        this.texturePath = texturePath;
    }

    public String getName() {
        return name;
    }

    public TowerType getType() {
        return type;
    }

    public Raylib.Texture getTexture() {
        if (texture == null) {
            texture = Raylib.LoadTexture(texturePath);
        }
        return texture;
    }

    public void unloadTexture() {
        if (texture != null) {
            Raylib.UnloadTexture(texture);
            texture = null;
        }
    }
}
