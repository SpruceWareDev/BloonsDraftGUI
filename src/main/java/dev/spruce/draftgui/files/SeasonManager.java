package dev.spruce.draftgui.files;

import dev.spruce.draftgui.Application;
import dev.spruce.draftgui.utils.DateUtils;

import java.util.List;

public class SeasonManager {

    private Season currentSeason;
    private final List<Season> seasons;

    public SeasonManager(List<Season> seasons) {
        this.seasons = seasons;
        if (!seasons.isEmpty()) {
            // Set current season to the latest
            this.currentSeason = seasons.get(seasons.size() - 1);
        }
    }

    public void createNewSeason(String name) {
        Season season = Application.getFileManager().createEmptySeason(name, DateUtils.getDate(), "Ongoing");
        this.seasons.add(season);
    }

    public Season getCurrentSeason() {
        return currentSeason;
    }

    public void setCurrentSeason(Season season) {
        this.currentSeason = season;
    }

    public void setSeasonByName(String name) {
        for (Season season : this.seasons) {
            if (season.getName().equals(name)) {
                this.currentSeason = season;
                return;
            }
        }
    }

    public List<Season> getSeasons() {
        return seasons;
    }
}
