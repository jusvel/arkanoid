package com.game.arkanoid.objects.factories;

import com.game.arkanoid.objects.Paddle;
import com.game.arkanoid.objects.GameObject;

public class PaddleFactory implements GameObjectFactory {
    private double speed;

    public PaddleFactory(double speed){
        this.speed = speed;
    }

    @Override
    public GameObject createGameObject(double x, double y) {
        return new Paddle(x, y, speed);
    }
}
