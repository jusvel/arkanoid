package com.game.arkanoid;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball {
    private double x, y;
    private double dx, dy;

    private double radius;
    private Circle circle;

    public Ball(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        circle = new Circle(x, y, radius, Color.BLACK);
    }

    public Circle getCircle() {
        return circle;
    }

    public void move() {
        x += dx;
        y += dy;
        circle.setCenterX(x);
        circle.setCenterY(y);
    }

    public void setVelocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public double getRadius() {
        return radius;
    }
}