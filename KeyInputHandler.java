package ru.spbu.apmath.prog.project;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInputHandler extends KeyAdapter {

    public static boolean leftPressed = false;
    public static boolean rightPressed = false;
    public static boolean jumpFlag = false;
    public static boolean aimFlag = false;
    public static boolean shotFlag = false;

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            jumpFlag = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            aimFlag = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            aimFlag = false;
            shotFlag = true;
        }
    }
}