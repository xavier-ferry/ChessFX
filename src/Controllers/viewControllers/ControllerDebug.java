package Controllers.viewControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ControllerDebug {

    @FXML
    private Label statutDebug;

    @FXML
    public Button afficherEchiquier;

    public void initialize(){
        System.out.println("ControllerDebug - Initialize");
    }

    public String getStatutDebug(){ return statutDebug.getText();}
    public void setStatutDebug(String message){ statutDebug.setText(message);}
}
