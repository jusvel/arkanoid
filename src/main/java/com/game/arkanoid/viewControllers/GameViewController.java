package com.game.arkanoid.viewControllers;

import com.game.arkanoid.Ball;
import com.game.arkanoid.CollisionHandler;
import com.game.arkanoid.Paddle;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class GameViewController {

    //TODO add pause button
    //TODO add game over screen
    //TODO add win screen
    //TODO add score
    //TODO add paddle bounds
    //TODO quit button
    //TODO refactor check collisions



    @FXML
    public Pane gamePane;
    @FXML
    public Label scoreLabel;

    private Paddle paddle;
    private Ball ball;

    private int score = 1;

    Timeline gameLoop;

    private boolean rightKeyPressed = false;
    private boolean leftKeyPressed = false;

    public void initialize(int level) {
        addBricksToGamePane(level);
        setUpPaddle();
        setUpBall();

        startGameLoop();
    }

    public void startGameLoop() {
        gameLoop = new Timeline(new KeyFrame(Duration.millis(5), e -> {moveBall();}));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }

    public void moveBall() {
        ball.move();
        CollisionHandler.checkCollisions(ball, paddle, gamePane, scoreLabel, score);
    }

    private void setUpBall() {
        ball = new Ball(260, 350, 10);
        gamePane.getChildren().add(ball.getCircle());
        ball.setVelocity(0, 3);
    }

    private void setUpPaddle() {
        paddle = new Paddle(205, 660, 3.0);
        gamePane.getChildren().add(paddle.getRectangle());
        addControlsToPaddle();
    }


    private void addControlsToPaddle() {
        Scene scene = gamePane.getScene();

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.RIGHT) rightKeyPressed = true;
            else if (event.getCode() == KeyCode.LEFT) leftKeyPressed = true;
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.RIGHT) rightKeyPressed = false;
            else if (event.getCode() == KeyCode.LEFT) leftKeyPressed = false;
        });
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                movePaddle();
            }
        }.start();
    }

    public void movePaddle() {
        if (rightKeyPressed) paddle.getRectangle().setLayoutX(paddle.getRectangle().getLayoutX() + paddle.getSpeed());
        else if (leftKeyPressed) paddle.getRectangle().setLayoutX(paddle.getRectangle().getLayoutX() - paddle.getSpeed());
    }

    private void addBricksToGamePane(int rows) {
        for (int i = 0; i < rows*2; i++) {
            for (int j = 0; j < 5; j++) {
                Rectangle brick = new Rectangle((107 * j)+5, (35 * i)+5, 102, 30);
                brick.setFill(Color.WHEAT);
                brick.setId("brick");
                gamePane.getChildren().add(brick);
            }
        }
    }
}
