package ru.spbu.apmath.prog.project;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HeroModel {
    private Rectangle rect;

    HeroModel(Rectangle rect, String img){
        this.rect = rect;
        setImg(img);
    }

    public void setImg(String imageName){
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(getClass().getResource(imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Graphics2D g2d = bi.createGraphics();
        g2d.setPaint (new Color(0, 0, 0));
        g2d.fillRect (0, 0, bi.getWidth(), bi.getHeight());
    }
}
//    ImageIcon img = new ImageIcon(imageName);
//    int w = img.getWidth(null);
//    int h = img.getHeight(null);
//    BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
//    Graphics g = bi.getGraphics();
//    g.drawImage(img, 0, 0, null);
