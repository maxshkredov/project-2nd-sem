package ru.spbu.apmath.prog.project;

import java.awt.*;

public class Aim {
    public Image aim;

    public Aim(Image image) {
        this.aim = image;
    }

    public void draw(Graphics g, int x, int y){
        g.drawImage(aim, x, y, null);
    }
}