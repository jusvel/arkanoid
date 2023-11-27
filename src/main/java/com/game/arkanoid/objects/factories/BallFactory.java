package com.game.arkanoid.objects.factories;

import com.game.arkanoid.objects.Ball;
import com.game.arkanoid.objects.GameObject;

public class BallFactory implements GameObjectFactory {
    private double radius;

    public BallFactory(double radius){
        this.radius = radius;
    }

    @Override
    public GameObject createGameObject(double x, double y) {
        return new Ball(x, y, radius);
    }
}
