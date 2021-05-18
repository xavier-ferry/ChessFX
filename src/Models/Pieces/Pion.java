package Models.Pieces;

import Models.Couleur;
import Models.Piece;

import java.util.ArrayList;
import java.util.Map;

public class Pion implements Piece {

    private String nomPiece;
    private Couleur couleurPiece;


    public Pion(Couleur couleur){
        this.nomPiece = "PION";
        this.couleurPiece = couleur;
    }

    @Override
    public ArrayList<String> getCasesAccessibles(Map<String,Piece> board, String nomCase) {
        ArrayList<String> res = new ArrayList<>();
        String nouvelleCase;
        if (getCouleurPiece() == Couleur.BLANC){
            nouvelleCase = caseVoisin(nomCase,"Haut");
            if (bouger(board,nouvelleCase)){
                res.add(nouvelleCase);
                if(nouvelleCase.charAt(1)=='3'){
                    nouvelleCase = caseVoisin(nouvelleCase,"Haut");
                    if (bouger(board,nouvelleCase)) {
                        res.add(nouvelleCase);
                    }
                }
            }
            nouvelleCase = caseVoisin(nomCase,"HautGauche");
            if (manger(board,nouvelleCase)){
                res.add(nouvelleCase);
            }

            nouvelleCase = caseVoisin(nomCase,"HautDroite");
            if (manger(board,nouvelleCase)){
                res.add(nouvelleCase);
            }


        } else {
            // pion de Couleur.NOIR
            // Si le pion n'est pas sur la deuxieme rangee
            nouvelleCase = caseVoisin(nomCase,"Bas");
            if (bouger(board,nouvelleCase)){
                res.add(nouvelleCase);
                if(nouvelleCase.charAt(1)=='6') {
                    nouvelleCase = caseVoisin(nouvelleCase, "Bas");
                    if (bouger(board,nouvelleCase)) {
                        res.add(nouvelleCase);
                    }
                }
            }

            nouvelleCase = caseVoisin(nomCase,"BasGauche");
            if ( manger(board,nouvelleCase)){
                res.add(nouvelleCase);
            }
            nouvelleCase = caseVoisin(nomCase,"BasDroite");
            if ( manger(board,nouvelleCase)){
                res.add(nouvelleCase);
            }
        }
        return res;
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
