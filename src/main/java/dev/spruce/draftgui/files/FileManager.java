package dev.spruce.draftgui.files;

import dev.spruce.draftgui.Application;
import dev.spruce.draftgui.game.draft.Draft;
import dev.spruce.draftgui.game.draft.Player;
import dev.spruce.draftgui.game.draft.PlayerDraft;
import dev.spruce.draftgui.utils.Statistics;

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

    /**
     * Checks if the necessary folders exist, and creates them if they don't.
     */
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

    /**
     * Loads player data from the players directory.
     * If the directory does not exist, an IllegalStateException is thrown.
     * If a player file cannot be read or parsed, an error message is printed to the console.
     */
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

    /**
     * Saves player data to the players directory.
     * If the directory does not exist, an IllegalStateException is thrown.
     * If a player file cannot be written, a RuntimeException is thrown.
     */
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

    /**
     * Creates an empty season with the specified name, start date, and end date.
     * It creates a directory for the season, a season info file and a drafts directory.
     *
     * @param name      The name of the season.
     * @param startDate The start date of the season in "YYYY-MM-DD" format.
     * @param endDate   The end date of the season in "YYYY-MM-DD" format.
     * @return A new Season object representing the created season.
     */
    public Season createEmptySeason(String name, String startDate, String endDate) {
        File seasonDir = new File(DRAFTS_DIR + File.separator + name);
        if (!seasonDir.exists()) {
            seasonDir.mkdirs();
        }

        File seasonFile = new File(seasonDir, "season.info");
        try {
            if (!seasonFile.exists()) {
                seasonFile.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(seasonFile);
            fileWriter.write(name + DRAFT_DATA_SEPARATOR + startDate + DRAFT_DATA_SEPARATOR + endDate);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File draftsDir = new File(seasonDir, "drafts");
        if (!draftsDir.exists()) {
            draftsDir.mkdirs();
        }

        return new Season(name, startDate, endDate);
    }

    /**
     * Loads all seasons from the drafts directory.
     * It reads the season info files and creates Season objects for each valid season.
     *
     * @return A list of Season objects representing the loaded seasons.
     */
    public List<Season> loadSeasons() {
        List<Season> seasons = new ArrayList<>();
        File draftsDir = new File(DRAFTS_DIR);
        if (!draftsDir.exists()) {
            throw new IllegalStateException("Drafts directory does not exist: " + DRAFTS_DIR);
        }

        File[] seasonDirs = draftsDir.listFiles(File::isDirectory);
        if (seasonDirs == null) {
            throw new IllegalStateException("Failed to list season directories in: " + DRAFTS_DIR);
        }

        for (File seasonDir : seasonDirs) {
            File seasonFile = new File(seasonDir, "season.info");
            if (!seasonFile.exists()) {
                System.err.println("Season info file does not exist for season: " + seasonDir.getName());
                continue;
            }

            try {
                String data = Files.readString(seasonFile.toPath());
                String[] parts = data.split(DRAFT_DATA_SEPARATOR);
                if (parts.length != 3) {
                    System.err.println("Invalid season info format for season: " + seasonDir.getName());
                    continue;
                }
                String name = parts[0];
                String startDate = parts[1];
                String endDate = parts[2];
                seasons.add(new Season(name, startDate, endDate));
            } catch (IOException e) {
                System.err.println("Failed to load season info from file: " + seasonFile.getName());
                e.printStackTrace();
            }
        }

        return seasons;
    }

    /**
     * Determines the next draft index for a given date and map.
     * It scans the drafts directory for existing draft files that match the specified date and map,
     * extracts their indices, and returns the next available index.
     *
     * @param date The date of the draft in "YYYY-MM-DD" format.
     * @param map  The name of the map for the draft.
     * @return The next available draft index (starting from 1).
     */
    public int getNextDraftIndex(Season season, String date, String map) {
        File draftsDir = new File(getSeasonDir(season) + File.separator + "drafts");
        File[] files = draftsDir.listFiles((dir, name) -> name.endsWith(DRAFT_FILE_EXTENSION));
        if (files == null) return 1;

        String prefix = date + map + "_";
        int max = 0;
        for (File f : files) {
            String name = f.getName();
            if (!name.startsWith(prefix)) continue;
            String numPart = name.substring(prefix.length(), name.length() - DRAFT_FILE_EXTENSION.length());
            try {
                max = Math.max(max, Integer.parseInt(numPart));
            } catch (NumberFormatException ignore) {}
        }
        return max+1;
    }

    public int getNextDraftIndex(String date, String map) {
        return getNextDraftIndex(Application.getSeasonManager().getCurrentSeason(), date, map);
    }

    public void saveDraftFile(Season season, Draft draft) {
        String filename = draft.getSaveName();
        File draftFile = new File(getSeasonDir(season) + File.separator + "drafts", filename);

        try {
            FileWriter fileWriter = new FileWriter(draftFile);
            fileWriter.write(draft.save());
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveDraftFile(Draft draft) {
        saveDraftFile(Application.getSeasonManager().getCurrentSeason(), draft);
    }

    public List<Draft> loadDraftFiles(Season season) {
        List<Draft> drafts = new ArrayList<>();

        File draftsDir = new File(getSeasonDir(season) + File.separator + "drafts");
        if (!draftsDir.exists()) {
            throw new IllegalStateException("Drafts directory does not exist: " + draftsDir.getAbsolutePath());
        }

        File[] draftFiles = draftsDir.listFiles((dir, name) -> name.endsWith(DRAFT_FILE_EXTENSION));
        if (draftFiles == null) {
            throw new IllegalStateException("Failed to list draft files in directory: " + draftsDir.getAbsolutePath());
        }

        for (File draftFile : draftFiles) {
            try {
                String data = Files.readString(draftFile.toPath());
                Draft draft = new Draft("", "", new ArrayList<>(), false);
                draft.load(data);
                drafts.add(draft);
            } catch (Exception e) {
                System.err.println("Failed to load draft from file: " + draftFile.getName());
                e.printStackTrace();
            }
        }

        return drafts;
    }

    public List<Draft> loadDraftFiles() {
        return loadDraftFiles(Application.getSeasonManager().getCurrentSeason());
    }

    public List<Float> getPlayersRounds(Player player) {
        List<Float> rounds = new ArrayList<>();
        for (Draft draft : loadDraftFiles()) {
            for (PlayerDraft playerDraft : draft.getPlayerDrafts()) {
                if (playerDraft.getPlayer().getName().equals(player.getName())) {
                    rounds.add((float) playerDraft.getRound());
                }
            }
        }
        return rounds;
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
            List<PlayerDraft> winners = Statistics.get().calculateWinners(draft);

            for (PlayerDraft winner : winners) {
                Player player = winner.getPlayer();
                player.incrementWins();
            }
        }
    }

    public String getSeasonDir(Season season) {
        return DRAFTS_DIR + File.separator + season.getName();
    }
}
