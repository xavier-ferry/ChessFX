package Models;


import java.util.ArrayList;
import java.util.Map;

public abstract class Piece {

    private Couleur couleurPiece;
    private String nomPiece;
    private ArrayList<String> directions;

    public Piece(String nomPiece, Couleur couleurPiece, ArrayList<String> directions){
        this.couleurPiece = couleurPiece;
        this.nomPiece = nomPiece;
        this.directions = directions;
    }

    @Override
    public String toString(){return nomPiece+couleurPiece.toString();}

    //TODO : Avoir l'attribut couleur dans l'interface pour ne pas redéfinir cette fonction partout ?
    public Couleur getCouleurPiece(){return couleurPiece;}
    public String getNomPiece(){return nomPiece;}
    public ArrayList<String> getDirections(){return directions; }


    public ArrayList<String> getCasesAccessibles(Map<String,Piece> board, String nomCase){
        ArrayList<String> res = new ArrayList<>() ;
        for (String caseActuelle : getCasesDefendues(board,nomCase)){ //On va retirer les cases avec une piece de meme couleur
            if (board.get(caseActuelle) == null || board.get(caseActuelle).getCouleurPiece() != getCouleurPiece())
                res.add(caseActuelle);
        }
        return res;
    }
    /*default  ArrayList<String> getCasesJouables(Map<String,Piece> board, String nomCase){
        ArrayList<String> res = getCasesDefendues(board,nomCase);
        for (String caseActuelle : res){ //On va retirer les cases avec une piece de meme couleur
            if (board.get(caseActuelle) != null && board.get(caseActuelle).getCouleurPiece() == getCouleurPiece())
                res.remove(caseActuelle);
        }
        return res;
    }*/

    public ArrayList<String> getCasesDefendues(Map<String, Piece> board, String nomCase){
        return getCasesDefenduesGenerique(board,nomCase,getDirections());
    }

    // TODO : Meilleure solution ? getDirections() pourrait être privé dans les classes ?
    //public ArrayList<String> getDirections(){ return null;}


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
    public String caseVoisin(String nomCase, String direction){
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

    public boolean manger(Map<String,Piece>  board, String nomCase){
        if ( nomCase != null){
            Piece laPiece =  board.get(nomCase);
            return laPiece != null && laPiece.getCouleurPiece() != getCouleurPiece();
        }
        return false;
    }
    public boolean bouger(Map<String,Piece>  board, String nomCase){
        return ( nomCase != null && board.get(nomCase) == null);
    }



    public ArrayList<String> getCasesDefenduesGenerique(Map<String, Piece> board, String nomCase, ArrayList<String> directionDeplacements){
        ArrayList<String> res = new ArrayList<>();
        String nouvelleCase = nomCase;
        int i = 0;
        while (i < directionDeplacements.size()){
            nouvelleCase = caseVoisin(nouvelleCase,directionDeplacements.get(i));
            if(nouvelleCase != null) {
                if (bouger(board, nouvelleCase)) {
                    res.add(nouvelleCase);
                } else {
                    res.add(nouvelleCase);
                    i++;
                    nouvelleCase = nomCase;
                }
            } else {
                i++;
                nouvelleCase = nomCase;
            }
        }
        /*while (i < directionDeplacements.size()){
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
        }*/
        return res;
    }
}

