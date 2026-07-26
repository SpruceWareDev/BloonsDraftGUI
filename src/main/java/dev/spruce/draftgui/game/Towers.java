package dev.spruce.draftgui.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Towers {

    public static final List<Tower> TOWERS = new ArrayList<>();

    public static final Tower DARTLING_GUNNER = add("Dartling Gunner", TowerType.MILITARY, new TowerRank(8, 0, 0, 2));
    public static final Tower GLUE = add("Glue", TowerType.PRIMARY, new TowerRank(3, 7, 0, 10));
    public static final Tower DART = add("Dart", TowerType.PRIMARY, new TowerRank(5, 0, 0, 10));
    public static final Tower BEAST_HANDLER = add("Beasty Boy", TowerType.SUPPORT, new TowerRank(9, 0, 0, 0));
    public static final Tower MORTAR = add("Mortar", TowerType.MILITARY, new TowerRank(5, 10, 0, 6));
    public static final Tower DRUID = add("Druid", TowerType.MAGIC, new TowerRank(8, 6, 3, 4));
    public static final Tower TACK = add("Tack", TowerType.PRIMARY, new TowerRank(7, 0, 0, 5));
    public static final Tower HELI = add("Heli", TowerType.MILITARY, new TowerRank(8, 7, 1, 3));
    public static final Tower SPIKE = add("Spike", TowerType.SUPPORT, new TowerRank(10, 1, 0, 0));
    public static final Tower NINJA = add("Ninja", TowerType.MAGIC, new TowerRank(10, 3, 0, 4));
    public static final Tower BOMB = add("Bomb", TowerType.PRIMARY, new TowerRank(9, 4, 0, 3));
    public static final Tower SNIPER = add("Sniper", TowerType.MILITARY, new TowerRank(5, 4, 10, 8));
    public static final Tower DESPERADO = add("Desperado", TowerType.PRIMARY, new TowerRank(6, 1, 1, 5));
    public static final Tower ACE = add("Ace", TowerType.MILITARY, new TowerRank(10, 0, 0, 1));
    public static final Tower ALCH = add("Alch", TowerType.MAGIC, new TowerRank(10, 8, 5, 3));
    public static final Tower ENGINEER = add("Engineer", TowerType.SUPPORT, new TowerRank(5, 2, 10, 4));
    public static final Tower MERMONKEY = add("Mermonkey", TowerType.SUPPORT, new TowerRank(5, 10, 0, 6));
    public static final Tower BOOMERANG = add("Boomerang", TowerType.PRIMARY, new TowerRank(8, 2, 0, 5));
    public static final Tower SUPER = add("Super", TowerType.MAGIC, new TowerRank(10, 1, 0, 0));
    public static final Tower ICE = add("Ice", TowerType.PRIMARY, new TowerRank(5, 8, 0, 9));
    public static final Tower WIZARD = add("Wizard", TowerType.MAGIC, new TowerRank(9, 1, 0, 5));

    private static Tower add(String name, TowerType type, TowerRank rank) {
        Tower tower = new Tower(name, type, rank, "assets/towers/" + name + ".png");
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

    public static HashMap<Tower, Integer> getTowerRanks() {
        HashMap<Tower, Integer> towerRankMap = new HashMap<>();
        for (Tower tower : TOWERS) {
            TowerRank towerRank = tower.getRank();
            int totalRank = towerRank.cost() + towerRank.damage() + towerRank.money() + towerRank.support();
            towerRankMap.put(tower, totalRank);
        }
        return towerRankMap;
    }

    public static List<Map.Entry<Tower, Integer>> getSortedTowerRanks() {
        HashMap<Tower, Integer> ranks = Towers.getTowerRanks();
        List<Map.Entry<Tower, Integer>> entryList = new ArrayList<>(ranks.entrySet());
        entryList.sort(Map.Entry.comparingByValue());
        return entryList;
    }
}
