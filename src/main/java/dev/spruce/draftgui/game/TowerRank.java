package dev.spruce.draftgui.game;

public class TowerRank {

    private final int damage;
    private final int support;
    private final int money;
    private final int cost;

    public TowerRank(int damage, int support, int money, int cost) {
        this.damage = damage;
        this.support = support;
        this.money = money;
        this.cost = cost;
    }

    public int getDamage() {
        return damage;
    }

    public int getSupport() {
        return support;
    }

    public int getMoney() {
        return money;
    }

    public int getCost() {
        return cost;
    }
}
