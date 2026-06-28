package dev.spruce.draftgui;

import dev.spruce.draftgui.game.Draft;
import dev.spruce.draftgui.game.Player;
import dev.spruce.draftgui.game.PlayerDraft;
import dev.spruce.draftgui.ui.impl.DraftList;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    public static final String DATA_DIR = "data";
    public static final String DRAFTS_DIR = DATA_DIR + File.separator + "drafts";
    public static final String PLAYERS_DIR = DATA_DIR + File.separator + "players";

    public static final String DRAFT_FILE_EXTENSION = ".draft";
    public static final String PLAYER_FILE_EXTENSION = ".player";
    public static final String PLAYER_DATA_SEPARATOR = ":";
    public static final String DRAFT_DATA_SEPARATOR = ";";

    private List<Player> players;

    public void init() {
        this.players = new ArrayList<>();
        folderCheck();
        loadPlayerData();
    }

    private void folderCheck() {
        File draftsDir = new File(DRAFTS_DIR);
        if (!draftsDir.exists()) {
            draftsDir.mkdirs();
        }

        File playersDir = new File(PLAYERS_DIR);
        if (!playersDir.exists()) {
            playersDir.mkdirs();
        }
    }

    public void loadPlayerData() {
        File playersDir = new File(PLAYERS_DIR);
        if (!playersDir.exists()) {
            throw new IllegalStateException("Players directory does not exist: " + PLAYERS_DIR);
        }

        File[] playerFiles = playersDir.listFiles((dir, name) -> name.endsWith(PLAYER_FILE_EXTENSION));
        if (playerFiles == null) {
            throw new IllegalStateException("Failed to list player files in directory: " + PLAYERS_DIR);
        }

        for (File playerFile : playerFiles) {
            try {
                String data = Files.readString(playerFile.toPath());
                Player player = new Player("");
                player.load(data);
                this.players.add(player);
            } catch (Exception e) {
                System.err.println("Failed to load player profile from file: " + playerFile.getName());
                e.printStackTrace();
            }
        }
    }

    public void savePlayerData() {
        File playersDir = new File(PLAYERS_DIR);
        if (!playersDir.exists()) {
            throw new IllegalStateException("Players directory does not exist: " + PLAYERS_DIR);
        }

        for (Player player : this.players) {
            String filename = player.getSaveName();
            File playerFile = new File(playersDir, filename);

            try {
                FileWriter fileWriter = new FileWriter(playerFile);
                fileWriter.write(player.save());
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void saveDraftFile(Draft draft) {
        String filename = draft.getSaveName();
        File draftFile = new File(DRAFTS_DIR, filename);

        try {
            FileWriter fileWriter = new FileWriter(draftFile);
            fileWriter.write(draft.save());
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Draft> loadDraftFiles() {
        List<Draft> drafts = new ArrayList<>();

        File draftsDir = new File(DRAFTS_DIR);
        if (!draftsDir.exists()) {
            throw new IllegalStateException("Drafts directory does not exist: " + DRAFTS_DIR);
        }

        File[] draftFiles = draftsDir.listFiles((dir, name) -> name.endsWith(DRAFT_FILE_EXTENSION));
        if (draftFiles == null) {
            throw new IllegalStateException("Failed to list draft files in directory: " + DRAFTS_DIR);
        }

        for (File draftFile : draftFiles) {
            try {
                String data = Files.readString(draftFile.toPath());
                Draft draft = new Draft("", "", new ArrayList<>());
                draft.load(data);
                drafts.add(draft);
            } catch (Exception e) {
                System.err.println("Failed to load draft from file: " + draftFile.getName());
                e.printStackTrace();
            }
        }

        return drafts;
    }

    public void saveAll() {
        savePlayerData();
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public Player getPlayerByName(String name) {
        for (Player player : this.players) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return null;
    }

    public void updatePlayerWins() {
        this.players.forEach(player -> player.setWins(0));
        for (Draft draft : loadDraftFiles()) {
            List<PlayerDraft> winners = calculateWinners(draft);

            for (PlayerDraft winner : winners) {
                Player player = winner.getPlayer();
                player.incrementWins();
            }
        }
    }

    private List<PlayerDraft> calculateWinners(Draft draft) {
        List<PlayerDraft> winners = new ArrayList<>();
        int highestRound = 0;

        for (PlayerDraft playerDraft : draft.getPlayerDrafts()) {
            if (playerDraft.getRound() > highestRound) {
                highestRound = playerDraft.getRound();
                winners.clear();
                winners.add(playerDraft);
            } else if (playerDraft.getRound() == highestRound) {
                winners.add(playerDraft);
            }
        }

        return winners;
    }

    public float getPlayerWinPercentage(Player player) {
        int totalDrafts = 0;
        int wins = player.getWins();

        for (Draft draft : loadDraftFiles()) {
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
}
