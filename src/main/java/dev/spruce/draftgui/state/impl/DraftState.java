package dev.spruce.draftgui.state.impl;

import com.raylib.Raylib;
import dev.spruce.draftgui.Application;
import dev.spruce.draftgui.game.Draft;
import dev.spruce.draftgui.game.Player;
import dev.spruce.draftgui.game.PlayerDraft;
import dev.spruce.draftgui.state.State;
import dev.spruce.draftgui.ui.UIComponent;
import dev.spruce.draftgui.ui.UIManager;
import dev.spruce.draftgui.ui.impl.Button;
import dev.spruce.draftgui.ui.impl.DraftList;
import dev.spruce.draftgui.utils.DateUtils;

import javax.swing.*;

import java.awt.desktop.AppForegroundListener;
import java.util.List;

import static com.raylib.Colors.BLACK;

public class DraftState extends State {

    private UIManager uiManager;
    private Draft draft;

    @Override
    public void initialize() {
        this.draft = new Draft(DateUtils.getDate(), "Cracked", Application.getFileManager().getPlayers());
        this.uiManager = new UIManager();

        int i = 0;
        for (PlayerDraft playerDraft : this.draft.getPlayerDrafts()) {
            final int width = 225;
            this.uiManager.addComponent(new DraftList(6 + ((width + 2) * i), 46, width, 400, playerDraft));
            i++;
        }

        this.uiManager.addComponent(new Button("Save Draft", 6, 500, 250, 40, () -> {
            int highestRound = 0;
            int highestIndex = 0;
            for (PlayerDraft playerDraft : this.draft.getPlayerDrafts()) {
                String playerRoundStr = JOptionPane.showInputDialog(playerDraft.getPlayer().getName() + "'s round: ");
                if (playerRoundStr != null && !playerRoundStr.trim().isEmpty()) {
                    try {
                        int playerRound = Integer.parseInt(playerRoundStr);
                        if (playerRound > highestRound) {
                            highestRound = playerRound;
                            highestIndex = this.draft.getPlayerDrafts().indexOf(playerDraft);
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid round number for " + playerDraft.getPlayer().getName());
                    }
                }
            }
            Player winner = Application.getFileManager().getPlayerByName(this.draft.getPlayerDrafts().get(highestIndex).getPlayer().getName());
            winner.incrementWins();
            Application.getFileManager().saveDraftFile(draft);
            Application.getStateManager().setState(new HomeState());
        }));

        this.uiManager.addComponent(new Button("Re-roll Towers", 6, 560, 250, 40, () -> {
            this.draft.regenerateDraft();
            for (UIComponent component : this.uiManager.getComponents()) {
                if (component instanceof DraftList) {
                    ((DraftList) component).setPlayerDraft(this.draft.getPlayerDrafts().get(this.uiManager.getComponents().indexOf(component)));
                }
             }
        }));
    }

    @Override
    public void update() {
        this.uiManager.update();
    }

    @Override
    public void render() {
        Raylib.DrawText("Map: " + draft.getMap(), 6, 6, 20, BLACK);
        Raylib.DrawText("Date: " + draft.getDate(), 6, 26, 20, BLACK);
        this.uiManager.render();
    }

    @Override
    public void dispose() {

    }
}
