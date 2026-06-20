package dev.spruce.draftgui.game;

import dev.spruce.draftgui.FileManager;
import dev.spruce.draftgui.files.ISaveable;

public class Player implements ISaveable {

    private String name;
    private int wins;

    public Player(String name) {
        this.name = name;
        this.wins = 0;
    }

    public void incrementWins() {
        this.wins++;
    }

    public String getName() {
        return name;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    @Override
    public String save() {
        return this.name + FileManager.PLAYER_DATA_SEPARATOR + this.wins;
    }

    @Override
    public void load(String data) {
        String[] elements = data.split(FileManager.PLAYER_DATA_SEPARATOR);
        if (elements.length < 2) {
            throw new RuntimeException("Failed to load player data: Not enough arguments given!");
        }
        this.name = elements[0];
        try {
            this.wins = Integer.parseInt(elements[1]);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getSaveName() {
        return name + FileManager.PLAYER_FILE_EXTENSION;
    }
}
