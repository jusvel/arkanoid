package com.game.arkanoid.objects.factories;

import com.game.arkanoid.objects.GameObject;

public interface GameObjectFactory {
    GameObject createGameObject(double x, double y);
}
