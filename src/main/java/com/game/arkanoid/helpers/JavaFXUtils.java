package com.game.arkanoid.helpers;

import com.game.arkanoid.LaunchGUI;
import com.game.arkanoid.viewControllers.GameViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class JavaFXUtils {
    public static void showTextOnScreen(Pane gamePane, String text, Color color) {
        Text lossText;
        lossText = new Text(text);
        lossText.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        lossText.setFill(color);
        lossText.setVisible(true);
        lossText.setX((gamePane.getWidth() - lossText.getLayoutBounds().getWidth()) / 2);
        lossText.setY((gamePane.getHeight() - lossText.getLayoutBounds().getHeight()) / 2);
        gamePane.getChildren().add(lossText);
    }

    public static void loadGame(VBox vbox, int level){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LaunchGUI.class.getResource("gameView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 560, 800);
            GameViewController gameViewController = fxmlLoader.getController();

            Stage stage = (Stage) vbox.getScene().getWindow();
            stage.setTitle("Arkanoid");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

            gameViewController.initialize(level);
        } catch (Exception e){
            System.out.println("Failed to load gameView.fxml");
            e.printStackTrace();
        }
    }

    public static void loadMainMenu(Pane gamePane) {
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
