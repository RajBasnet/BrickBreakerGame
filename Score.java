package com.example;

import java.awt.*;

public class Score extends Rectangle {

    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    int score;
    int prevScore;

    public Score(int GAME_WIDTH, int GAME_HEIGHT) {

        Score.GAME_WIDTH = GAME_WIDTH;
        Score.GAME_HEIGHT = GAME_HEIGHT;
    }

    public void draw(Graphics g) {

        g.setColor(Color.white);
        g.setFont(new Font("Tsuki Typeface", Font.PLAIN, 25));
        g.drawString("SCORE: " + score, (GAME_WIDTH/2)-65, (GAME_HEIGHT*9)/10-25);

        g.setColor(Color.BLUE);
        g.setFont(new Font("Tsuki Typeface", Font.PLAIN, 25));
        g.drawString("Previous SCORE: " + prevScore, (GAME_WIDTH/2)-100, (GAME_HEIGHT*9)/10);
    }
}
