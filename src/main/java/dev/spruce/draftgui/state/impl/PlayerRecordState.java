package dev.spruce.draftgui.state.impl;

import com.raylib.Raylib;
import dev.spruce.draftgui.Application;
import dev.spruce.draftgui.game.Player;
import dev.spruce.draftgui.state.State;
import dev.spruce.draftgui.ui.UIManager;
import dev.spruce.draftgui.ui.impl.Button;
import dev.spruce.draftgui.utils.RenderUtils;
import dev.spruce.draftgui.utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.desktop.AppForegroundListener;

import static com.raylib.Colors.*;

public class PlayerRecordState extends State {

    private UIManager uiManager;

    @Override
    public void initialize() {
        Application.getFileManager().updatePlayerWins();
        this.uiManager = new UIManager();
        this.uiManager.addComponent(new Button("Back to Home", 6, 6, 200, 40,
                () -> Application.getStateManager().setState(new HomeState()))
        );
        this.uiManager.addComponent(new Button("Add Record", 6, 6 + UIUtils.BUTTON_HEIGHT + 2, 200, 40,
                () -> {
                    String playerName = JOptionPane.showInputDialog("Enter player name:");
                    if (playerName != null && !playerName.trim().isEmpty()) {
                        Application.getFileManager().getPlayers().add(new Player(playerName));
                    }

                })
        );

    }

    @Override
    public void update() {
        this.uiManager.update();
    }

    @Override
    public void render() {
        for (Player player : Application.getFileManager().getPlayers()) {
            renderPlayerRecord(player);
        }

        this.uiManager.render();
    }

    private void renderPlayerRecord(Player player) {
        int index = Application.getFileManager().getPlayers().indexOf(player);
        int yOffset = 6 + (UIUtils.BUTTON_HEIGHT + 2) * index + 6 + (UIUtils.BUTTON_HEIGHT * 2);
        int x = 6;
        int y = yOffset;

        Raylib.DrawRectangle(x, y, Raylib.GetScreenWidth() - 12, UIUtils.BUTTON_HEIGHT, GRAY);

        RenderUtils.DrawTextAShadow(player.getName(), 12, y, 20, 3,  WHITE);

        renderSection(x + 200, y, 100, UIUtils.BUTTON_HEIGHT, "Wins", String.valueOf(player.getWins()));
        renderSection(x + 306, y, 300, UIUtils.BUTTON_HEIGHT, "Win Percentage",
                String.valueOf(Application.getFileManager().getPlayerWinPercentage(player)));
    }

    private void renderSection(int x, int y, int width, int height, String title, String value) {
        Raylib.DrawRectangle(x, y, width, height, DARKGRAY);
        RenderUtils.DrawTextAShadow(title, x + 6, y + 6, 20, WHITE);
        RenderUtils.DrawTextAShadow(value, x + width - 6 - Raylib.MeasureText(value, 20), y + 6, 20, GREEN);
    }

    @Override
    public void dispose() {

    }
}
