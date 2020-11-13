package com.github.zachcloud;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.github.zachcloud.DirectionUtils.verifyDirection;

public class DeepDungeonMapNode {

    private Map<String, DeepDungeonMapNode> linkedMaps;

    private int x;
    private int y;

    private boolean isExit;
    private boolean isOrigin;

    /**
     * Makes a deep dungeon node with no attached maps
     */
    public DeepDungeonMapNode(int x, int y) {
        this.linkedMaps = new HashMap<>();
        this.x = x;
        this.y = y;
    }



    public void addNode(String direction, DeepDungeonMapNode newNode) {
        direction = direction.toLowerCase();
        verifyDirection(direction);
        linkedMaps.put(direction, newNode);
    }

    public void unlinkDirection(String direction) {
        direction = direction.toLowerCase();
        verifyDirection(direction);
        linkedMaps.remove(direction);
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

    public boolean isDeadEnd() {
        return linkedMaps.size() <= 1; // all maps have at least one exit, which is the entrance from another room
    }

    public void markAsOrigin() {
        this.isOrigin = true;
    }

    public void markAsExit() {
        this.isExit = true;
    }

    public Set<String> getValidDirections() {
        return linkedMaps.keySet();
    }

    public boolean isExit() {
        return isExit;
    }

    @Override
    public String toString() {
        return "DeepDungeonMapNode{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
