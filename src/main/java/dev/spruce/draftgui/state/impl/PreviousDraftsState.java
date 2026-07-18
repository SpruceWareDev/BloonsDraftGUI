package dev.spruce.draftgui.state.impl;

import com.raylib.Raylib;
import dev.spruce.draftgui.Application;
import dev.spruce.draftgui.game.Draft;
import dev.spruce.draftgui.game.Player;
import dev.spruce.draftgui.game.PlayerDraft;
import dev.spruce.draftgui.state.State;
import dev.spruce.draftgui.ui.UIManager;
import dev.spruce.draftgui.ui.impl.Button;
import dev.spruce.draftgui.ui.impl.Table;

import java.util.List;

public class PreviousDraftsState extends State {

    private UIManager uiManager;
    private List<Draft> drafts;

    @Override
    public void initialize() {
        this.drafts = Application.getFileManager().loadDraftFiles();
        this.uiManager = new UIManager();
        this.uiManager.addComponent(new Button("Back to Home", 6, 6, 200, 40,
                () -> Application.getStateManager().setState(new HomeState()))
        );

        Table table = new Table(
                List.of("Map", "Date", "Winners"),
                6, 6 + 42, Raylib.GetRenderWidth() - 12, 30
        );

        for (Draft draft : this.drafts) {
            List<PlayerDraft> winners = Application.getFileManager().calculateWinners(draft);
            StringBuilder winnerNames = new StringBuilder();

            for (PlayerDraft winner : winners) {
                winnerNames.append(winner.getPlayer().getName()).append(", ");
            }

            if (!winnerNames.isEmpty()) {
                winnerNames.setLength(winnerNames.length() - 2);
            }

            table.addRow(List.of(
                    draft.getMap(),
                    draft.getDate(),
                    winnerNames.toString()
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
    public void dispose(){

    }
}
