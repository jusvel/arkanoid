package com.game.arkanoid.objects;

import javafx.scene.shape.Rectangle;

import static com.game.arkanoid.helpers.Constants.PADDLE_HEIGHT;
import static com.game.arkanoid.helpers.Constants.PADDLE_WIDTH;

public class Paddle extends GameObject {
    private double speed;
    private final Rectangle rectangle;

    public Paddle(double x, double y, double speed) {
        super(x, y);
        this.speed = speed;
        rectangle = new Rectangle(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed){
        this.speed = speed;
    }

    @Override
    public void move() {
        x += speed;
        rectangle.setLayoutX(x);
    }
}
