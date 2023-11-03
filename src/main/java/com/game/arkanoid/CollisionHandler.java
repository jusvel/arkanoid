package com.game.arkanoid;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class CollisionHandler {
    public static void checkCollisions(Ball ball, Paddle paddle, Pane gamePane, Label scoreLabel, int score){
        if (ball.getCircle().getBoundsInParent().intersects(paddle.getRectangle().getBoundsInParent())) handleBallPaddleCollision(ball, paddle);
        handleBallWallCollision(ball, gamePane);
        handleBallBrickCollision(ball, gamePane, scoreLabel, score);
        handleLoss(ball, gamePane);
        handleWin(gamePane);
    }

    private static void handleBallPaddleCollision(Ball ball, Paddle paddle) {
        Bounds ballBounds = ball.getCircle().getBoundsInParent();
        Bounds paddleBounds = paddle.getRectangle().getBoundsInParent();

        if (ballBounds.intersects(paddleBounds)) {
            double ballCenterX = ballBounds.getMinX() + ballBounds.getWidth() / 2;
            double paddleCenterX = paddleBounds.getMinX() + paddleBounds.getWidth() / 2;

            double relativeIntersectX = (paddleCenterX - ballCenterX) / (paddleBounds.getWidth() / 2);
            double maxBounceAngle = Math.toRadians(60);

            double bounceAngle = relativeIntersectX * maxBounceAngle;

            double speed = Math.sqrt(ball.getDx() * ball.getDx() + ball.getDy() * ball.getDy());

            double newVelocityX = speed * Math.sin(bounceAngle);
            double newVelocityY = -speed * Math.cos(bounceAngle);

            ball.setVelocity(newVelocityX, newVelocityY);
        }
    }

    private static void handleBallWallCollision(Ball ball, Pane gamePane) {
        if (ball.getCircle().getCenterX() <= ball.getRadius() ||
                ball.getCircle().getCenterX() >= gamePane.getWidth() - ball.getRadius()) {
            ball.setVelocity(-ball.getDx(), ball.getDy());
        }

        if (ball.getCircle().getCenterY() <= ball.getRadius()) {
            ball.setVelocity(ball.getDx(), -ball.getDy());
        }
    }

    private static void handleBallBrickCollision(Ball ball, Pane gamePane, Label scoreLabel, int score) {
        for (Node brick : gamePane.getChildren()) {
            if (brick.getId() != null && brick.getId().equals("brick") && ball.getCircle().getBoundsInParent().intersects(brick.getBoundsInParent())) {
                System.out.println("Collision detected");
                gamePane.getChildren().remove(brick);
                ball.setVelocity(ball.getDx(), -ball.getDy());
                scoreLabel.setText("Score: " + (score++));
                break;
            }
        }
    }

    private static void handleLoss(Ball ball, Pane gamePane) {

    }

    private static void handleWin(Pane gamePane) {

    }
}
