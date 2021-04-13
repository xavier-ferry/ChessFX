import Controllers.SuperController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainBis extends Application {

    @Override
    public void start(Stage primaryStage) {
        System.out.println("MainBis - start");

        SuperController superController = null;
        try {
            superController = new SuperController();
            superController.getBorderPane().getStylesheets().add(getClass().getResource("Views/grid.css").toExternalForm());
            primaryStage.setScene(new Scene(superController.getBorderPane()));

        } catch (IOException e) {
            System.out.println("Erreur lors de new SuperController()");
            e.printStackTrace();
        }

        primaryStage.setTitle("ChessFX");
        primaryStage.show();
    }

    public static void main(String[] args) {
        System.out.println("MainBis - main");
        launch(args);
    }
}
