package Models;


import java.util.ArrayList;
import java.util.Map;

public interface Piece {


    @Override
    String toString();

    Couleur getCouleurPiece();
    String getNomPiece();

    ArrayList<String> getCasesAccessibles(Map<String,Piece> board,String nomCase);

    private String caseBas(String nomCase){
        char lettre = nomCase.charAt(0);
        int ligne = Character.getNumericValue(nomCase.charAt(1));
        ligne -= 1;

        if ( ligne >= 1 && ligne <= 8 ){
            return "" + lettre + ligne;
        } else {
            return null;
        }
    }
    private String caseHautGauche(String nomCase){
        char lettre = nomCase.charAt(0);
        int ligne = Character.getNumericValue(nomCase.charAt(1));

        char lettreGauche = (char) ((int)lettre -1);
        ligne += 1;

        if ( ligne >= 1 && ligne <= 8 && lettreGauche >= 'a'){
            return "" + lettreGauche + ligne;
        } else {
            return null;
        }
    }
    private String caseHautDroite(String nomCase){
        char lettre = nomCase.charAt(0);
        int ligne = Character.getNumericValue(nomCase.charAt(1));

        char lettreDroite = (char) ((int)lettre +1);
        ligne += 1;

        if ( ligne >= 1 && ligne <= 8 && lettreDroite <= 'h'){
            return "" + lettreDroite + ligne;
        } else {
            return null;
        }
    }
    private String caseBasDroite(String nomCase){
        char lettre = nomCase.charAt(0);
        int ligne = Character.getNumericValue(nomCase.charAt(1));

        char lettreDroite = (char) ((int)lettre +1);
        ligne -= 1;

        if ( ligne >= 1 && ligne <= 8 && lettreDroite <= 'h'){
            return "" + lettreDroite + ligne;
        } else {
            return null;
        }
    }
    private String caseBasGauche(String nomCase){
        char lettre = nomCase.charAt(0);
        int ligne = Character.getNumericValue(nomCase.charAt(1));

        char lettreGauche = (char) ((int)lettre -1);
        ligne -= 1;

        if ( ligne >= 1 && ligne <= 8 && lettreGauche >= 'a'){
            return "" + lettreGauche + ligne;
        } else {
            return null;
        }
    }
    private String caseGauche(String nomCase){
        char lettre = nomCase.charAt(0);
        int ligne = Character.getNumericValue(nomCase.charAt(1));

        char lettreGauche = (char) ((int)lettre -1);
        if ( ligne >= 1 && ligne <= 8 && lettreGauche >= 'a'){
            return "" + lettreGauche + ligne;
        } else {
            return null;
        }
    }
    private String caseDroite(String nomCase){
        char lettre = nomCase.charAt(0);
        int ligne = Character.getNumericValue(nomCase.charAt(1));

        char lettreDroite = (char) ((int)lettre +1);
        if ( ligne >= 1 && ligne <= 8 && lettreDroite <= 'h'){
            return "" + lettreDroite + ligne;
        } else {
            return null;
        }
    }
    private String caseHaut(String nomCase){
        char lettre = nomCase.charAt(0);
        int ligne = Character.getNumericValue(nomCase.charAt(1));
        ligne += 1;
        if ( ligne >= 1 && ligne <= 8){
            return "" + lettre + ligne;
        } else {
            return null;
        }
    }
    default String caseVoisin(String nomCase, String direction){
        switch (direction){
            case "HautGauche"   : return caseHautGauche(nomCase);
            case "Haut"         : return caseHaut(nomCase);
            case "HautDroite"   : return caseHautDroite(nomCase);
            case "Droite"       : return caseDroite(nomCase);
            case "BasDroite"    : return caseBasDroite(nomCase);
            case "Bas"          : return caseBas(nomCase);
            case "BasGauche"    : return caseBasGauche(nomCase);
            case "Gauche"       : return caseGauche(nomCase);
            default : return "";
        }
    }

    default boolean manger(Map<String,Piece>  board, String nomCase){
        if ( nomCase != null){
            Piece laPiece =  board.get(nomCase);
            return laPiece != null && laPiece.getCouleurPiece() != getCouleurPiece();
        }
        return false;
    }
    default boolean bouger(Map<String,Piece>  board, String nomCase){
        return ( nomCase != null && board.get(nomCase) == null);
    }

    default ArrayList<String> getDeplacementsGenerique(Map<String, Piece> board, String nomCase, ArrayList<String> directionDeplacements){
        ArrayList<String> res = new ArrayList<>();
        String nouvelleCase = nomCase;
        int i = 0;
        while (i < directionDeplacements.size()){
            nouvelleCase = caseVoisin(nouvelleCase,directionDeplacements.get(i));
            if (bouger(board,nouvelleCase)){
                res.add(nouvelleCase);
            } else if (manger(board,nouvelleCase)){
                res.add(nouvelleCase);
                i++;
                nouvelleCase = nomCase;
            } else {
                i++;
                nouvelleCase = nomCase;
            }
        }
        return res;
    }
}

