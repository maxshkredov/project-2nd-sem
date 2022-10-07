package ru.spbu.apmath.prog.project;

import java.awt.*;

public class GameBackground {
    private Image background;

    public GameBackground(Image image) {
        this.background = image;
    }

    public void draw(Graphics g, int x, int y){
        g.drawImage(background, x, y, null);
    }
}
