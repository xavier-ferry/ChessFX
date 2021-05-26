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
    public ArrayList<String> getCasesDefendues(Map<String, Piece> board, String nomCase){
        ArrayList<String> res = new ArrayList<>();
        String nouvelleCase;
        switch (getCouleurPiece()){
            case BLANC:
                nouvelleCase = caseVoisin(nomCase,"HautGauche");
                if (nouvelleCase != null)
                    res.add(nouvelleCase);
                nouvelleCase = caseVoisin(nomCase,"HautDroite");
                if (nouvelleCase != null)
                    res.add(nouvelleCase);
                break;
            case NOIR:
                nouvelleCase = caseVoisin(nomCase,"BasGauche");
                if (nouvelleCase != null)
                    res.add(nouvelleCase);
                nouvelleCase = caseVoisin(nomCase,"BasDroite");
                if (nouvelleCase != null)
                    res.add(nouvelleCase);
                break;
        }
        return res;
    }

    @Override
    public ArrayList<String> getCasesAccessibles(Map<String,Piece> board, String nomCase) {
        ArrayList<String> res = new ArrayList<>();
        String nouvelleCase;
        switch (getCouleurPiece()){
            case BLANC:
                nouvelleCase = caseVoisin(nomCase,"Haut");
                if (bouger(board,nouvelleCase)){
                    res.add(nouvelleCase);
                    if(nomCase.charAt(1)=='2'){
                        nouvelleCase = caseVoisin(nouvelleCase,"Haut");
                        if (bouger(board,nouvelleCase)) {
                            res.add(nouvelleCase);
                        }
                    }
                }
                break;
            case NOIR:
                nouvelleCase = caseVoisin(nomCase,"Bas");
                if (bouger(board,nouvelleCase)){
                    res.add(nouvelleCase);
                    if(nomCase.charAt(1)=='7') {
                        nouvelleCase = caseVoisin(nouvelleCase, "Bas");
                        if (bouger(board,nouvelleCase)) {
                            res.add(nouvelleCase);
                        }
                    }
                }
                break;
        }

        ArrayList<String> casesDefendues = getCasesDefendues(board,nomCase);
        for (String caseDef : casesDefendues){
            if (manger(board,caseDef))
                res.add(caseDef);
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
