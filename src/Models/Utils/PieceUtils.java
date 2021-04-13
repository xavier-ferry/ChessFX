package Models.Utils;

import Models.Couleur;
import Models.Piece;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static Models.Utils.CaseUtils.caseVoisin;
import static Models.Utils.PlateauUtils.*;

public class PieceUtils {

    private static ArrayList<String> getDeplacementsGenerique(String nomCase, Piece maPiece, Map<String,Piece> board, ArrayList<String> directionDeplacements){
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
        return res;
    }
    public static ArrayList<String> getDeplacementsPion(String nomCase, Piece pion, Map<String,Piece> board ){
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
            if (manger(nouvelleCase,board,pion.getCouleurPiece())){
                res.add(nouvelleCase);
            }

            nouvelleCase = caseVoisin(nomCase,"HautDroite");
            if (manger(nouvelleCase,board,pion.getCouleurPiece())){
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
            if ( manger(nouvelleCase,board,pion.getCouleurPiece())){
                res.add(nouvelleCase);
            }
            nouvelleCase = caseVoisin(nomCase,"BasDroite");
            if ( manger(nouvelleCase,board,pion.getCouleurPiece())){
                res.add(nouvelleCase);
            }
        }
        return res;
    }

    public static ArrayList<String> getDeplacementsTour(String nomCase, Piece tour, Map<String,Piece> board ){
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

    public static ArrayList<String> getDeplacementsFou(String nomCase, Piece fou, Map<String,Piece> board ) {
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

    public static ArrayList<String> getDeplacementsDame(String nomCase, Piece dame, Map<String,Piece> board ) {
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
    public static ArrayList<String> getDeplacementsRoi(String nomCase, Piece roi, Map<String,Piece> board ) {
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
        int i = 0;
        while (i < directionDeplacements.size()){
            String nouvelleCase = caseVoisin(nomCase,directionDeplacements.get(i));

            /*if (bouger(nouvelleCase,board) || manger(nouvelleCase,board,roi.getCouleurPiece()))
              // Tester si le roi se retrouve en echec
                 res.add(nouvelleCase);

            i++;*/

            /* Problème avec cette version de la fonction :*/
            if (bouger(nouvelleCase,board) || manger(nouvelleCase,board,roi.getCouleurPiece())){
                // Tester si le roi se retrouve en echec !
                Map<String,Piece> plateauTest = new HashMap<>(board);
                plateauTest = testDeplacerPiece(plateauTest,nomCase,nouvelleCase);
                if (! isCheck(plateauTest,nouvelleCase))
                    res.add(nouvelleCase);
                else
                    System.out.println("Echec si je me déplace en "+nouvelleCase);
            }
            i++;


        }
        return res;

    }

    public static ArrayList<String> getDeplacementsCavalier(String nomCase, Piece cavalier, Map<String,Piece> board ) {
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
