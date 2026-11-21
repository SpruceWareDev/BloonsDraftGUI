package dev.spruce.draftgui.state.impl;

import com.raylib.Colors;
import com.raylib.Raylib;
import dev.spruce.draftgui.Application;
import dev.spruce.draftgui.files.Season;
import dev.spruce.draftgui.game.draft.Player;
import dev.spruce.draftgui.state.State;
import dev.spruce.draftgui.ui.UIManager;
import dev.spruce.draftgui.ui.impl.Button;
import dev.spruce.draftgui.ui.impl.Table;
import dev.spruce.draftgui.utils.DateUtils;

import javax.swing.*;
import java.util.List;

public class SeasonsState extends State {

    private UIManager uiManager;

    @Override
    public void initialize() {
        this.uiManager = new UIManager();

        this.uiManager.addComponent(new Button("Back to Home", 6, 6, 200, 40,
                () -> Application.getStateManager().setState(new HomeState()))
        );

        this.uiManager.addComponent(new Button("Add Season", 212, 6, 200, 40, () -> {
            String seasonName = JOptionPane.showInputDialog("Enter season name:");
            if (seasonName != null && !seasonName.trim().isEmpty()) {
                Application.getSeasonManager().createNewSeason(seasonName);
            }
        }));

        Table table = new Table(
                List.of("Season", "Start Date", "End Date"),
                6, 50, Raylib.GetRenderWidth() - 12, 30
        );

        for (Season season : Application.getSeasonManager().getSeasons()) {
            table.addRow(new Table.Row(
                    List.of(season.getName(), season.getStartDate(), season.getEndDate()),
                    Colors.WHITE
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
