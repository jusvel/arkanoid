package com.game.arkanoid;

import com.game.arkanoid.objects.Paddle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaddleTest {

    @Test
    public void testPaddleMovement(){
        Paddle paddle = new Paddle(100, 100, 10);
        paddle.setSpeed(10);

        double initialPaddleX = paddle.getX();

        paddle.move();

        double expectedPaddleX = initialPaddleX + paddle.getSpeed();

        assertEquals(expectedPaddleX, paddle.getX(),"Paddle X coordinate should be equal to " + expectedPaddleX);

        paddle.setSpeed(-10);
        paddle.move();
        expectedPaddleX = expectedPaddleX + paddle.getSpeed();
        assertEquals(expectedPaddleX, paddle.getX(),"Paddle X coordinate should be equal to " + expectedPaddleX);

    }
}
