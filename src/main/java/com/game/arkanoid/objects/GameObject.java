package com.game.arkanoid.objects;

public abstract class GameObject {
    protected double x, y;

    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public abstract void move();

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
