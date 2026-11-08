package dev.spruce.draftgui.files;

public class SeasonManager {

    private Season currentSeason;

    public SeasonManager() {

    }

    public Season getCurrentSeason() {
        return currentSeason;
    }

    public void setCurrentSeason(Season season) {
        this.currentSeason = season;
    }
}
