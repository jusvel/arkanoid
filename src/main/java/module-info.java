module com.game.arkanoid {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.game.arkanoid to javafx.fxml;
    exports com.game.arkanoid;
    exports com.game.arkanoid.viewControllers;
    opens com.game.arkanoid.viewControllers to javafx.fxml;
}