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
import dev.spruce.draftgui.utils.Timer;

import java.util.List;

import static com.raylib.Colors.WHITE;

public class DraftState extends State {

    private UIManager uiManager;
    private final Draft draft;

    private boolean rollingTowers;
    private Timer rollTimer;
    private static final long BASE_ROLL_TIME = 2;
    private long rollTime = BASE_ROLL_TIME;
    private int rollCounter = 0;

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
            Application.getFileManager().saveDraftFile(draft);
            Application.getStateManager().setState(new HomeState());
        }));

        this.uiManager.addComponent(new Button("Re-roll Towers", 6, 546, 250, 40, () -> {
            rollTime = BASE_ROLL_TIME;
            rollCounter = 30;
            rollTimer = new Timer(rollTime);
            this.rollingTowers = true;

            /*
            this.draft.regenerateDraft();
            for (UIComponent component : this.uiManager.getComponents()) {
                if (component instanceof DraftList draftList) {
                    draftList.setPlayerDraft(this.draft.getPlayerDrafts().get(this.uiManager.getComponents().indexOf(component)));
                }
             }

             */
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

        if (rollingTowers) {
            if (rollCounter > 0 && this.rollTimer.hasElapsed()) {
                this.draft.regenerateDraft();

                for (UIComponent component : this.uiManager.getComponents()) {
                    if (component instanceof DraftList draftList) {
                        draftList.setPlayerDraft(this.draft.getPlayerDrafts().get(this.uiManager.getComponents().indexOf(component)));
                    }
                }

                rollCounter--;
                rollTime += 3;
                this.rollTimer.setDuration(rollTime);
                this.rollTimer.reset();
            } else if (rollCounter <= 0) {
                rollingTowers = false;
            }
        }
    }

    @Override
    public void render() {
        RenderUtils.DrawTextAShadow("Map: " + draft.getMap(), 6, 6, 20, WHITE);
        RenderUtils.DrawTextAShadow("Date: " + draft.getDate(), 6, 26, 20, WHITE);

        RenderUtils.DrawTextAShadow("Leftover Towers:", 6, 46, 20, WHITE);
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
