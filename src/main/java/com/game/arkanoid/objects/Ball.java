package com.game.arkanoid.objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball extends GameObject{
    private double dx, dy;

    private final double radius;
    private final Circle circle;

    public Ball(double x, double y, double radius) {
        super(x, y);
        this.radius = radius;
        circle = new Circle(x, y, radius, Color.WHITE);
    }

    public Circle getCircle() {
        return circle;
    }

    @Override
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