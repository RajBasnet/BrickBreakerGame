package com.example;

import java.awt.*;

public class Brick extends Rectangle{

    int id;
    public Brick(int x, int y, int BRICK_WIDTH, int BRICK_HEIGHT, int id) {

        super(x,y,BRICK_WIDTH,BRICK_HEIGHT);
        this.id = id;
    }

    public void draw(Graphics g) {

        if(id == 1) {
            g.setColor(new Color(255, 5, 5));
            g.fillRect(x, y, width, height);
        }

        if(id == 2) {

            g.setColor(new Color(189, 2, 52));
            g.fillRect(x, y, width, height);
        }

        if(id == 3) {

            g.setColor(new Color(136, 135, 133));
            g.fillRect(x, y, width, height);
        }
    }
}
