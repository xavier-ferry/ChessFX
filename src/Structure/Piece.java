package Structure;

import java.util.ArrayList;
import java.util.Map;

import static Deplacements.CaseUtils.caseBas;

public class Piece {
    public enum NomMateriel {PION,TOUR,CAVALIER,FOU,DAME,ROI}
    private final NomMateriel piece;
    private final Couleur couleurPiece;


    public Piece(NomMateriel maPiece, Couleur maCouleur){
        this.piece = maPiece;
        this.couleurPiece = maCouleur;
    }

    @Override
    public String toString() {
        String res = "";
        switch (couleurPiece){
            case BLANC: res = piece+"BLANC";break;
            case NOIR: res = piece+"NOIR";
        }
        return res;
    }

    public Couleur getCouleurPiece(){return couleurPiece;}
    public String getNomMateriel(){return piece.toString();}

    private static String caseHautGauche(String nomCase){
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
    private static String caseHautDroite(String nomCase){
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
    private static String caseBasDroite(String nomCase){
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
    private static String caseBasGauche(String nomCase){
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
    private static String caseGauche(String nomCase){
        char lettre = nomCase.charAt(0);
        int ligne = Character.getNumericValue(nomCase.charAt(1));

        char lettreGauche = (char) ((int)lettre -1);
        if ( ligne >= 1 && ligne <= 8 && lettreGauche >= 'a'){
            return "" + lettreGauche + ligne;
        } else {
            return null;
        }
    }
    private static String caseDroite(String nomCase){
        char lettre = nomCase.charAt(0);
        int ligne = Character.getNumericValue(nomCase.charAt(1));

        char lettreDroite = (char) ((int)lettre +1);
        if ( ligne >= 1 && ligne <= 8 && lettreDroite <= 'h'){
            return "" + lettreDroite + ligne;
        } else {
            return null;
        }
    }
    private static String caseHaut(String nomCase){
        char lettre = nomCase.charAt(0);
        int ligne = Character.getNumericValue(nomCase.charAt(1));
        ligne += 1;
        if ( ligne >= 1 && ligne <= 8){
            return "" + lettre + ligne;
        } else {
            return null;
        }
    }
    /*
    private static String caseBas(String nomCase){
        char lettre = nomCase.charAt(0);
        int ligne = Character.getNumericValue(nomCase.charAt(1));
        ligne -= 1;

        if ( ligne >= 1 && ligne <= 8 ){
            return "" + lettre + ligne;
        } else {
            return null;
        }
    }
    */

    private static String caseVoisin(String nomCase, String direction){
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

    private static boolean bouger(String nomCase, Plateau board){
        return ( nomCase != null && board.getPiecebyNomCase(nomCase) == null);
    }

    private static boolean manger(String nomCase, Plateau board, Couleur maCouleur){
        if ( nomCase != null){
            Piece laPiece =  board.getPiecebyNomCase(nomCase);
            return laPiece != null && laPiece.getCouleurPiece() != maCouleur;
        }
        return false;
    }

    public static ArrayList<String> getDeplacementsPion(String nomCase, Piece pion, Plateau board){
        ArrayList<String> res = new ArrayList<>();
        String nouvelleCase;
        if (pion.getCouleurPiece() == Couleur.BLANC){
            nouvelleCase = caseVoisin(nomCase,"Haut");
            if (bouger(nouvelleCase,board)){
                res.add(nouvelleCase);
                if(nouvelleCase.charAt(1)=='3'){
                    nouvelleCase = caseVoisin(nouvelleCase,"Haut");
                    if (bouger(nouvelleCase,board)) {
                        res.add(nouvelleCase);
                    }
                }
            }
            nouvelleCase = caseVoisin(nomCase,"HautGauche");
            if (manger(nouvelleCase,board,pion.couleurPiece)){
                res.add(nouvelleCase);
            }

            nouvelleCase = caseVoisin(nomCase,"HautDroite");
            if (manger(nouvelleCase,board,pion.couleurPiece)){
                res.add(nouvelleCase);
            }


        } else {
            // pion de Couleur.NOIR
            // Si le pion n'est pas sur la deuxieme rangee
            nouvelleCase = caseVoisin(nomCase,"Bas");
            if (bouger(nouvelleCase,board)){
                res.add(nouvelleCase);
                if(nouvelleCase.charAt(1)=='6') {
                    nouvelleCase = caseVoisin(nouvelleCase, "Bas");
                    if (bouger(nouvelleCase, board)) {
                        res.add(nouvelleCase);
                    }
                }
            }

            nouvelleCase = caseVoisin(nomCase,"BasGauche");
            if ( manger(nouvelleCase,board,pion.couleurPiece)){
                res.add(nouvelleCase);
            }
            nouvelleCase = caseVoisin(nomCase,"BasDroite");
            if ( manger(nouvelleCase,board,pion.couleurPiece)){
                res.add(nouvelleCase);
            }
        }
        System.out.println("PION en "+nomCase+ " peut se deplacer -> "+res);
        return res;
    }

    public static ArrayList<String> getDeplacementsGenerique(String nomCase, Piece maPiece, Map<String,Piece> board, ArrayList<String> directionDeplacements){
        ArrayList<String> res = new ArrayList<>();
        String nouvelleCase = nomCase;
        int i = 0;
        while (i < directionDeplacements.size()){
            nouvelleCase = caseVoisin(nouvelleCase,directionDeplacements.get(i));
            if (bouger(nouvelleCase,board)){
                res.add(nouvelleCase);
            } else if (manger(nouvelleCase,board,maPiece.getCouleurPiece())){
                res.add(nouvelleCase);
                i++;
                nouvelleCase = nomCase;
            } else {
                i++;
                nouvelleCase = nomCase;
            }
        }

        System.out.println(maPiece.getNomMateriel()+" "+maPiece.getCouleurPiece()+" en "+nomCase+ " peut se déplacer ici -> "+res);
        return res;
    }

    public static ArrayList<String> getDeplacementsTour(String nomCase, Piece tour, Plateau board){
        ArrayList<String> directionDeplacements = new ArrayList<>(){
            {
                add("Bas");
                add("Haut");
                add("Gauche");
                add("Droite");
            }
        };
        return getDeplacementsGenerique(nomCase,tour,board,directionDeplacements);
    }

    public static ArrayList<String> getDeplacementsFou(String nomCase, Piece fou, Plateau board) {
        ArrayList<String> directionDeplacements = new ArrayList<>(){
            {
                add("HautGauche");
                add("HautDroite");
                add("BasGauche");
                add("BasDroite");
            }
        };
        return getDeplacementsGenerique(nomCase,fou,board,directionDeplacements);
    }

    public static ArrayList<String> getDeplacementsDame(String nomCase, Piece dame, Plateau board) {
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
        return getDeplacementsGenerique(nomCase,dame,board,directionDeplacements);
    }

    // Attention, il faudra vérifier que le deplacement effectué par le roi n'implique pas un echec
   public static ArrayList<String> getDeplacementsRoi(String nomCase, Piece roi, Plateau board) {
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
       ArrayList<String> res = new ArrayList<>();
       System.out.println(roi.getNomMateriel()+" "+roi.getCouleurPiece()+" en "+nomCase);
       int i = 0;
       while (i < directionDeplacements.size()){
           String nouvelleCase = caseVoisin(nomCase,directionDeplacements.get(i));
           if (bouger(nouvelleCase,board) || manger(nouvelleCase,board,roi.getCouleurPiece()) ){
               res.add(nouvelleCase);
           }
           i++;
       }
       return res;

   }

    public static ArrayList<String> getDeplacementsCavalier(String nomCase, Piece cavalier, Plateau board) {
        ArrayList<String> res = new ArrayList<>();
        ArrayList<String> casesATester = new ArrayList<>();
        char lettreCase = nomCase.charAt(0);
        int numeroCase = Character.getNumericValue(nomCase.charAt(1));

        for (int i = -2 ; i <= 2 ; i+= 4) {
            char nouvelleLettreCase = (char) ((int) lettreCase + i);
            if (nouvelleLettreCase >= 'a' && nouvelleLettreCase <= 'h') {
                if (numeroCase > 1) {
                    casesATester.add("" + nouvelleLettreCase + ((char) numeroCase - 1));
                }
                if (numeroCase < 8) {
                    casesATester.add("" + nouvelleLettreCase + ((char) numeroCase + 1));
                }
            }
        }
        for (int i = -2 ; i <= 2 ; i+= 4) {
            if (numeroCase + i <= 8 && numeroCase + i >= 1) {
                char nouvelleLettreCase = (char) ((int) lettreCase - 1);
                if (lettreCase >= 'a'){
                    casesATester.add("" + nouvelleLettreCase + ((char) numeroCase + i));
                }
                nouvelleLettreCase = (char) ((int) lettreCase + 1);
                if (lettreCase <= 'h'){
                    casesATester.add("" + nouvelleLettreCase + ((char) numeroCase + i));
                }
            }
        }
        for (String s : casesATester) {
            if (bouger(s, board) || manger(s, board, cavalier.getCouleurPiece())) {
                res.add(s);
            }
        }

        return res;
    }
}

