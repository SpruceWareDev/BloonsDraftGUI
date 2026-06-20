package dev.spruce.draftgui.state.impl;

import com.raylib.Raylib;
import dev.spruce.draftgui.Application;
import dev.spruce.draftgui.game.Player;
import dev.spruce.draftgui.state.State;
import dev.spruce.draftgui.ui.UIManager;
import dev.spruce.draftgui.ui.impl.Button;
import dev.spruce.draftgui.utils.UIUtils;

import javax.swing.*;
import java.awt.*;

import static com.raylib.Colors.BLACK;
import static com.raylib.Colors.GRAY;

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
        Raylib.DrawRectangle(6, 6 + (UIUtils.BUTTON_HEIGHT + 2) * index + 6 + (UIUtils.BUTTON_HEIGHT * 2),
                Raylib.GetScreenWidth() - 12, UIUtils.BUTTON_HEIGHT, GRAY);

        Raylib.DrawText(player.getName(), 12,
                6 + (UIUtils.BUTTON_HEIGHT + 2) * index + 6 + (UIUtils.BUTTON_HEIGHT * 2) + 10, 20, BLACK);

        String winsText = "Wins: " + player.getWins();
        Raylib.DrawText(winsText, Raylib.GetScreenWidth() - Raylib.GuiGetTextWidth(winsText) - 52,
                6 + (UIUtils.BUTTON_HEIGHT + 2) * index + 6 + (UIUtils.BUTTON_HEIGHT * 2) + 10, 20, BLACK);
    }

    @Override
    public void dispose() {

    }
}
