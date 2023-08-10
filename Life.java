package com.example;

import java.awt.*;

public class Life  extends Rectangle {

    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    int life = 3;
    public Life(int GAME_WIDTH, int GAME_HEIGHT) {

        Life.GAME_WIDTH = GAME_WIDTH;
        Life.GAME_HEIGHT = GAME_HEIGHT;
    }

    public void draw(Graphics g) {

        g.setColor(new Color(18, 136, 9, 255));
        g.setFont(new Font("Tsuki Typeface", Font.PLAIN, 25));
        g.drawString("LIVES: " + String.valueOf(life), (GAME_WIDTH/2-50), (GAME_HEIGHT*9)/10-50);
    }
}
