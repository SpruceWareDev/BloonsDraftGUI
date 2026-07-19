package dev.spruce.draftgui.state.impl;

import com.raylib.Colors;
import com.raylib.Raylib;
import dev.spruce.draftgui.Application;
import dev.spruce.draftgui.game.*;
import dev.spruce.draftgui.state.State;
import dev.spruce.draftgui.ui.UIManager;
import dev.spruce.draftgui.ui.impl.TowerCard;
import dev.spruce.draftgui.utils.DateUtils;
import dev.spruce.draftgui.utils.RenderUtils;

import java.util.ArrayList;
import java.util.List;

public class TowerSelectState extends State {

    private UIManager uiManager;
    private final boolean[] mapDifficulties;

    private final List<PlayerLoadout> playerLoadouts;
    private PlayerLoadout currentPlayerLoadout;
    private int currentIndex;
    private List<Tower> availableTowers;
    private List<Tower> currentTowerSelection;
    private boolean swapTowerCards = false;

    private boolean draftComplete = false;

    public TowerSelectState(List<Player> players, boolean[] mapDifficulties) {
        this.playerLoadouts = players.stream().map(PlayerLoadout::new).toList();
        this.mapDifficulties = mapDifficulties;
    }

    @Override
    public void initialize() {
        this.uiManager = new UIManager();
        // Set to -1 so that the first call to nextPlayer() sets it to 0
        this.currentIndex = -1;
        this.availableTowers = Towers.TOWERS;
        nextPlayer();
    }

    @Override
    public void update() {
        this.uiManager.update();

        if (this.draftComplete) {
            Application.getStateManager().setState(new DraftState(this.mapDifficulties, this.playerLoadouts, false));

        }

        if (this.swapTowerCards) {
            this.uiManager.getComponentsByClass(TowerCard.class).forEach(uiComponent -> ((TowerCard) uiComponent).unloadTowerTexture());
            this.uiManager.getComponents().clear();
            for (int i = 0; i < this.currentTowerSelection.size(); i++) {
                Tower tower = this.currentTowerSelection.get(i);
                float x = (Raylib.GetRenderWidth() / 2f) - (this.currentTowerSelection.size() * 250f / 2f) + (i * 250f);
                float y = Raylib.GetRenderHeight() / 2f - 150f;
                TowerCard towerCard = new TowerCard(tower, x, y, 200, 300, () -> selectTower(tower));
                this.uiManager.addComponent(towerCard);
            }
            this.swapTowerCards = false;
        }
    }

    @Override
    public void render() {
        String text = draftComplete ? "Draft Complete!" : this.currentPlayerLoadout.getPlayer().getName() + "'s Turn";
        RenderUtils.DrawTextAShadow(
                text, 
                (int) (Raylib.GetRenderWidth() / 2f) - (Raylib.MeasureText(text, 40) / 2),
                50,
                40,
                3,
                Colors.WHITE);
        this.uiManager.render();
    }

    @Override
    public void dispose() {

    }

    private void nextPlayer() {
        this.currentIndex++;
        if (availableTowers.size() < playerLoadouts.size() && this.currentIndex >= this.playerLoadouts.size()) {
            this.draftComplete = true;
            this.currentIndex = 0;
        } else if (this.currentIndex >= this.playerLoadouts.size()) {
            this.currentIndex = 0;
        }

        this.currentPlayerLoadout = this.playerLoadouts.get(this.currentIndex);
        this.currentTowerSelection = generateTowerSelection();
        this.swapTowerCards = true;
    }

    private void selectTower(Tower tower) {
        if (this.currentPlayerLoadout != null && this.currentTowerSelection.contains(tower)) {
            this.currentPlayerLoadout.addTower(tower);
            this.availableTowers.remove(tower);
            nextPlayer();
        }
    }

    private List<Tower> generateTowerSelection() {
        List<Tower> towers = new ArrayList<>();
        List<Tower> tempAvailableTowers = new ArrayList<>(availableTowers);
        int cardCount = Math.min(3, tempAvailableTowers.size());
        for (int i = 0; i < cardCount; i++) {
            Tower tower = tempAvailableTowers.get((int) (Math.random() * tempAvailableTowers.size()));
            towers.add(tower);
            tempAvailableTowers.remove(tower);
        }
        return towers;
    }
}
