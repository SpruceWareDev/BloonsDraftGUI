package dev.spruce.draftgui.files;

import dev.spruce.draftgui.game.draft.Draft;
import dev.spruce.draftgui.game.draft.Player;

import java.util.List;

public class Season {

    private String name;
    private String startDate;
    private String endDate;

    private List<Player> players;
    private List<Draft> drafts;

    public Season(String name, String startDate, String endDate, List<Player> players, List<Draft> drafts) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.players = players;
        this.drafts = drafts;
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
