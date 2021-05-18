package Models.Pieces;

import Models.Couleur;
import Models.Piece;

import java.util.ArrayList;
import java.util.Map;

public class Fou implements Piece {

    private String nomPiece;
    private Couleur couleurPiece;


    public Fou(Couleur couleur){
        this.nomPiece = "FOU";
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
