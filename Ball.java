package com.example;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Ball extends Rectangle {

    Random random;
    int xVelocity;
    int yVelocity;
    int initialSpeed = 5;

    public Ball(int x, int y, int width, int height) {

        super(x,y,width,height);
        random = new Random();
    }

    public void keyPressed(KeyEvent e) {

        int randomXDirection = random.nextInt(2);
        if(randomXDirection == 0) {
            randomXDirection--;
        }

        int randomYDirection = random.nextInt(2);
        if(randomYDirection == 0) {
            randomYDirection++;
        }

        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            System.out.println(randomXDirection*initialSpeed);
            System.out.println(-randomYDirection*initialSpeed);
            setXDirection(randomXDirection*initialSpeed);
            setYDirection(-randomYDirection*initialSpeed);
        }
    }

    public void setXDirection(int randomXDirection) {

        xVelocity = randomXDirection;
    }

    public void setYDirection(int randomYDirection) {

        yVelocity = randomYDirection;
    }

    public void move() {

        x += xVelocity;
        y += yVelocity;
    }

    public void draw(Graphics g) {

        g.setColor(Color.black);
        g.fillOval(x,y,width,height);
    }
}
