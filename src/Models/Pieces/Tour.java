package Models.Pieces;

import Models.Couleur;
import Models.Piece;

import java.util.ArrayList;

public class Tour extends Piece {

    private boolean hasMoved;


    public Tour(Couleur couleur){
        super("TOUR",couleur,new ArrayList<>(){
            {
                add("Bas");
                add("Haut");
                add("Gauche");
                add("Droite");
            }
        });

        this.hasMoved = false;
    }

    public void setHasMoved(){hasMoved = true;}
}
