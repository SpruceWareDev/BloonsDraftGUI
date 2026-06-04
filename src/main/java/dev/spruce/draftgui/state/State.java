package dev.spruce.draftgui.state;

public abstract class State {
    public abstract void initialize();
    public abstract void update();
    public abstract void render();
    public abstract void dispose();
}
