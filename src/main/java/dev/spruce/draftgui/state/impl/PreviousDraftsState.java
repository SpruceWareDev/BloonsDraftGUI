package dev.spruce.draftgui.state.impl;

import dev.spruce.draftgui.Application;
import dev.spruce.draftgui.game.Draft;
import dev.spruce.draftgui.state.State;
import dev.spruce.draftgui.ui.UIManager;
import dev.spruce.draftgui.ui.impl.Button;

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
