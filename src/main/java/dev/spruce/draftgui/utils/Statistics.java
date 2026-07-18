package dev.spruce.draftgui.utils;

import dev.spruce.draftgui.Application;
import dev.spruce.draftgui.game.Draft;
import dev.spruce.draftgui.game.Player;
import dev.spruce.draftgui.game.PlayerDraft;

import java.util.ArrayList;
import java.util.List;

public class Statistics {

    private static Statistics instance;

    private List<Draft> drafts;

    public Statistics() {
        updateDraftFiles();
    }

    public void updateDraftFiles() {
        this.drafts = Application.getFileManager().loadDraftFiles();
    }

    public float getPlayerWinPercentage(Player player) {
        int totalDrafts = 0;
        int wins = player.getWins();

        for (Draft draft : drafts) {
            for (PlayerDraft playerDraft : draft.getPlayerDrafts()) {
                if (playerDraft.getPlayer().equals(player)) {
                    totalDrafts++;
                    break;
                }
            }
        }

        if (totalDrafts == 0) {
            return 0.0f;
        }

        return (float) wins / totalDrafts * 100;
    }

    public int getPlayerGamesPlayed(Player player) {
        int gamesPlayed = 0;

        for (Draft draft : drafts) {
            for (PlayerDraft playerDraft : draft.getPlayerDrafts()) {
                if (playerDraft.getPlayer().equals(player)) {
                    gamesPlayed++;
                    break;
                }
            }
        }

        return gamesPlayed;
    }

    public int getPlayerHighestRound(Player player) {
        int highestRound = 0;

        for (Draft draft : drafts) {
            for (PlayerDraft playerDraft : draft.getPlayerDrafts()) {
                if (playerDraft.getPlayer().equals(player)) {
                    if (playerDraft.getRound() > highestRound) {
                        highestRound = playerDraft.getRound();
                    }
                }
            }
        }

        return highestRound;
    }

    public int getAverageRound(Player player) {
        int totalRounds = 0;
        int gamesPlayed = 0;

        for (Draft draft : drafts) {
            for (PlayerDraft playerDraft : draft.getPlayerDrafts()) {
                if (playerDraft.getPlayer().equals(player)) {
                    totalRounds += playerDraft.getRound();
                    gamesPlayed++;
                    break;
                }
            }
        }

        if (gamesPlayed == 0) {
            return 0;
        }

        return totalRounds / gamesPlayed;
    }

    public int getOverallAverageRound() {
        int totalRounds = 0;
        int totalGames = 0;

        for (Draft draft : drafts) {
            for (PlayerDraft playerDraft : draft.getPlayerDrafts()) {
                totalRounds += playerDraft.getRound();
                totalGames++;
            }
        }

        if (totalGames == 0) {
            return 0;
        }

        return totalRounds / totalGames;
    }

    public static Statistics get() {
        if (instance == null) {
            instance = new Statistics();
        }
        return instance;
    }
}
