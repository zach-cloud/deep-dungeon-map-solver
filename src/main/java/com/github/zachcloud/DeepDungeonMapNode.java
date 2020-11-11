package com.github.zachcloud;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DeepDungeonMapNode {

    private Map<String, DeepDungeonMapNode> linkedMaps;

    private int x;
    private int y;

    /**
     * Makes a deep dungeon node with no attached maps
     */
    public DeepDungeonMapNode(int x, int y) {
        this.linkedMaps = new HashMap<>();
        this.x = x;
        this.y = y;
    }

    private void verifyDirection(String originalDirection) {
        if (!originalDirection.equalsIgnoreCase("n") && !originalDirection.equalsIgnoreCase("w") &&
                !originalDirection.equalsIgnoreCase("e") && !originalDirection.equalsIgnoreCase("s") &&
                !originalDirection.equalsIgnoreCase("d") && !originalDirection.equalsIgnoreCase("u")) {
            throw new RuntimeException("Invalid direction: " + originalDirection);
        }
    }

    public void addNode(String direction, DeepDungeonMapNode newNode) {
        direction = direction.toLowerCase();
        verifyDirection(direction);
        linkedMaps.put(direction, newNode);
    }

    public DeepDungeonMapNode traverse(String direction) {
        direction = direction.toLowerCase();
        verifyDirection(direction);
        if(linkedMaps.containsKey(direction)) {
            return linkedMaps.get(direction);
        } else {
            throw new RuntimeException("Attempted to go an illegal route. Last direction: " + direction);
        }
    }

    public boolean canGoDirection(String direction) {
        direction = direction.toLowerCase();
        verifyDirection(direction);
        return linkedMaps.containsKey(direction);
    }

    public Set<String> getValidDirections() {
        return linkedMaps.keySet();
    }
}
