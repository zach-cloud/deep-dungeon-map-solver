package com.github.zachcloud;

import java.util.HashSet;
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

    public boolean findNextDirection(Speedwalk sw, DeepDungeonMapNode node) {
        if(node.isExit()) {
            System.out.println("Found an exit - returning");
            return true;
        }
        // Attempt to walk all valid directions
        for(String dir : new HashSet<>(node.getValidDirections())) {
            sw.addDirection(dir);
            System.out.println("Proceeding " + dir + " from " + node.toString());
            // Before we traverse forward, remove these exits so we don't take them again
            DeepDungeonMapNode nextNode = node.traverse(dir);
            node.unlinkDirection(dir);
            nextNode.unlinkDirection(DirectionUtils.reverseDirecrtion(dir));
            if(findNextDirection(sw, nextNode)) {
                // We found an exit
                System.out.println("Found an exit");
               return true;
            } // Else, we simply continue traversing. No action to take.
        }
        // Walked all paths and didn't find an answer.
        System.out.println("Found a dead end - backtracking");
        sw.removeLast(1);
        return false;
    }

    public Speedwalk solve() {
        Speedwalk sw = new Speedwalk();
        findNextDirection(sw, origin);
        // Finally, add the down direction to proceed to next floor.
        sw.addDirection("d");
        return sw;
    }


    /**
     * Converts the String representation
     * of the map into a deep dungeon map node
     *
     * @param data map data
     * @return Origin node
     */
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
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                indices[y][x] = lines[y].charAt(x);
                if (indices[y][x] == ORIGIN_SPACE) {
                    startX = x;
                    startY = y;
                    foundOrigin = true;
                } else if (indices[y][x] == DESTINATION_SPACE) {
                    foundExit = true;
                }
            }
        }

        if (!foundExit || !foundOrigin) {
            throw new RuntimeException("Map cannot be solved (no entrance/exit)");
        }

        System.out.println("Found origin at " + startX + ", " + startY);

        DeepDungeonMapNode origin = new DeepDungeonMapNode(startX, startY);
        origin.markAsOrigin();
        explore(origin, indices, startX, startY);
        return origin;
    }

    private void explore(DeepDungeonMapNode thisNode, char[][] indices, int x, int y) {
        // Starting at this node, we will explore all directions
        // We'd rather not go back to this node.
        // So we block it off on the indices
        indices[y][x] = '*';

        // Let's explore each of the four directions.
        exploreDirection(thisNode, indices, x, y, 0, 1, "s", "n"); // South
        exploreDirection(thisNode, indices, x, y, 0, -1, "n", "s"); // North
        exploreDirection(thisNode, indices, x, y, 1, 0, "e", "w"); // East
        exploreDirection(thisNode, indices, x, y, -1, 0, "w", "e"); // West
    }

    private void exploreDirection(DeepDungeonMapNode thisNode, char[][] indices, int x, int y,
                                  int xDiff, int yDiff, String thisDirection, String oppositeDirection) {
        try {
            // In order for this to be valid, the two spaces below this one (y + 1) and (y+ 2) must be empty
            char emptySpace1 = indices[y + yDiff][x + xDiff];
            char emptySpace2 = indices[y + (yDiff * 2)][x + (xDiff * 2)];
            if (emptySpace1 == EMPTY_SPACE && emptySpace2 == EMPTY_SPACE) {
                // The origin space can be any valid space delimiter
                char roomSpace = indices[y + (yDiff * 3)][x + (xDiff * 3)];
                if (roomSpace == EMPTY_SPACE || roomSpace == DESTINATION_SPACE || roomSpace == ORIGIN_SPACE) {
                    // This is a valid map square.
                    DeepDungeonMapNode newNode = new DeepDungeonMapNode(x + (xDiff * 3), y + (yDiff * 3));
                    // Add exits to the maps that need it
                    thisNode.addNode(thisDirection, newNode);
                    newNode.addNode(oppositeDirection, thisNode);
                    // Mark this as origin or exit as needed
                    if(roomSpace == ORIGIN_SPACE) {
                        newNode.markAsOrigin();
                    } else if(roomSpace == DESTINATION_SPACE) {
                        newNode.markAsExit();
                    }
                    // Explore routes from this one
                    explore(newNode, indices, x + (xDiff * 3), y + (yDiff * 3));
                }
            }
        } catch (Exception ex) {
            // We went out of bounds, so it's not traversable.
        }
    }

    /**
     * Checks that the map has the proper amount of lines,
     * and proper amount of characters per line.
     *
     * @param lines Map lines
     */
    private void checkMapIntegrity(String[] lines) {
        if (lines.length != HEIGHT) {
            throw new RuntimeException("Invalid map data (height == " + lines.length + ")");
        }
        for (int y = 0; y < HEIGHT; y++) {
            String line = lines[y];
            if (line.length() != WIDTH) {
                throw new RuntimeException("Invalid map data (width == " + line.length() + ")");
            }
        }
    }
}
