package com.game.arkanoid.viewControllers;

import com.game.arkanoid.Ball;
import com.game.arkanoid.CollisionHandler;
import com.game.arkanoid.LaunchGUI;
import com.game.arkanoid.Paddle;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameViewController {
    //TODO add score
    @FXML
    public Pane gamePane;
    @FXML
    public Label scoreLabel;
    @FXML
    Label countDownLabel;

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
        setUpKeybinds();

        setUpCountDownLabel();

        countDownThenStartGame();
    }


    private void countDownThenStartGame() {
        Timeline countDown = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            int count = Integer.parseInt(countDownLabel.getText());
            count--;
            countDownLabel.setText(String.valueOf(count));
        }));
        countDown.setCycleCount(3);
        countDown.setOnFinished(e -> {
            gamePane.getChildren().remove(countDownLabel);
            startGameLoop();
        });
        countDown.play();
    }


    public void startGameLoop() {
        gameLoop = new Timeline(new KeyFrame(Duration.millis(5), e -> {moveBall();}));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }

    public void moveBall() {
        ball.move();
        CollisionHandler.checkCollisions(ball, paddle, gamePane, scoreLabel, score, gameLoop);
    }

    private void setUpBall() {
        ball = new Ball(260, 350, 10);
        gamePane.getChildren().add(ball.getCircle());
        ball.setVelocity(0, 3);
    }

    private void setUpPaddle() {
        paddle = new Paddle(205, 660, 3.0);
        gamePane.getChildren().add(paddle.getRectangle());
    }


    private void setUpKeybinds() {
        Scene scene = gamePane.getScene();

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.RIGHT) {
                rightKeyPressed = true;
            } else if (event.getCode() == KeyCode.LEFT) {
                leftKeyPressed = true;
            } else if(event.getCode() == KeyCode.P){
                if(gameLoop.getStatus() == Timeline.Status.RUNNING){
                    gameLoop.stop();
                } else {
                    gameLoop.play();
                }
            } else if(event.getCode() == KeyCode.Q){
                loadMainMenu();
            }
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
        Rectangle paddleRectangle = paddle.getRectangle();
        double paddleX = paddleRectangle.getLayoutX();
        double paddleWidth = paddleRectangle.getWidth();

        if (rightKeyPressed && (paddleX + paddleWidth) < gamePane.getWidth()-205) {
            paddleRectangle.setLayoutX(paddleX + paddle.getSpeed());
        } else if (leftKeyPressed && paddleX > -205) {
            paddleRectangle.setLayoutX(paddleX - paddle.getSpeed());
        }
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

    private void setUpCountDownLabel() {
        countDownLabel = new Label("3");
        countDownLabel.setLayoutX(240);
        countDownLabel.setLayoutY(350);
        countDownLabel.setStyle("-fx-font-size: 100px; -fx-font-weight: bold; -fx-text-fill: red;");
        gamePane.getChildren().add(countDownLabel);
    }

    private void loadMainMenu() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LaunchGUI.class.getResource("mainMenuView.fxml"));
            Scene menuScene = new Scene(fxmlLoader.load(), 560, 800);
            Stage stage = (Stage) gamePane.getScene().getWindow();
            stage.setTitle("Arkanoid");
            stage.setScene(menuScene);
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e){
            System.out.println("Failed to load gameView.fxml");
            e.printStackTrace();
        }
    }
}
