package com.game.arkanoid.viewControllers;

import com.game.arkanoid.LaunchGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenuController {
    @FXML
    private VBox vbox;

    public void exit(){
        System.exit(0);
    }

    public void startGame(ActionEvent actionEvent){

        int level = ((Button) actionEvent.getSource()).getId().strip().charAt(5) - '0';
        System.out.println(level);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LaunchGUI.class.getResource("gameView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 560, 800);
            GameViewController controller = fxmlLoader.getController();

            Stage stage = (Stage) vbox.getScene().getWindow();
            stage.setTitle("Arkanoid");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

            controller.initialize(level);
        } catch (Exception e){
            System.out.println("Failed to load gameView.fxml");
            e.printStackTrace();
        }
    }
}
