package com.github.zachcloud;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DeepDungeonMap {

    private DeepDungeonMapNode origin;

    private static final char ORIGIN_SPACE = 'U';
    private static final char DESTINATION_SPACE = 'D';
    private static final char EMPTY_SPACE = ' ';
    private static final char WALL_SPACE = '*';

    private static final int HEIGHT = 21;
    private static final int WIDTH = 21;

    private static class XYPair {
        int x;
        int y;

        public XYPair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            XYPair xyPair = (XYPair) o;
            return x == xyPair.x &&
                    y == xyPair.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    /**
     * Creates a Deep Dungeon Map object from the
     * given String representation.
     *
     * @param representation String representation
     *                       Pulled from the UOSSMud game
     */
    public DeepDungeonMap(String representation) {
        this.origin = deserialize(representation);
    }

    public DeepDungeonMapNode getOrigin() {
        return origin;
    }

    public Speedwalk solve() {
        return null;
    }

    private DeepDungeonMapNode deserialize(String data) {
        String[] lines = data.split("\n");
        checkMapIntegrity(lines);

        // Holds the starting locations (the 'U' character)
        int startX = -1;
        int startY = -1;

        boolean foundOrigin = false;
        boolean foundExit = false;

        // Populate an array holding the characters from the map
        // for easy lookup later.
        char[][] indices = new char[HEIGHT][WIDTH];
        for(int y = 0; y < HEIGHT; y++) {
            for(int x = 0; x < WIDTH; x++) {
                indices[y][x] = lines[y].charAt(x);
                if(indices[y][x] == ORIGIN_SPACE) {
                    startX = x;
                    startY = y;
                    foundOrigin = true;
                } else if(indices[y][x] == DESTINATION_SPACE) {
                    foundExit = true;
                }
            }
        }

        if(!foundExit || !foundOrigin) {
            throw new RuntimeException("Map cannot be solved (no entrance/exit)");
        }

        System.out.println("Found origin at " + startX + ", " + startY);
        return null;
    }

    private void checkMapIntegrity(String[] lines) {
        if(lines.length != HEIGHT) {
            throw new RuntimeException("Invalid map data (height == " + lines.length + ")");
        }
        for(int y = 0;  y < HEIGHT; y++) {
            String line = lines[y];
            if(lines.length != HEIGHT) {
                throw new RuntimeException("Invalid map data (height == " + lines.length + ")");
            }
        }
    }
}
