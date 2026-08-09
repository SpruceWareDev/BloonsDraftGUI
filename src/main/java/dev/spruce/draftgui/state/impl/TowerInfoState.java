package dev.spruce.draftgui.state.impl;

import com.raylib.Raylib;
import dev.spruce.draftgui.game.tower.Tower;
import dev.spruce.draftgui.game.tower.Towers;
import dev.spruce.draftgui.state.State;
import dev.spruce.draftgui.ui.UIManager;
import dev.spruce.draftgui.ui.impl.Table;

import java.util.List;
import java.util.Map;

public class TowerInfoState extends State {

    private UIManager uiManager;

    @Override
    public void initialize() {
        List<Map.Entry<Tower, Integer>> sortedTowerRanks = Towers.getSortedTowerRanks();
        this.uiManager = new UIManager();
        Table towerTable = new Table(
                List.of("Tower Name", "Rank", "Damage", "Support", "Money", "Cost"),
                6, 30, Raylib.GetRenderWidth() - 12, 30
        );
        for (Map.Entry<Tower, Integer> entry : sortedTowerRanks) {
            System.out.println(entry.getKey().getName() + ": " + entry.getValue());
            Tower tower = entry.getKey();
            int totalRank = entry.getValue();
            towerTable.addRow(new Table.Row(
                    List.of(
                            tower.getName(),
                            String.valueOf(totalRank),
                            String.valueOf(tower.getRank().damage()),
                            String.valueOf(tower.getRank().support()),
                            String.valueOf(tower.getRank().money()),
                            String.valueOf(tower.getRank().cost())
                    )
            ));
        }
        this.uiManager.addComponent(towerTable);
    }

    @Override
    public void update() {
        this.uiManager.update();
    }

    @Override
    public void render() {
        this.uiManager.render();
    }

    @Override
    public void dispose() {

    }
}
