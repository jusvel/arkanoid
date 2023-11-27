package com.game.arkanoid;

import com.game.arkanoid.objects.Ball;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BallTest {

    @Test
    public void testBallMovement(){
        Ball ball = new Ball(100, 100, 10);
        ball.setVelocity(5, 10);

        double initialBallX = ball.getX();
        double initialBallY = ball.getY();

        ball.move();
        ball.move();

        double expectedBallX = initialBallX + ball.getDx() + ball.getDx();
        double expectedBallY = initialBallY + ball.getDy() + ball.getDy();

        assertEquals(expectedBallX, ball.getX(),"Ball X coordinate should be equal to " + expectedBallX);
        assertEquals(expectedBallY, ball.getY(),"Ball Y coordinate should be equal to " + expectedBallY);

    }
}
