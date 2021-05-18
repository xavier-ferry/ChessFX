package Models.Pieces;

import Models.Couleur;
import Models.Piece;

import java.util.ArrayList;
import java.util.Map;

public class Dame implements Piece {

    private String nomPiece;
    private Couleur couleurPiece;


    public Dame(Couleur couleur){
        this.nomPiece = "DAME";
        this.couleurPiece = couleur;
    }

    @Override
    public ArrayList<String> getCasesAccessibles(Map<String,Piece> board, String nomCase) {
        ArrayList<String> directionDeplacements = new ArrayList<>(){
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
    };
        return getDeplacementsGenerique(board,nomCase,directionDeplacements);
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
