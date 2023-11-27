package com.game.arkanoid.viewControllers;

import com.game.arkanoid.helpers.JavaFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainMenuController {
    @FXML
    private VBox vbox;

    public void exit(){
        System.exit(0);
    }

    public void startGame(ActionEvent actionEvent){
        int level = Integer.parseInt(String.valueOf(((Button) actionEvent.getSource()).getId().charAt(5)));

        JavaFXUtils.loadGame(vbox,level);
    }
}
