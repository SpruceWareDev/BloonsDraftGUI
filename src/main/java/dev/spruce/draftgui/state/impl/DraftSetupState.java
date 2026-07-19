package dev.spruce.draftgui.state.impl;

import com.raylib.Colors;
import com.raylib.Raylib;
import dev.spruce.draftgui.Application;
import dev.spruce.draftgui.game.Player;
import dev.spruce.draftgui.state.State;
import dev.spruce.draftgui.ui.UIManager;
import dev.spruce.draftgui.ui.impl.Button;
import dev.spruce.draftgui.ui.impl.MultiSelectBox;
import dev.spruce.draftgui.utils.RenderUtils;
import dev.spruce.draftgui.ui.UIConstants;

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
        this.difficultySelect = new MultiSelectBox(6, 40, 200, 224, "Difficulty Select",
                Arrays.asList("Beginner", "Intermediate", "Advanced", "Expert"));
        this.uiManager.addComponent(difficultySelect);

        List<String> playerNames = new ArrayList<>();
        for (Player player : Application.getFileManager().getPlayers()) {
            playerNames.add(player.getName());
        }
        this.playerSelect = new MultiSelectBox(220, 40, 200, 424, "Player Select", playerNames);
        this.uiManager.addComponent(playerSelect);


        this.uiManager.addComponent(new Button("Start Draft", Raylib.GetRenderWidth() - 166, Raylib.GetRenderHeight() - UIConstants.BUTTON_HEIGHT
                 - 6, 160, UIConstants.BUTTON_HEIGHT, () -> {
            boolean[] selectedDifficulties = difficultySelect.getSelectedOptions();

            List<String> selectedPlayers = getSelectedPlayers();

            Application.getStateManager().setState(new DraftState(selectedDifficulties, selectedPlayers));
        }));

        this.uiManager.addComponent(new Button("Custom Start", 6, Raylib.GetRenderHeight() - UIConstants.BUTTON_HEIGHT - 6, 160, UIConstants.BUTTON_HEIGHT, () -> {
            Application.getStateManager().setState(new TowerSelectState(
                    Application.getFileManager().getPlayers().stream().filter(player -> getSelectedPlayers().contains(player.getName())).toList(),
                    difficultySelect.getSelectedOptions()
            ));
        }));
    }

    @Override
    public void update() {
        this.uiManager.update();
    }

    @Override
    public void render() {
        RenderUtils.DrawTextAShadow("Draft Setup State", 10, 10, 20, Colors.WHITE);
        this.uiManager.render();
    }

    @Override
    public void dispose() {

    }

    private List<String> getSelectedPlayers() {
        List<String> selectedPlayers = new ArrayList<>();
        for (int i = 0; i < playerSelect.getOptions().size(); i++) {
            if (playerSelect.getSelectedOptions()[i]) {
                selectedPlayers.add(playerSelect.getOptions().get(i));
            }
        }
        return selectedPlayers;
    }
}
