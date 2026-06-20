package dev.spruce.draftgui.state.impl;

import com.raylib.Colors;
import com.raylib.Raylib;
import dev.spruce.draftgui.Application;
import dev.spruce.draftgui.game.Maps;
import dev.spruce.draftgui.game.Player;
import dev.spruce.draftgui.state.State;
import dev.spruce.draftgui.ui.UIManager;
import dev.spruce.draftgui.ui.impl.Button;
import dev.spruce.draftgui.ui.impl.MultiSelectBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DraftSetupState extends State {

    private UIManager uiManager;
    private MultiSelectBox difficultySelect;
    private MultiSelectBox playerSelect;

    @Override
    public void initialize() {
        this.uiManager = new UIManager();
        this.difficultySelect = new MultiSelectBox(6, 40, 200, 200,
                Arrays.asList("Beginner", "Intermediate", "Advanced", "Expert"));
        this.uiManager.addComponent(difficultySelect);

        List<String> playerNames = new ArrayList<>();
        for (Player player : Application.getFileManager().getPlayers()) {
            playerNames.add(player.getName());
        }
        this.playerSelect = new MultiSelectBox(220, 40, 200, 200, playerNames);
        this.uiManager.addComponent(playerSelect);


        this.uiManager.addComponent(new Button("Start Draft", 10, 250, 100, 30, () -> {
            boolean[] selectedDifficulties = difficultySelect.getSelectedOptions();
            List<String> maps = new ArrayList<>();
            if (selectedDifficulties[0]) maps.addAll(Maps.BEGINNER_MAPS);
            if (selectedDifficulties[1]) maps.addAll(Maps.INTERMEDIATE_MAPS);
            if (selectedDifficulties[2]) maps.addAll(Maps.ADVANCED_MAPS);
            if (selectedDifficulties[3]) maps.addAll(Maps.EXPERT_MAPS);

            //Pick a random map
            String selectedMap = maps.get((int) (Math.random() * maps.size()));

            List<String> selectedPlayers = new ArrayList<>();
            for (int i = 0; i < playerSelect.getSelectedOptions().length; i++) {
                if (playerSelect.getSelectedOptions()[i]) {
                    selectedPlayers.add(playerSelect.getOptions().get(i));
                }
            }

            Application.getStateManager().setState(new DraftState(selectedMap, selectedPlayers));
        }));
    }

    @Override
    public void update() {
        this.uiManager.update();
    }

    @Override
    public void render() {
        Raylib.DrawText("Draft Setup State", 10, 10, 20, Colors.BLACK);
        this.uiManager.render();
    }

    @Override
    public void dispose() {

    }
}
