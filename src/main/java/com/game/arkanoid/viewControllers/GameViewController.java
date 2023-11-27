package com.game.arkanoid.viewControllers;

import com.game.arkanoid.gameStates.GameState;
import com.game.arkanoid.gameStates.PausedState;
import com.game.arkanoid.gameStates.PlayingState;
import com.game.arkanoid.handlers.CollisionHandler;
import com.game.arkanoid.helpers.JavaFXUtils;
import com.game.arkanoid.objects.Ball;
import com.game.arkanoid.objects.Paddle;
import com.game.arkanoid.objects.factories.BallFactory;
import com.game.arkanoid.objects.factories.GameObjectFactory;
import com.game.arkanoid.objects.factories.PaddleFactory;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static com.game.arkanoid.helpers.Constants.*;

public class GameViewController {
    @FXML
    public Pane gamePane;
    @FXML
    public Label scoreLabel;
    @FXML
    Label countDownLabel;

    private GameState gameState;
    private GameObjectFactory ballFactory;
    private GameObjectFactory paddleFactory;
    private Paddle paddle;
    private Ball ball;

    public Timeline gameLoop;

    private boolean rightKeyPressed = false;
    private boolean leftKeyPressed = false;

    public void initialize(int level) {
        gameState = new PlayingState(this);

        ballFactory = new BallFactory(BALL_RADIUS);
        paddleFactory = new PaddleFactory(PADDLE_SPEED);

        addBricksToGamePane(level);
        setUpPaddle();
        setUpBall();
        setUpKeybindings();
        setUpCountDownLabel();

        gameState.startGame();
    }

    public void countDownThenStartGame() {
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
        CollisionHandler.checkCollisions(ball, paddle, gamePane, scoreLabel, gameLoop);
    }

    private void setUpBall() {
        ball = (Ball) ballFactory.createGameObject(BALL_STARTING_X, BALL_STARTING_Y);
        gamePane.getChildren().add(ball.getCircle());
        ball.setVelocity(0, BALL_SPEED);
    }

    private void setUpPaddle() {
        paddle = (Paddle) paddleFactory.createGameObject(PADDLE_STARTING_X, PADDLE_STARTING_Y);
        gamePane.getChildren().add(paddle.getRectangle());
    }

    private void setUpKeybindings() {
        Scene scene = gamePane.getScene();

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.RIGHT) {
                rightKeyPressed = true;
            } else if (event.getCode() == KeyCode.LEFT) {
                leftKeyPressed = true;
            } else if(event.getCode() == KeyCode.P){
                toggleGamePause();
            } else if(event.getCode() == KeyCode.Q){
                JavaFXUtils.loadMainMenu(gamePane);
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
        paddle.setX(paddle.getRectangle().getLayoutX());
        if (rightKeyPressed && (paddle.getX() + PADDLE_WIDTH) < gamePane.getWidth()-(PADDLE_WIDTH*1.5)-PADDLE_SPEED) {
            paddle.setSpeed(PADDLE_SPEED);
            paddle.move();
        } else if (leftKeyPressed && paddle.getX() > -(PADDLE_WIDTH*1.5)-PADDLE_SPEED) {
            paddle.setSpeed(-PADDLE_SPEED);
            paddle.move();
        }
    }

    private void addBricksToGamePane(int level) {
        final int bricksPerRow = 5;
        final int totalRows = level * 2;

        for (int i = 0; i < totalRows * bricksPerRow; i++) {
            int row = i / bricksPerRow;
            int column = i % bricksPerRow;

            int x = ((BRICK_WIDTH + GAP) * column) + GAP;
            int y = ((BRICK_HEIGHT + GAP) * row) + GAP;

            Rectangle brick = new Rectangle(x, y, BRICK_WIDTH, BRICK_HEIGHT);
            brick.setFill(BRICK_COLOR);
            brick.setId("brick");
            gamePane.getChildren().add(brick);
        }
    }

    private void setUpCountDownLabel() {
        countDownLabel = new Label("3");
        countDownLabel.setLayoutX(240);
        countDownLabel.setLayoutY(350);
        countDownLabel.setStyle("-fx-font-size: 100px; -fx-font-weight: bold; -fx-text-fill: red;");
        gamePane.getChildren().add(countDownLabel);
    }

    public void setGameState(GameState newState) {
        gameState = newState;
    }

    public void toggleGamePause() {
        if(gameState instanceof PlayingState){
            setUpCountDownLabel();
            gameLoop.stop();
            gameState = new PausedState(this);
        } else if(gameState instanceof PausedState){
            gameState.resumeGame();
        }
    }
}