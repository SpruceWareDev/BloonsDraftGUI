package dev.spruce.draftgui.game;

import java.util.ArrayList;
import java.util.List;

public class Towers {

    public static final List<Tower> TOWERS = new ArrayList<>();

    public static final Tower DARTLING_GUNNER = add(new Tower("Dartling Gunner"));
    public static final Tower GLUE = add(new Tower("Glue"));
    public static final Tower DART = add(new Tower("Dart"));
    public static final Tower BEAST_HANDLER = add(new Tower("Beasty Boy"));
    public static final Tower MORTAR = add(new Tower("Mortar"));
    public static final Tower DRUID = add(new Tower("Druid"));
    public static final Tower TACK = add(new Tower("Tack"));
    public static final Tower HELI = add(new Tower("Heli"));
    public static final Tower SPIKE = add(new Tower("Spike"));
    public static final Tower NINJA = add(new Tower("Ninja"));
    public static final Tower BOMB = add(new Tower("Bomb"));
    public static final Tower SNIPER = add(new Tower("Sniper"));
    public static final Tower DESPERADO = add(new Tower("Desperado"));
    public static final Tower ACE = add(new Tower("Ace"));
    public static final Tower ALCH = add(new Tower("Alch"));
    public static final Tower ENGINEER = add(new Tower("Engineer"));
    public static final Tower MERMONKEY = add(new Tower("Mermonkey"));
    public static final Tower BOOMERANG = add(new Tower("Boomerang"));
    public static final Tower SUPER = add(new Tower("Super"));
    public static final Tower ICE = add(new Tower("Ice"));
    public static final Tower WIZARD = add(new Tower("Wizard"));

    private static Tower add(Tower tower) {
        TOWERS.add(tower);
        return tower;
    }

    public static Tower getTowerByName(String name) {
        for (Tower tower : TOWERS) {
            if (tower.getName().equalsIgnoreCase(name)) {
                return tower;
            }
        }
        return null;
    }
}
