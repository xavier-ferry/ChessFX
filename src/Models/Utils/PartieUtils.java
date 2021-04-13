package Models.Utils;


import Models.Couleur;
import Models.Piece;

import java.util.Map;

import static Models.Utils.PlateauUtils.*;

public class PartieUtils {

    /*public static int ajouterPlateauHistorique(Map<String, Piece> plateauAajouter){
        Map<Map<String,Piece>, Integer> tmp = getHistoriquePlateau();
        Integer nb = tmp.get(plateauAajouter);
        if (nb.equals(null)) nb = 0;
        tmp.put(plateauAajouter,nb+1);
        setHistoriquePlateau(tmp);

        return nb;
    }*/

    public static String statutPartie(Map<String,Piece> plateau, Couleur joueurQuiVientDeJouer){
        Couleur couleurOpposee = joueurQuiVientDeJouer.getCouleurOpposee();
        String caseRoi = getCaseRoi(plateau,couleurOpposee);
        if (isCheck(plateau,caseRoi)){
            if (isCheckMate(plateau,caseRoi))
                return "CHECKMATE -- "+joueurQuiVientDeJouer+" WIN !! ";
            return "CHECK "+couleurOpposee;
        }
        return "ENCOURS";

    }

    public static String jouerCoup(Map<String,Piece> plateauActuel, String caseDepart, String caseDestination){
        Couleur couleurJoueurActif =getPiecebyNomCase(caseDepart,plateauActuel).getCouleurPiece();
        deplacerPiece(plateauActuel,caseDepart,caseDestination);
        /* Verifier que le déplacement est légal.
        * Ajouter le coup à l'historique
        * */

        /*if (ajouterPlateauHistorique(plateauActuel) == 3){
            return "NUL PAR REPETITION";
        }
*/

        return statutPartie(plateauActuel,couleurJoueurActif);

        //Si echec
        // Si echec et mat


    }
}
