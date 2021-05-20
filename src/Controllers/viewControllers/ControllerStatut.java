package Controllers.viewControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class ControllerStatut {

    @FXML
    private Label statut;

    @FXML
    public TextArea chargementFichier;

    @FXML
    public Button nouvellePartie;

    public void initialize(){
        System.out.println("ControllerStatut - initialize");
    }
}
