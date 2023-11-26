package com.game.arkanoid.objects;

import javafx.scene.shape.Rectangle;

public class Paddle extends GameObject{
    private final double speed;
    private final Rectangle rectangle;

    public Paddle(double x, double y, double speed) {
        super(x, y);
        this.speed = speed;
        rectangle = new Rectangle(x, y, 130, 20);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public double getSpeed() {
        return speed;
    }

    @Override
    public void move() {

    }
}
