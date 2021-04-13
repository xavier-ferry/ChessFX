package Controllers.viewControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ControllerDeroulement {
    @FXML
    private Label coupsPartie;

    private static int nbCoups;



    public void initialize(){
        System.out.println("ControllerDeroulement - initialize");

        nbCoups = 1;
        coupsPartie.setText("Coups de la partie : "+'\n');

    }

    public void updateCoupPartie(String deplacement){
        /* Faire ici l'affichage suivant :
        * 1. e4 e5
        * 2. Nf3 d5
        * ...
        * */
        String txt;
        if (nbCoups % 2 == 1 )
            txt =  coupsPartie.getText() + (nbCoups / 2 + 1) +".\t"+ deplacement + "\t";
        else
            txt = coupsPartie.getText() + deplacement + "\n";

        coupsPartie.setText(txt);
        nbCoups++;
    }
}
