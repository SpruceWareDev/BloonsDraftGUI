package dev.spruce.draftgui.state;

public class StateManager {

    private State currentState;

    public void setState(State newState) {
        if (currentState != null) {
            currentState.dispose();
        }
        currentState = newState;
        if (currentState != null) {
            currentState.initialize();
        }
    }

    public void update() {
        if (currentState != null) {
            currentState.update();
        }
    }

    public void render() {
        if (currentState != null) {
            currentState.render();
        }
    }
}
