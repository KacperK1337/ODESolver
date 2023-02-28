package pl.kacperk;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ODESolverApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(
                        getClass().getResource("/fxml/main.fxml")
                )
        );
        Scene scene = new Scene(root, 900, 600);
        String css = Objects.requireNonNull(
                this.getClass().getResource("/stylesheet/main.css")
        ).toExternalForm();
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
        primaryStage.setTitle("ODE Solver");
        primaryStage.setMinHeight(637);
        primaryStage.setMinWidth(914);
        primaryStage.show();
    }
}
