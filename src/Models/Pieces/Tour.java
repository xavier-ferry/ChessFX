package Models.Pieces;

import Models.Couleur;
import Models.Piece;

import java.util.ArrayList;

public class Tour implements Piece {

    private String nomPiece;
    private Couleur couleurPiece;


    public Tour(Couleur couleur){
        this.nomPiece = "TOUR";
        this.couleurPiece = couleur;
    }


    public ArrayList<String> getDirections(){
        return new ArrayList<>(){
            {
                add("Bas");
                add("Haut");
                add("Gauche");
                add("Droite");
            }
        };
    }

    public String toString(){
        return nomPiece+couleurPiece.toString();
    }

    @Override
    public Couleur getCouleurPiece() {
        return couleurPiece;
    }

    @Override
    public String getNomPiece() {
        return nomPiece;
    }
}
