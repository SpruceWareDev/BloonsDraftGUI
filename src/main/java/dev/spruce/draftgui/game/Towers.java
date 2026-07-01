package dev.spruce.draftgui.game;

import java.util.ArrayList;
import java.util.List;

public class Towers {

    public static final List<Tower> TOWERS = new ArrayList<>();

    public static final Tower DARTLING_GUNNER = add(new Tower("Dartling Gunner", TowerType.MILITARY));
    public static final Tower GLUE = add(new Tower("Glue", TowerType.PRIMARY));
    public static final Tower DART = add(new Tower("Dart", TowerType.PRIMARY));
    public static final Tower BEAST_HANDLER = add(new Tower("Beasty Boy", TowerType.SUPPORT));
    public static final Tower MORTAR = add(new Tower("Mortar", TowerType.MILITARY));
    public static final Tower DRUID = add(new Tower("Druid", TowerType.MAGIC));
    public static final Tower TACK = add(new Tower("Tack", TowerType.PRIMARY));
    public static final Tower HELI = add(new Tower("Heli", TowerType.MILITARY));
    public static final Tower SPIKE = add(new Tower("Spike", TowerType.SUPPORT));
    public static final Tower NINJA = add(new Tower("Ninja", TowerType.MAGIC));
    public static final Tower BOMB = add(new Tower("Bomb", TowerType.PRIMARY));
    public static final Tower SNIPER = add(new Tower("Sniper", TowerType.MILITARY));
    public static final Tower DESPERADO = add(new Tower("Desperado", TowerType.PRIMARY));
    public static final Tower ACE = add(new Tower("Ace", TowerType.MILITARY));
    public static final Tower ALCH = add(new Tower("Alch", TowerType.MAGIC));
    public static final Tower ENGINEER = add(new Tower("Engineer", TowerType.SUPPORT));
    public static final Tower MERMONKEY = add(new Tower("Mermonkey", TowerType.SUPPORT));
    public static final Tower BOOMERANG = add(new Tower("Boomerang", TowerType.PRIMARY));
    public static final Tower SUPER = add(new Tower("Super", TowerType.MAGIC));
    public static final Tower ICE = add(new Tower("Ice", TowerType.PRIMARY));
    public static final Tower WIZARD = add(new Tower("Wizard", TowerType.MAGIC));

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
