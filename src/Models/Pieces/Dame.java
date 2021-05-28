package Models.Pieces;

import Models.Couleur;
import Models.Piece;

import java.util.ArrayList;

public class Dame extends Piece {

    public Dame(Couleur couleur){
        super("DAME",couleur,new ArrayList<>(){
            {
                add("HautGauche");
                add("HautDroite");
                add("BasGauche");
                add("BasDroite");
                add("Bas");
                add("Haut");
                add("Gauche");
                add("Droite");
            }
        });
    }

}
