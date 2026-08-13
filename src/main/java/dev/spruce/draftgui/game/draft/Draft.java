package dev.spruce.draftgui.game.draft;

import dev.spruce.draftgui.Application;
import dev.spruce.draftgui.FileManager;
import dev.spruce.draftgui.files.ISaveable;
import dev.spruce.draftgui.game.tower.Tower;
import dev.spruce.draftgui.game.tower.Towers;

import java.util.ArrayList;
import java.util.List;

public class Draft implements ISaveable {

    private static final int DATE_INDEX = 0;
    private static final int MAP_INDEX = 1;
    private static final int PLAYER_DATA_INDEX = 2;

    private String date;
    private String map;
    private String fileName;

    private final List<Player> players;
    private List<PlayerDraft> playerDrafts;
    private final int numPlayers;
    private boolean randomDraft;

    public Draft(String date, String map, List<Player> players, boolean randomDraft) {
        this.date = date;
        this.map = map;
        this.players = players;
        this.randomDraft = randomDraft;
        this.playerDrafts = new ArrayList<>();
        if (players.size() > 1) {
            regenerateDraft();
        }
        this.numPlayers = playerDrafts.size();
    }

    public Draft(String date, String map, List<Player> players, List<PlayerLoadout> playerLoadouts) {
        this.date = date;
        this.map = map;
        this.players = players;
        this.playerDrafts = new ArrayList<>();
        if (playerLoadouts.size() > 1) {
            for (PlayerLoadout loadout : playerLoadouts) {
                this.playerDrafts.add(new PlayerDraft(loadout.getPlayer(), loadout.getTowers(), 0));
            }
        }
        this.numPlayers = playerDrafts.size();
    }

    public void regenerateDraft() {
        if (randomDraft) {
            this.playerDrafts = DraftGenerator.generateDraftsRandom(players);
            System.out.println("Random draft generated for players: " + players);
        } else {
            this.playerDrafts = DraftGenerator.generateBalancedDrafts(players);
        }
    }

    public List<Tower> getLeftOverTowers() {
        List<Tower> usedTowers = new ArrayList<>();
        for (PlayerDraft playerDraft : playerDrafts) {
            usedTowers.addAll(playerDraft.getTowers());
        }
        List<Tower> leftOverTowers = new ArrayList<>(Towers.TOWERS);
        leftOverTowers.removeAll(usedTowers);
        return leftOverTowers;
    }

    public String getDate() {
        return date;
    }

    public String getMap() {
        return map;
    }

    public List<PlayerDraft> getPlayerDrafts() {
        return playerDrafts;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    @Override
    public String save() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date).append(FileManager.DRAFT_DATA_SEPARATOR);
        stringBuilder.append(map).append(FileManager.DRAFT_DATA_SEPARATOR);

        for (PlayerDraft playerDraft : this.playerDrafts) {
            Player player = playerDraft.getPlayer();
            stringBuilder.append(player.getName()).append(FileManager.PLAYER_DATA_SEPARATOR);
            stringBuilder.append(playerDraft.getRound()).append(FileManager.PLAYER_DATA_SEPARATOR);
            for (Tower tower : playerDraft.getTowers()) {
                stringBuilder.append(tower.getName()).append(FileManager.PLAYER_DATA_SEPARATOR);
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append(FileManager.DRAFT_DATA_SEPARATOR);
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        return stringBuilder.toString();
    }

    @Override
    public void load(String data) {
        String[] elements = data.split(FileManager.DRAFT_DATA_SEPARATOR);
        this.date = elements[DATE_INDEX];
        this.map = elements[MAP_INDEX];

        for (int i = PLAYER_DATA_INDEX; i < elements.length; i++) {
            String[] playerElements = elements[i].split(FileManager.PLAYER_DATA_SEPARATOR);
            String playerName = playerElements[0];
            String roundString = playerElements[1];
            List<Tower> towers = new ArrayList<>();
            for (int j = 2; j < playerElements.length; j++) {
                Tower tower = Towers.getTowerByName(playerElements[j]);
                towers.add(tower);
            }
            int playerRound = Integer.parseInt(roundString);
            Player player = Application.getFileManager().getPlayerByName(playerName);
            this.playerDrafts.add(new PlayerDraft(player, towers, playerRound));
        }
    }

    @Override
    public String getSaveName() {
        if (fileName == null) {
            int index = Application.getFileManager().getNextDraftIndex(date, map);
            fileName = date + map + "_" + index + FileManager.DRAFT_FILE_EXTENSION;
        }
        return fileName;
    }

    public void setMap(String map) {
        this.map = map;
    }
}
