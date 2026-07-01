package dev.spruce.draftgui.state.impl;

import com.raylib.Raylib;
import dev.spruce.draftgui.Application;
import dev.spruce.draftgui.game.Draft;
import dev.spruce.draftgui.game.Player;
import dev.spruce.draftgui.game.PlayerDraft;
import dev.spruce.draftgui.game.Tower;
import dev.spruce.draftgui.state.State;
import dev.spruce.draftgui.ui.UIComponent;
import dev.spruce.draftgui.ui.UIManager;
import dev.spruce.draftgui.ui.impl.Button;
import dev.spruce.draftgui.ui.impl.DraftList;
import dev.spruce.draftgui.utils.DateUtils;
import dev.spruce.draftgui.utils.RenderUtils;

import javax.swing.*;

import java.awt.desktop.AppForegroundListener;
import java.util.List;

import static com.raylib.Colors.BLACK;

public class DraftState extends State {

    private UIManager uiManager;
    private final Draft draft;

    public DraftState(String map, List<String> playerNames) {
        List<Player> players = playerNames.stream()
                .map(name -> Application.getFileManager().getPlayerByName(name))
                .toList();
        this.draft = new Draft(DateUtils.getDate(), map, players);
    }

    @Override
    public void initialize() {
        this.uiManager = new UIManager();

        int height = this.draft.getPlayerDrafts().get(0).getTowers().size() * 24 + 6;
        int i = 0;
        for (PlayerDraft playerDraft : this.draft.getPlayerDrafts()) {
            final int width = 225;

            float startX = (Raylib.GetRenderWidth() / 2f) - draft.getNumPlayers() * (width + 2) / 2f;
            float x = startX + ((width + 2) * i);
            float y = 46;

            this.uiManager.addComponent(new DraftList(x, y, width, height, playerDraft));
            i++;
        }

        this.uiManager.addComponent(new Button("Save Draft", 6, 500, 250, 40, () -> {
            updatePlayerRounds();
            int highestRound = 0;
            int highestIndex = 0;
            for (PlayerDraft playerDraft : this.draft.getPlayerDrafts()) {
                int playerRound = playerDraft.getRound();
                if (playerRound > highestRound) {
                    highestRound = playerRound;
                    highestIndex = this.draft.getPlayerDrafts().indexOf(playerDraft);
                }
            }
            Player winner = Application.getFileManager().getPlayerByName(this.draft.getPlayerDrafts().get(highestIndex).getPlayer().getName());
            winner.incrementWins();
            Application.getFileManager().saveDraftFile(draft);
            Application.getStateManager().setState(new HomeState());
        }));

        this.uiManager.addComponent(new Button("Re-roll Towers", 6, 546, 250, 40, () -> {
            this.draft.regenerateDraft();
            for (UIComponent component : this.uiManager.getComponents()) {
                if (component instanceof DraftList draftList) {
                    draftList.setPlayerDraft(this.draft.getPlayerDrafts().get(this.uiManager.getComponents().indexOf(component)));
                }
             }
        }));
    }

    private void updatePlayerRounds() {
        for (UIComponent component : this.uiManager.getComponents()) {
            if (component instanceof DraftList draftList) {
                draftList.updateRound();
            }
        }
    }

    @Override
    public void update() {
        this.uiManager.update();
    }

    @Override
    public void render() {
        Raylib.DrawText("Map: " + draft.getMap(), 6, 6, 20, BLACK);
        Raylib.DrawText("Date: " + draft.getDate(), 6, 26, 20, BLACK);

        RenderUtils.DrawTextAShadow("Leftover Towers:", 6, 46, 20, BLACK);
        for (Tower tower : this.draft.getLeftOverTowers()) {
            RenderUtils.DrawTextAShadow(tower.getName(), 6,
                    66 + (this.draft.getLeftOverTowers().indexOf(tower) * 24), 20, tower.getType().getColor());
        }

        this.uiManager.render();
    }

    @Override
    public void dispose() {

    }
}
