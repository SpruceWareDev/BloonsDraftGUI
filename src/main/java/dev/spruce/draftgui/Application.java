package dev.spruce.draftgui;

import com.raylib.Raylib;
import dev.spruce.draftgui.files.FileManager;
import dev.spruce.draftgui.files.SeasonManager;
import dev.spruce.draftgui.state.StateManager;
import dev.spruce.draftgui.state.impl.HomeState;
import dev.spruce.draftgui.utils.Colours;
import dev.spruce.draftgui.utils.DateUtils;

public class Application {

    private static StateManager stateManager;
    private static FileManager fileManager;
    private static SeasonManager seasonManager;

    private void init() {
        Raylib.InitWindow(1200, 600, "DraftGUI");
        Raylib.SetTargetFPS(60);
        Raylib.SetWindowState(Raylib.FLAG_WINDOW_RESIZABLE);
        Raylib.SetExitKey(0);
        stateManager = new StateManager();
        fileManager = new FileManager();
        fileManager.init();
        seasonManager = new SeasonManager(fileManager.loadSeasons());
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

    public static SeasonManager getSeasonManager() {
        return seasonManager;
    }
}
