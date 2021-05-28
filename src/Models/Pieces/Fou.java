package Models.Pieces;

import Models.Couleur;
import Models.Piece;

import java.util.ArrayList;

public class Fou extends Piece {

    public Fou(Couleur couleur){
        super("FOU",couleur,new ArrayList<>(){
            {
                add("HautGauche");
                add("HautDroite");
                add("BasGauche");
                add("BasDroite");
            }
        });
    }


}
