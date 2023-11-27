package com.game.arkanoid;

import com.game.arkanoid.handlers.CollisionHandler;
import com.game.arkanoid.objects.Ball;
import com.game.arkanoid.objects.Paddle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CollisionHandlerTest {

    @Test
    public void testBallPaddleCollision(){
        Ball ball = new Ball(95, 90, 10);
        ball.setVelocity(5, 10);
        Paddle paddle = new Paddle(100, 100, 10);
        ball.move();

        boolean collision = CollisionHandler.checkBallPaddleCollision(ball.getCircle().getBoundsInParent(), paddle.getRectangle().getBoundsInParent());

        assertTrue(collision, "Ball should collide with paddle");
    }
}
