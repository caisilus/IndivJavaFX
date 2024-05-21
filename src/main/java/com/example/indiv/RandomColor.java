package com.example.indiv;

import javafx.scene.paint.Color;

import java.util.Random;

public class RandomColor {
    public static Color getColor() {
        Random random = new Random();
        return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    public static Color getColorFromString(String input) {
        int hash = input.hashCode();
        // Ensure the hash is positive
        hash = hash < 0 ? -hash : hash;

        // Generate RGB components from hash
        int r = (hash & 0xFF0000) >> 16;
        int g = (hash & 0x00FF00) >> 8;
        int b = (hash & 0x0000FF);

        // Create and return the Color object
        return Color.rgb(r, g, b);
    }
}
