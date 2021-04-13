package Controllers.viewControllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ControllerDeplacement {
    @FXML
    public TextArea dep;

    public void initialize(){
        System.out.println("ControllerDeplacement - Initialize");


    }
/*
    public void testerCoup() {

        String tmp = stringToDepartDestination(maPartie.getPlateau().getEchiquier(),dep.getText(), Couleur.BLANC);
        System.out.println(tmp);
        if (tmp != "ERROR" ){
            String [] cases = tmp.split(",");
            String depart =cases[0];
            String destination = cases[1];

            System.out.println(depart + " -> " + destination);
            String statutPartie = jouerCoup(maPartie.getPlateau().getEchiquier(),depart,destination);
            updateGridPanel(depart,destination);

            String txt = String.join(", ",cases);
            txtDebug.setText(txt);
            updateCoupPartie(dep.getText()+statutPartie);
        } else {
            txtDebug.setText("ERROR deplacement");
        }
        // Problème de isCheckMate :(
        *//*if (isCheckMate(maPartie.getPlateau().getEchiquier(),getCaseRoi(maPartie.getPlateau().getEchiquier(), Couleur.NOIR))){
            txtDebug.setText("CHECKMATE");
            System.out.println("ECHEC ET MAT !!!");
        }
    }
    */
}
