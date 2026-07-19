package dev.spruce.draftgui.game;

import java.util.ArrayList;
import java.util.List;

public class Towers {

    public static final List<Tower> TOWERS = new ArrayList<>();

    public static final Tower DARTLING_GUNNER = add("Dartling Gunner", TowerType.MILITARY);
    public static final Tower GLUE = add("Glue", TowerType.PRIMARY);
    public static final Tower DART = add("Dart", TowerType.PRIMARY);
    public static final Tower BEAST_HANDLER = add("Beasty Boy", TowerType.SUPPORT);
    public static final Tower MORTAR = add("Mortar", TowerType.MILITARY);
    public static final Tower DRUID = add("Druid", TowerType.MAGIC);
    public static final Tower TACK = add("Tack", TowerType.PRIMARY);
    public static final Tower HELI = add("Heli", TowerType.MILITARY);
    public static final Tower SPIKE = add("Spike", TowerType.SUPPORT);
    public static final Tower NINJA = add("Ninja", TowerType.MAGIC);
    public static final Tower BOMB = add("Bomb", TowerType.PRIMARY);
    public static final Tower SNIPER = add("Sniper", TowerType.MILITARY);
    public static final Tower DESPERADO = add("Desperado", TowerType.PRIMARY);
    public static final Tower ACE = add("Ace", TowerType.MILITARY);
    public static final Tower ALCH = add("Alch", TowerType.MAGIC);
    public static final Tower ENGINEER = add("Engineer", TowerType.SUPPORT);
    public static final Tower MERMONKEY = add("Mermonkey", TowerType.SUPPORT);
    public static final Tower BOOMERANG = add("Boomerang", TowerType.PRIMARY);
    public static final Tower SUPER = add("Super", TowerType.MAGIC);
    public static final Tower ICE = add("Ice", TowerType.PRIMARY);
    public static final Tower WIZARD = add("Wizard", TowerType.MAGIC);

    private static Tower add(String name, TowerType type) {
        Tower tower = new Tower(name, type, "assets/towers/" + name + ".png");
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
