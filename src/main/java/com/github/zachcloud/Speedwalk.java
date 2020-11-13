package com.github.zachcloud;

import java.util.ArrayList;
import java.util.List;

public class Speedwalk {

    private List<String> speedwalk;

    public Speedwalk() {
        this.speedwalk = new ArrayList<>();
    }

    public void addDirection(String newDirection) {
        newDirection = newDirection.toLowerCase();
        DirectionUtils.verifyDirection(newDirection);
        speedwalk.add(newDirection);
    }

    public void removeLast(int count) {
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                speedwalk.remove(speedwalk.size() - 1);
            }
        }
    }

    public String getLastDirection() {
        return speedwalk.get(speedwalk.size() - 1);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String s : speedwalk) {
            builder.append(s).append(";");
        }
        if (builder.length() > 0) {
            builder.setLength(builder.length() - 1);
        }
        return builder.toString();
    }
}
