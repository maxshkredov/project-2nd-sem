package ru.spbu.apmath.prog.project;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;

    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private static int WIDTH = screenSize.width - 10;
    private static int HEIGHT = screenSize.height - 35;
    private static String NAME = "Game of worms";
    private static GameBackground background;

    private static Sprite hero1;
    private static Sprite hero2;
    private static boolean currentHero1 = true;

    private static double player1_x = 180;
    private static double player1_y = 360;
    private static double player2_x = 1100;
    private static double player2_y = 310;

    private static Aim aim;

    private static Bullet bullet;
    private static int bull_x = 0;
    private static int bull_y = 0;
    private boolean bulletFlyingFlag = false;
    private int distance = 50;

    private boolean fallingFlag = false;
    private boolean flyingFlag = false;
    private int flyCounter = 0;
    private int fallCounter = 0;
    private int bulletFlyCounter = 0;

    private boolean drawnFlag = false;

    private String hero1CurrentFilepath;
    private String hero2CurrentFilepath;

//    private HeroModel worm;

    private void start() {
        new Thread(this).start();
    }


    public void run() {
        long lastTime = System.currentTimeMillis();
        long delta;

        init();

        while (true) {
            delta = System.currentTimeMillis() - lastTime;
            lastTime = System.currentTimeMillis();
            render();
            update(delta);
        }
    }

    private GameBackground getBack(String path) {
        BufferedImage sourceImage = null;
        try {
            URL url = this.getClass().getResource(path);
            sourceImage = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        GameBackground img = new GameBackground(Toolkit.getDefaultToolkit().createImage(sourceImage.getSource()));
        return img;
    }

    public Sprite getSprite(String path) {
        BufferedImage sourceImage = null;
        try {
            URL url = this.getClass().getResource(path);
            sourceImage = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Sprite(Toolkit.getDefaultToolkit().createImage(sourceImage.getSource()));
    }

    public Bullet getBulletImage(String path) {
        BufferedImage sourceImage = null;
        try {
            URL url = this.getClass().getResource(path);
            sourceImage = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Bullet(Toolkit.getDefaultToolkit().createImage(sourceImage.getSource()));
    }

    private Aim getAimImage(String path) {
        BufferedImage sourceImage = null;
        try {
            URL url = this.getClass().getResource(path);
            sourceImage = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Aim img = new Aim(Toolkit.getDefaultToolkit().createImage(sourceImage.getSource()));
        return img;
    }

    private void init() {
        addKeyListener(new KeyInputHandler());
        hero1CurrentFilepath = "assets/player1.png";
        hero2CurrentFilepath = "assets/player2.png";
        hero1 = getSprite(hero1CurrentFilepath);
        hero2 = getSprite(hero2CurrentFilepath);
        background = getBack("assets/background.png");
        bullet = getBulletImage("assets/bullet.png");
        aim = getAimImage("assets/aim.png");
//        worm = new HeroModel(new Rectangle(200,200, 30, 70), "assets/player1.png");
    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            requestFocus();
            return;
        }

        Graphics g = bs.getDrawGraphics();
        if (!drawnFlag) {
            background.draw(g, 0, 0);
            drawnFlag = true;
        }
        hero1.draw(g, (int) player1_x, (int) player1_y);
        hero2.draw(g, (int) player2_x, (int) player2_y);
        if (KeyInputHandler.aimFlag) {
            if ((currentHero1) && (hero1CurrentFilepath.equals("assets/player1.png"))) {
                aim.draw(g, (int) player1_x + distance, (int) player1_y);
            } else if ((currentHero1) && (hero1CurrentFilepath.equals("assets/player1_reversed.png"))) {
                aim.draw(g, (int) player1_x - distance, (int) player1_y);
            } else if ((!currentHero1) && (hero2CurrentFilepath.equals("assets/player2.png"))) {
                aim.draw(g, (int) player2_x - distance, (int) player2_y);
            } else {
                aim.draw(g, (int) player2_x + distance, (int) player2_y);
            }
        }
        if (bulletFlyingFlag) {
            bullet.draw(g, bull_x, bull_y);
        }
        g.dispose();
        bs.show();
    }

    private void update(long delta) {
        if (KeyInputHandler.leftPressed) {
            if ((!KeyInputHandler.aimFlag) && (!bulletFlyingFlag)) {
                if (currentHero1) {
                    if (hero1CurrentFilepath.equals("assets/player1.png")) {
                        hero1 = getSprite("assets/player1_reversed.png");
                        hero1CurrentFilepath = "assets/player1_reversed.png";
                    }
                    if (player1_x >= 0) {
                        player1_x = player1_x - 0.25;
                    }
                } else {
                    if (hero2CurrentFilepath.equals("assets/player2_reversed.png")) {
                        hero2 = getSprite("assets/player2.png");
                        hero2CurrentFilepath = "assets/player2.png";
                    }
                    if (player2_x >= 0) {
                        player2_x = player2_x - 0.25;
                    }
                }
            } else if (KeyInputHandler.aimFlag) {
                if (((currentHero1) && (hero1CurrentFilepath.equals("assets/player1_reversed.png"))) ||
                        ((!currentHero1) && (hero2CurrentFilepath.equals("assets/player2.png")))) {
                    if (distance <= 400) {
                        distance += 5;
                    }
                } else {
                    if (distance >= 50) {
                        distance -= 5;
                    }
                }
            }
        }
        if (KeyInputHandler.rightPressed) {
            if ((!KeyInputHandler.aimFlag) && (!bulletFlyingFlag)) {
                if (currentHero1) {
                    if (hero1CurrentFilepath.equals("assets/player1_reversed.png")) {
                        hero1 = getSprite("assets/player1.png");
                        hero1CurrentFilepath = "assets/player1.png";
                    }
                    if (player1_x <= WIDTH - 19) {
                        player1_x += 0.25;
                    }
                } else {
                    if (hero2CurrentFilepath.equals("assets/player2.png")) {
                        hero2 = getSprite("assets/player2_reversed.png");
                        hero2CurrentFilepath = "assets/player2_reversed.png";
                    }
                    if (player2_x <= WIDTH - 19) {
                        player2_x += 0.25;
                    }
                }
            } else if (KeyInputHandler.aimFlag) {
                if (((currentHero1) && (hero1CurrentFilepath.equals("assets/player1.png"))) ||
                        ((!currentHero1) && (hero2CurrentFilepath.equals("assets/player2_reversed.png")))) {
                    if (distance <= 400) {
                        distance += 5;
                    }
                } else {
                    if (distance >= 50) {
                        distance -= 5;
                    }
                }
            }
        }
        if (KeyInputHandler.shotFlag) {
            if (currentHero1) {
                bull_x = (int) player1_x;
                bull_y = (int) player1_y;
            } else {
                bull_x = (int) player2_x;
                bull_y = (int) player2_y;
            }
            KeyInputHandler.shotFlag = false;
            bulletFlyingFlag = true;
        }
        if (bulletFlyingFlag) {
            if ((currentHero1) && (hero1CurrentFilepath.equals("assets/player1.png"))) {
                bull_x += Bullet.speed;
                bull_y = (int) player1_y + ((bull_x - (int) player1_x - distance / 2) ^ 2) - ((distance / 2) ^ 2);
            } else if ((currentHero1) && (hero1CurrentFilepath.equals("assets/player1_reversed.png"))) {
                bull_x -= Bullet.speed;
                bull_y = (int) player1_y + (((int) player1_x - bull_x - distance / 2) ^ 2) - ((distance / 2) ^ 2);
            } else if ((!currentHero1) && (hero2CurrentFilepath.equals("assets/player2.png"))) {
                bull_x -= Bullet.speed;
                bull_y = (int) player2_y + (((int) player2_x - bull_x - distance / 2) ^ 2) - ((distance / 2) ^ 2);
            } else {
                bull_x += Bullet.speed;
//                bull_y = (int) player2_y + ((bull_x - (int) player2_x * 2 - distance / 2) ^ 2) -
//                        ((distance / 2 + (int) player2_x) ^ 2 + 2 * (bull_x - (int) player2_x) * (int) player2_x);
                bull_y = (int) player2_y + ((bull_x - (int) player2_x - distance / 2) ^ 2) - ((distance / 2) ^ 2);
//                bull_y = (int) player2_y + (((distance*(bull_x - (int)player2_x))^2 - 400*(bull_x - (int)player2_x))*distance^2)/160000;
//                bull_y = (int) player2_y - (((distance)^2 * (bull_x - (int)player2_x))/400) + ((distance*(bull_x - (int)player2_x))/400)^2;
            }
            bulletFlyCounter++;
            if (bulletFlyCounter == distance) {
                drawnFlag = false;
                bulletFlyCounter = 0;
                currentHero1 = !currentHero1;
                bulletFlyingFlag = false;
            }
        }
        if (KeyInputHandler.jumpFlag) {
            flyingFlag = true;
            KeyInputHandler.jumpFlag = false;
        }
        if (flyingFlag) {
            if (currentHero1) {
                player1_y = player1_y - 2;
                flyCounter++;
                if (flyCounter == 30) {
                    flyingFlag = false;
                    fallingFlag = true;
                    flyCounter = 0;
                }
            } else {
                player2_y = player2_y - 2;
                flyCounter++;
                if (flyCounter == 30) {
                    flyingFlag = false;
                    fallingFlag = true;
                    flyCounter = 0;
                }
            }
        }
        if (fallingFlag) {
            if (currentHero1) {
                player1_y = player1_y + 2;
                fallCounter++;
                if (fallCounter == 30) {
                    fallingFlag = false;
                    fallCounter = 0;
                }
            } else {
                player2_y = player2_y + 2;
                fallCounter++;
                if (fallCounter == 30) {
                    fallingFlag = false;
                    fallCounter = 0;
                }
            }
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        JFrame frame = new JFrame(Game.NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(game, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        game.start();
    }
}