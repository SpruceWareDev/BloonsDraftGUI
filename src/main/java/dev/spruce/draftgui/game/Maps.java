package dev.spruce.draftgui.game;

import java.util.List;

public class Maps {

    public static final List<String> BEGINNER_MAPS = List.of(
           "Monkey Meadow",
           "Tree Stump",
           "Town Center",
           "Middle of the Road",
           "One Two Tree",
           "Scrapyard",
           "The Cabin",
           "Resort",
           "Skates",
           "Lotus Island",
           "Candy Falls",
           "Winter Park",
           "Carved",
           "Park Path",
           "Alpine Run",
           "Frozen Over",
           "In The Loop",
           "Quarry",
           "Cubism",
           "Four Circles",
           "Hedge",
           "End of the Road",
           "Logs",
           "Water Park",
           "Three Mines"
    );

    public static final List<String> INTERMEDIATE_MAPS = List.of(
        "KartsNDarts",
        "Moon Landing",
        "Luminous Cove",
        "Cracked",
        "Streambed",
        "Chutes",
        "Rake",
        "Spice Islands",
        "Bazaar",
        "Adora's Temple",
        "Spring Spring",
        "Kelp Forest",
        "Quarry",
        "Balance",
        "Encrypted",
        "Bazaar",
        "Haunted",
        "Downstream",
        "Firing Range",
        "Polyphemus",
        "Covered Garden",
        "Bloonarius Prime",
        "Sulfur Springs",
        "Lost Crevasse",
        "Erosion"
    );

    public static final List<String> ADVANCED_MAPS = List.of(
        "Cornfield",
        "Underground",
        "Off The Coast",
        "Another Brick",
        "High Finance",
        "Peninsula",
        "Pat's Pond",
        "Cargo",
        "Spillway",
        "Geared",
        "Mesa",
        "X Factor",
        "Sunken Columns",
        "Midnight Mansion",
        "Erosion",
        "Dark Path",
        "Castle Revenge",
        "Ancient Portal",
        "Last Resort",
        "Enchanted Glade",
        "Water Park Parade",
        "Bloonarius Prime"
    );

    public static final List<String> EXPERT_MAPS = List.of(
        "Dark Castle",
        "Quad",
        "Workshop",
        "Bloody Puddles",
        "Infernal",
        "Flooded Valley",
        "Ravine",
        "Sanctuary",
        "Dark Dungeons",
        "Glacial Trail",
        "Tricky Tracks",
        "Muddy Puddles",
        "#Ouch"
    );

    public static String getRandomBeginnerMap() {
        int randomIndex = (int) (Math.random() * BEGINNER_MAPS.size());
        return BEGINNER_MAPS.get(randomIndex);
    }

    public static String getRandomIntermediateMap() {
        int randomIndex = (int) (Math.random() * INTERMEDIATE_MAPS.size());
        return INTERMEDIATE_MAPS.get(randomIndex);
    }

    public static String getRandomAdvancedMap() {
        int randomIndex = (int) (Math.random() * ADVANCED_MAPS.size());
        return ADVANCED_MAPS.get(randomIndex);
    }

    public static String getRandomExpertMap() {
        int randomIndex = (int) (Math.random() * EXPERT_MAPS.size());
        return EXPERT_MAPS.get(randomIndex);
    }
}
