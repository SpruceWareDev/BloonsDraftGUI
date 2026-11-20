package dev.spruce.draftgui.files;

import dev.spruce.draftgui.game.draft.Draft;
import dev.spruce.draftgui.game.draft.Player;

import java.util.List;

public class Season {

    private final String name;
    private final String startDate;
    private final String endDate;

    public Season(String name, String startDate, String endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
