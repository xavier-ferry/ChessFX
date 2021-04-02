package Deplacements;

import Structure.Couleur;
import Structure.Piece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static Deplacements.PieceUtils.*;
import static java.util.Map.*;

public class PlateauUtils {
    public static Piece getPiecebyNomCase(String nomCase, Map<String, Piece> board){
        return board.get(nomCase);
    }

    public static boolean bouger(String nomCase, Map<String,Piece>  board){
        return ( nomCase != null && getPiecebyNomCase(nomCase,board) == null);
    }

    public static boolean manger(String nomCase, Map<String,Piece>  board, Couleur maCouleur){
        if ( nomCase != null){
            Piece laPiece =  getPiecebyNomCase(nomCase,board);
            return laPiece != null && laPiece.getCouleurPiece() != maCouleur;
        }
        return false;
    }

    public static ArrayList<String> getDeplacementsAutorises(String nomCase, Map<String,Piece> board){
        ArrayList<String> res = new ArrayList<>();
        Piece maPiece = getPiecebyNomCase(nomCase, board);
        if (maPiece != null) {
            switch (maPiece.getNomMateriel()){
                case "PION" : res = getDeplacementsPion(nomCase, maPiece, board); break;
                case "TOUR" : res = getDeplacementsTour(nomCase, maPiece, board); break;
                case "FOU"  : res = getDeplacementsFou(nomCase, maPiece, board); break;
                case "DAME" : res = getDeplacementsDame(nomCase, maPiece, board); break;
                case "ROI"  : res = getDeplacementsRoi(nomCase, maPiece, board); break;
                case "CAVALIER" : res = getDeplacementsCavalier(nomCase, maPiece, board); break;
            }
        }else {
            res.add("emptyCase");
        }

        return res;
    }

    private static String getCaseRoi(Map<String,Piece> plateauActuel, Couleur maCouleur){
        String res = "ERROR";
        for (Entry<String,Piece> entry : plateauActuel.entrySet()){
            Piece pieceActuelle =  entry.getValue();

            if ( pieceActuelle.getCouleurPiece() == maCouleur && pieceActuelle.getNomMateriel().equals("ROI")){
                res = entry.getKey();
            }
        }
        return res;
    }
    private static boolean isCheck(Map<String,Piece> plateauActuel, String caseRoi){
        Couleur couleurRoi = getPiecebyNomCase(caseRoi,plateauActuel).getCouleurPiece();
        for (Entry<String,Piece> entry : plateauActuel.entrySet()){
            Piece pieceActuelle =  entry.getValue();
            if ( !pieceActuelle.getCouleurPiece().equals(couleurRoi) && getDeplacementsAutorises(entry.getKey(),plateauActuel).contains(caseRoi)){
                System.out.println("ECHEC sur les "+couleurRoi.toString()+" sur ce test.\n--------");
                return true;
            }
        }
        System.out.println("Pas d'ECHEC sur les "+couleurRoi.toString()+" sur ce test.\n--------");
        return false;
    }

    private static Map<String, Piece> testDeplacerPiece(Map<String, Piece> plateauActuel,String caseDepart, String caseDestination) {
        System.out.println("--------\nDéplacement test : "+caseDepart + " -> "+caseDestination);
        plateauActuel.put(caseDestination,plateauActuel.get(caseDepart));
        plateauActuel.remove(caseDepart);
        return plateauActuel;
    }

    private static Map<String, Piece> imaginePlateau(Map<String, Piece> plateauActuel, String caseDepart, String caseDestination){
        Couleur maCouleur = plateauActuel.get(caseDepart).getCouleurPiece();
        plateauActuel = testDeplacerPiece(new HashMap<>(plateauActuel),caseDepart,caseDestination);
        if (!isCheck(plateauActuel,getCaseRoi(plateauActuel,maCouleur))) {
            return plateauActuel;
        }
        else
            return null;
    }

    public static void deplacerPiece(String caseDepart, String caseDestination, Map<String,Piece> board){
        if (getDeplacementsAutorises(caseDepart,board).contains(caseDestination) &&
                imaginePlateau(new HashMap<>(board),caseDepart,caseDestination) != null ) {
            board.put(caseDestination,board.get(caseDepart));
            board.remove(caseDepart);
        } else {
            System.out.println("Déplacement Illégal !");
        }
    }
}
