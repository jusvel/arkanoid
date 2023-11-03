package com.game.arkanoid;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class GameViewController implements Initializable {

    @FXML
    public Rectangle paddle;
    @FXML
    public Pane gamePane;
    public Label scoreLabel;


    private Ball ball;
    private int score = 1;

    Timeline gameLoop;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ball = new Ball(250, 350, 5);
        gamePane.getChildren().add(ball.getCircle());
        System.out.println("a");

        ball.setVelocity(0, 1);

        startGameLoop();
    }

    public void startGameLoop() {
        gameLoop = new Timeline(new KeyFrame(Duration.millis(5), e -> {
            moveBall();

        }));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }

    public void moveBall() {
        System.out.println((int)ball.getCircle().getCenterX() + " " + (int)ball.getCircle().getCenterY());
        ball.move();
        checkCollisions();
    }

    public void checkCollisions() {
        if (ball.getCircle().getBoundsInParent().intersects(paddle.getBoundsInParent())) {
            handlePaddleCollision();
        }

        if (ball.getCircle().getCenterX() <= ball.getRadius() ||
                ball.getCircle().getCenterX() >= gamePane.getWidth() - ball.getRadius()) {
            ball.setVelocity(-ball.getDx(), ball.getDy());
        }

        if (ball.getCircle().getCenterY() <= ball.getRadius()) {
            ball.setVelocity(ball.getDx(), -ball.getDy());
        }

            handleBrickCollision();

        if (ball.getCircle().getCenterY() > gamePane.getHeight()) {
            gameLoop.stop();
            Text lossText;
            lossText = new Text("loooooooseerrrr!");
            lossText.setFont(Font.font("Arial", FontWeight.BOLD, 30));
            lossText.setFill(Color.RED);
            lossText.setVisible(true);
            lossText.setX((gamePane.getWidth() - lossText.getLayoutBounds().getWidth()) / 2);
            lossText.setY((gamePane.getHeight() - lossText.getLayoutBounds().getHeight()) / 2);
            gamePane.getChildren().add(lossText);
        }
    }

    public void handlePaddleCollision() {
        Bounds ballBounds = ball.getCircle().getBoundsInParent();
        Bounds paddleBounds = paddle.getBoundsInParent();

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

    public void handleBrickCollision() {

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

    public void movePaddleLeft() {
        paddle.setLayoutX(paddle.getLayoutX() - 10);
    }
    public void movePaddleRight() {
        paddle.setLayoutX(paddle.getLayoutX() + 10);
    }

}
