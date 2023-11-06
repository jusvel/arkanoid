package com.game.arkanoid;

import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

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
        if (ballBounds.intersects(paddleBounds)) {
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
        int score = Integer.parseInt(String.valueOf(scoreLabel.getText().charAt(scoreLabel.getText().length()-1)));
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

    private static void showTextOnScreen(Pane gamePane, String text, Color color) {
        Text lossText;
        lossText = new Text(text);
        lossText.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        lossText.setFill(color);
        lossText.setVisible(true);
        lossText.setX((gamePane.getWidth() - lossText.getLayoutBounds().getWidth()) / 2);
        lossText.setY((gamePane.getHeight() - lossText.getLayoutBounds().getHeight()) / 2);
        gamePane.getChildren().add(lossText);
    }
}
