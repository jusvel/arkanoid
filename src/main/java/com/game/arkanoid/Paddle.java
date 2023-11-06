package com.game.arkanoid;

import javafx.scene.shape.Rectangle;

public class Paddle {
    private final double speed;
    private final Rectangle rectangle;

    public Paddle(double x, double y,double speed) {
        this.speed = speed;
        rectangle = new Rectangle(x, y, 130, 20);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public double getSpeed() {
        return speed;
    }
}
