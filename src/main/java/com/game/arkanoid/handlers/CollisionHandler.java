package com.game.arkanoid.handlers;

import com.game.arkanoid.objects.Ball;
import com.game.arkanoid.objects.Paddle;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import static com.game.arkanoid.helpers.JavaFXUtils.showTextOnScreen;

public class CollisionHandler {
    public static void checkCollisions(Ball ball, Paddle paddle, Pane gamePane, Label scoreLabel, Timeline gameLoop){
        Bounds ballBounds = ball.getCircle().getBoundsInParent();
        Bounds paddleBounds = paddle.getRectangle().getBoundsInParent();

        handleBallPaddleCollision(ball, ballBounds, paddleBounds);
        handleBallWallCollision(ball, gamePane);
        handleBallBrickCollision(ball, ballBounds, gamePane, scoreLabel);
        handleLoss(ball, gamePane, gameLoop);
        handleWin(gamePane, gameLoop);
    }

    private static void handleBallPaddleCollision(Ball ball, Bounds ballBounds, Bounds paddleBounds) {
        if (checkBallPaddleCollision(ballBounds, paddleBounds)) {
            double ballCenterX = ball.getCircle().getCenterX();
            double paddleCenterX = paddleBounds.getMinX() + paddleBounds.getWidth() / 2;

            double relativeIntersectX = (paddleCenterX - ballCenterX) / (paddleBounds.getWidth() / 2);

            double bounceAngle = relativeIntersectX * Math.toRadians(60);

            double speed = Math.sqrt(ball.getDx() * ball.getDx() + ball.getDy() * ball.getDy());

            double newVelocityX = speed * Math.sin(bounceAngle);
            double newVelocityY = -speed * Math.cos(bounceAngle);

            ball.setVelocity(newVelocityX, newVelocityY);
        }
    }

    private static void handleBallWallCollision(Ball ball, Pane gamePane) {
        double ballCenterX = ball.getCircle().getCenterX();
        double ballCenterY = ball.getCircle().getCenterY();
        double ballRadius = ball.getRadius();

        if (ballCenterX <= ballRadius || ballCenterX >= gamePane.getWidth() - ballRadius) {
            ball.setVelocity(-ball.getDx(), ball.getDy());
        }
        if (ballCenterY <= ballRadius) {
            ball.setVelocity(ball.getDx(), -ball.getDy());
        }
    }

    private static void handleBallBrickCollision(Ball ball, Bounds ballBounds, Pane gamePane, Label scoreLabel) {
        for (Node brick : gamePane.getChildren()) {
            if (brick.getId() != null && brick.getId().equals("brick") && ballBounds.intersects(brick.getBoundsInParent())) {
                handleBrickCollision(ball, gamePane, scoreLabel, brick);
                break;
            }
        }
    }

    private static void handleBrickCollision(Ball ball, Pane gamePane, Label scoreLabel, Node brick) {
        gamePane.getChildren().remove(brick);
        ball.setVelocity(ball.getDx(), -ball.getDy());
        updateScore(scoreLabel);
    }

    private static void updateScore(Label scoreLabel) {
        int score = Integer.parseInt(String.valueOf(scoreLabel.getText().split(" ")[1]));
        score++;
        scoreLabel.setText("Score: " + score);
    }

    private static void handleLoss(Ball ball, Pane gamePane, Timeline gameLoop) {
        if (ball.getCircle().getCenterY() > gamePane.getHeight()) {
            gameLoop.stop();
            showTextOnScreen(gamePane, "You lost, press q", Color.RED);
        }
    }

    private static void handleWin(Pane gamePane, Timeline gameLoop) {
        if (gamePane.getChildren().size() == 2) {
            gameLoop.stop();
            showTextOnScreen(gamePane, "You won, press q", Color.LIGHTGREEN);
        }
    }


    public static boolean checkBallPaddleCollision(Bounds ballBounds, Bounds paddleBounds) {
        return ballBounds.intersects(paddleBounds);
    }
}
