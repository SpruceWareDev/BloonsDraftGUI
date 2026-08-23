package dev.spruce.draftgui.game.draft;

import dev.spruce.draftgui.game.tower.Tower;
import dev.spruce.draftgui.game.tower.Towers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DraftGenerator {

    public static List<PlayerDraft> generateBalancedDrafts(List<Player> players) {
        List<PlayerDraft> drafts = new ArrayList<>();
        List<Map.Entry<Tower, Integer>> towerRanks = Towers.getSortedTowerRanks();

        // Sort towers by their money rank descending
        towerRanks.sort((a, b) -> b.getKey().getRank().money() - a.getKey().getRank().money());

        // Shuffle the towers to add randomization
        Collections.shuffle(towerRanks);

        // Initialize drafts for each player
        for (Player player : players) {
            drafts.add(new PlayerDraft(player, new ArrayList<>(), 0));
        }

        // Distribute the towers evenly among players
        int playerIndex = 0;
        for (Map.Entry<Tower, Integer> towerEntry : towerRanks) {
            drafts.get(playerIndex).getTowers().add(towerEntry.getKey());
            playerIndex = (playerIndex + 1) % players.size(); // Rotate to the next player
        }

        return drafts;
    }

    public static List<PlayerDraft> generateDraftsRandom(List<Player> players) {
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
}
