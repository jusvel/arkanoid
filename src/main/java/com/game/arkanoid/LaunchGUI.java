package com.game.arkanoid;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;

public class LaunchGUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LaunchGUI.class.getResource("gameView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 560, 800);
        GameViewController controller = fxmlLoader.getController();
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.LEFT) {
                controller.movePaddleLeft();
            } else if (code == KeyCode.RIGHT) {
                controller.movePaddleRight();
            }
        });
        stage.setTitle("Arkanoid");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}