package dev.spruce.draftgui.state.impl;

import com.raylib.Raylib;
import dev.spruce.draftgui.Application;
import dev.spruce.draftgui.state.State;
import dev.spruce.draftgui.ui.UIComponent;
import dev.spruce.draftgui.ui.UIManager;
import dev.spruce.draftgui.ui.impl.Button;
import dev.spruce.draftgui.utils.UIUtils;

public class HomeState extends State {

    private UIManager uiManager;

    @Override
    public void initialize() {
        this.uiManager = new UIManager();

        this.uiManager.addComponent(new Button("Start Draft",
                Raylib.GetRenderWidth() / 2f - UIUtils.BUTTON_WIDTH / 2f, 40f, UIUtils.BUTTON_WIDTH, UIUtils.BUTTON_HEIGHT, () -> {
            Application.getStateManager().setState(new DraftSetupState());
        }));
        this.uiManager.addComponent(new Button("Players",
                Raylib.GetRenderWidth() / 2f - UIUtils.BUTTON_WIDTH / 2f, 100f, UIUtils.BUTTON_WIDTH, UIUtils.BUTTON_HEIGHT, () -> {
            Application.getStateManager().setState(new PlayerRecordState());
        }));
        this.uiManager.addComponent(new Button("Previous Drafts",
                Raylib.GetRenderWidth() / 2f - UIUtils.BUTTON_WIDTH / 2f, 160f, UIUtils.BUTTON_WIDTH, UIUtils.BUTTON_HEIGHT, () -> {
            Application.getStateManager().setState(new PreviousDraftsState());
        }));
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
