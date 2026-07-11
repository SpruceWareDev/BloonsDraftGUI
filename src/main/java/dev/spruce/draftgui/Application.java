package dev.spruce.draftgui;

import com.raylib.Raylib;
import dev.spruce.draftgui.state.StateManager;
import dev.spruce.draftgui.state.impl.HomeState;
import dev.spruce.draftgui.utils.Colours;

import static com.raylib.Colors.RAYWHITE;

public class Application {

    private static StateManager stateManager;
    private static FileManager fileManager;

    private void init() {
        Raylib.InitWindow(1200, 600, "DraftGUI");
        Raylib.SetTargetFPS(60);
        Raylib.SetWindowState(Raylib.FLAG_WINDOW_RESIZABLE);
        stateManager = new StateManager();
        fileManager = new FileManager();
        fileManager.init();
        stateManager.setState(new HomeState());
    }

    public void run() {
        init();
        while (!Raylib.WindowShouldClose()) {
            stateManager.update();
            Raylib.BeginDrawing();
            Raylib.ClearBackground(Colours.DARK);
            stateManager.render();
            Raylib.EndDrawing();
        }
        fileManager.saveAll();
        Raylib.CloseWindow();
    }

    public static StateManager getStateManager() {
        return stateManager;
    }

    public static FileManager getFileManager() {
        return fileManager;
    }
}
