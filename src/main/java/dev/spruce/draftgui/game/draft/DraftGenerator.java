package dev.spruce.draftgui.game.draft;

import dev.spruce.draftgui.game.tower.Tower;
import dev.spruce.draftgui.game.tower.Towers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DraftGenerator {

    public static List<PlayerDraft> generateBalancedDrafts(List<Player> players) {
        List<PlayerDraft> drafts = new ArrayList<>();
        List<Map.Entry<Tower, Integer>> towerRanks = Towers.getSortedTowerRanks();
        towerRanks.sort((a, b) -> b.getValue() - a.getValue()); // Sort towers by rank descending

        for (Player player : players) {
            drafts.add(new PlayerDraft(player, new ArrayList<>(), 0));
        }

        for (Map.Entry<Tower, Integer> towerEntry : towerRanks) {
            PlayerDraft leastRankedDraft = drafts.stream()
                    .min((d1, d2) -> {
                        int rank1 = d1.getTowers().stream().mapToInt(t -> Towers.getTowerRanks().get(t)).sum();
                        int rank2 = d2.getTowers().stream().mapToInt(t -> Towers.getTowerRanks().get(t)).sum();
                        rank1 += (int) (Math.random() * 5 - 2);
                        rank2 += (int) (Math.random() * 5 - 2);
                        return Integer.compare(rank1, rank2);
                    })
                    .orElse(null);

            if (leastRankedDraft != null) {
                leastRankedDraft.getTowers().add(towerEntry.getKey());
            }
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
