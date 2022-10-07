package ru.spbu.apmath.prog.project;

import java.awt.*;

public class Sprite {
    private Image image;

    public Sprite(Image image) {
        this.image = image;
    }

    public void draw(Graphics g, int x, int y) {
        g.drawImage(image, x, y,null);
    }
}