package dev.spruce.draftgui.state.impl;

import com.raylib.Raylib;
import dev.spruce.draftgui.Application;
import dev.spruce.draftgui.game.Player;
import dev.spruce.draftgui.state.State;
import dev.spruce.draftgui.ui.UIManager;
import dev.spruce.draftgui.ui.impl.Button;
import dev.spruce.draftgui.ui.impl.Table;
import dev.spruce.draftgui.utils.RenderUtils;
import dev.spruce.draftgui.utils.Statistics;
import dev.spruce.draftgui.utils.UIUtils;

import javax.swing.*;

import java.util.List;

import static com.raylib.Colors.*;

public class PlayerRecordState extends State {

    private UIManager uiManager;

    @Override
    public void initialize() {
        Application.getFileManager().updatePlayerWins();
        Statistics.get().updateDraftFiles();

        this.uiManager = new UIManager();
        this.uiManager.addComponent(new Button("Back to Home", 6, 6, 200, 40,
                () -> Application.getStateManager().setState(new HomeState()))
        );

        this.uiManager.addComponent(new Button("Add Player", 6, 6 + UIUtils.BUTTON_HEIGHT + 2, 200, 40,
                () -> {
                    String playerName = JOptionPane.showInputDialog("Enter player name:");
                    if (playerName != null && !playerName.trim().isEmpty()) {
                        Application.getFileManager().getPlayers().add(new Player(playerName));
                    }
                })
        );

        Table table = new Table(
                List.of("Player Name", "Wins", "Win %", "Avg Round", "Max Round"),
                6, 6 + (UIUtils.BUTTON_HEIGHT + 2) * 2, Raylib.GetRenderWidth() - 12, 30
        );
        for (Player player : Application.getFileManager().getPlayers()) {
            table.addRow(List.of(
                player.getName(),
                String.valueOf(player.getWins()),
                String.format("%.2f", Statistics.get().getPlayerWinPercentage(player)),
                String.valueOf(Statistics.get().getAverageRound(player)),
                String.valueOf(Statistics.get().getPlayerHighestRound(player))
            ));
        }
        this.uiManager.addComponent(table);
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
