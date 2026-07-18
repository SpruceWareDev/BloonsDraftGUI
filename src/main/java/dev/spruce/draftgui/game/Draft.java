package dev.spruce.draftgui.game;

import dev.spruce.draftgui.Application;
import dev.spruce.draftgui.FileManager;
import dev.spruce.draftgui.files.ISaveable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Draft implements ISaveable {

    private static final int DATE_INDEX = 0;
    private static final int MAP_INDEX = 1;
    private static final int PLAYER_DATA_INDEX = 2;

    private String date;
    private String map;

    private final List<Player> players;
    private List<PlayerDraft> playerDrafts;
    private final int numPlayers;

    public Draft(String date, String map, List<Player> players) {
        this.date = date;
        this.map = map;
        this.players = players;
        this.playerDrafts = new ArrayList<>();
        if (players.size() > 1) {
            this.playerDrafts = generateDrafts(players);
        }
        this.numPlayers = playerDrafts.size();
    }

    public void regenerateDraft() {
        this.playerDrafts = generateDrafts(this.players);
    }

    private List<PlayerDraft> generateDrafts(List<Player> players) {
        List<PlayerDraft> drafts = new ArrayList<>();

        int towersPerPlayer = Towers.TOWERS.size() / players.size();
        List<Tower> usedTowers = new ArrayList<>();
        for (Player player : players) {
            List<Tower> playerTowers = new ArrayList<>();
            while (playerTowers.size() < towersPerPlayer) {
                Tower randomTower = Towers.TOWERS.get((int) (Math.random() * Towers.TOWERS.size()));
                if (!usedTowers.contains(randomTower)) {
                    playerTowers.add(randomTower);
                    usedTowers.add(randomTower);
                }
            }
            drafts.add(new PlayerDraft(player, playerTowers, 0));
        }

        return drafts;
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
        return this.date + this.map + (int) (Math.random() * 100f) + FileManager.DRAFT_FILE_EXTENSION;
    }
}
