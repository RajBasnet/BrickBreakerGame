package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GamePanel extends JPanel implements Runnable {

    static final int GAME_WIDTH = 700;
    static final int GAME_HEIGHT = 600;
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 100;
    static final int PADDLE_HEIGHT = 20;
    static final int BRICK_WIDTH = 50;
    static final int BRICK_HEIGHT = 25;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle;
    Ball ball;
    List<Brick> bricks = new ArrayList<>();
    Score score;
    Life life;

    public GamePanel() {

        newPaddle();
        newBall();
        newBricks();
        score = new Score(GAME_WIDTH, GAME_HEIGHT);
        life = new Life(GAME_WIDTH,GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new ActionListener());
        this.setPreferredSize(SCREEN_SIZE);
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void newPaddle() {

        paddle = new Paddle(GAME_WIDTH/2-PADDLE_WIDTH/2, GAME_HEIGHT-PADDLE_HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT);
    }

    public void newBall() {

        ball = new Ball(GAME_WIDTH/2-BALL_DIAMETER/2,GAME_HEIGHT-BALL_DIAMETER-PADDLE_HEIGHT,BALL_DIAMETER,BALL_DIAMETER);
    }

    public void newBricks() {

        int  i = 0;
        int xP = 1;
        int yP = 1;
        int id = 1;

        while(true) {
            //Creating arraylist of bricks to store
            bricks.add(new Brick(xP, yP, BRICK_WIDTH, BRICK_HEIGHT, id));
            id++;
            if (id > 3) {
                id = 1;
            }

            xP = xP + BRICK_WIDTH + 4; //Will always have 13 breaks per one line for this width
            if(xP < GAME_WIDTH) {
                continue;

            }
            xP = 1;
            if (yP <= GAME_HEIGHT/3) { //3 determines the height of bricks to build as well as bricks count
                yP = yP + BRICK_HEIGHT + 4;
                continue;
            }
            break;
        }
    }

    public void paint(Graphics g) {

        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);


    }

    public void draw(Graphics g) {

        paddle.draw(g);
        ball.draw(g);
        int i = 0;
        while(i < bricks.size()) {
            bricks.get(i).draw(g);
            i++;
        }
        score.draw(g);
        life.draw(g);

    }

    public void move(){

        paddle.move();
        ball.move();
    }

    public void checkCollision() {

        //check collision for paddle two side edges
        if(paddle.x <= 0) {
            paddle.x = 0;
        }

        if(paddle.x >= (GAME_WIDTH-PADDLE_WIDTH)) {
            paddle.x = GAME_WIDTH-PADDLE_WIDTH;
        }

        //check collision of ball with two sides and only top
        if(ball.x <= 2) {
            ball.setXDirection(-ball.xVelocity);
        }

        if(ball.x >= GAME_WIDTH-BALL_DIAMETER-2) {
            ball.setXDirection(-ball.xVelocity);
        }

        if(ball.y <= 2) {
            ball.setYDirection(-ball.yVelocity);
        }

        //check collision with bricks
        random = new Random();
        int i = 0;
        while(i < bricks.size()) {
            if(ball.intersects(bricks.get(i))) {
                bricks.remove(i);
                score.score++;
                int randomXDirection = random.nextInt(2);
                if(randomXDirection == 0) {
                    randomXDirection--;
                }
                int randomYDirection = random.nextInt(2);
                if(randomYDirection == 0) {
                    randomYDirection--;
                }
                ball.setXDirection(-ball.xVelocity*randomXDirection);
                ball.setYDirection(-ball.yVelocity*randomYDirection);
            }
            i++;
        }

        //Check collision with paddle
        random = new Random();
        if(ball.intersects(paddle)) {
            int randomXDirection = random.nextInt(2);
            if(randomXDirection == 0) {
                randomXDirection--;
            }

            ball.setXDirection(randomXDirection*ball.initialSpeed);
            ball.setYDirection(-ball.initialSpeed);
        }

        //Check collision with bottom
        if(ball.y >= GAME_HEIGHT-BALL_DIAMETER/2) {
            newPaddle();
            newBall();
            life.life--;
        }

        if(life.life <= 0 || bricks.size() <= 0) {

            bricks.removeAll(bricks);
            life = new Life(GAME_WIDTH,GAME_HEIGHT);
            newBall();
            newPaddle();
            newBricks();
            score.prevScore = score.score;
            score.score = 0;
        }
    }

    public void run() {

        //game loop
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000/amountOfTicks;
        double delta = 0;

        while(true) {

            long now = System.nanoTime();
            delta += (now - lastTime)/ns;
            lastTime = now;

            if(delta >= 1) {
                move();
                checkCollision();
                repaint();
                delta--;
            }
        }
    }

    public class ActionListener extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            if(ball.xVelocity != 0 || ball.yVelocity != 0) {
                paddle.keyPressed(e);
            }

            if(ball.xVelocity == 0 && ball.yVelocity == 0) {
                ball.keyPressed(e);
            }
        }

        public void keyReleased(KeyEvent e) {

            paddle.keyReleased(e);
        }
    }
}
