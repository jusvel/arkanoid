package com.game.arkanoid;

import javafx.scene.shape.Rectangle;

public class Paddle {
    private double x, y;
    private double speed;
    private Rectangle rectangle;

    public Paddle(double x, double y,double speed) {
        this.x = x;
        this.y = y;
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
