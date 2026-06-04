package dev.spruce.draftgui.state.impl;

import dev.spruce.draftgui.Application;
import dev.spruce.draftgui.game.Draft;
import dev.spruce.draftgui.state.State;

import java.util.List;

public class PreviousDraftsState extends State {

    private List<Draft> drafts;

    @Override
    public void initialize() {
        this.drafts = Application.getFileManager().loadDraftFiles();
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {

    }

    @Override
    public void dispose() {

    }
}
