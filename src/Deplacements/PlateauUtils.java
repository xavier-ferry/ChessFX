package Deplacements;

import Structure.Couleur;
import Structure.Piece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static Deplacements.CaseUtils.caseVoisin;
import static Deplacements.PieceUtils.*;
import static java.util.Map.*;

public class PlateauUtils {

    /**
     * @param nomCase
     * @param board
     * @return la piece qui se trouve à la case nomCase. null si la case est vide
     */
    public static Piece getPiecebyNomCase(String nomCase, Map<String, Piece> board){
        return board.get(nomCase);
    }


    /**
     * @param nomCase la case où on tente de se déplacer
     * @param board un plateau de jeu
     * @return true si la case est libre, false sinon
     */
    public static boolean bouger(String nomCase, Map<String,Piece>  board){
        return ( nomCase != null && getPiecebyNomCase(nomCase,board) == null);
    }


    /**
     * @param nomCase la case où on tente de manger
     * @param board un plateau de jeu
     * @param maCouleur la couleur du joueur qui tente de manger
     * @return true si la case est occupé par une piece de l'adversaire. False sinon
     */
    public static boolean manger(String nomCase, Map<String,Piece>  board, Couleur maCouleur){
        if ( nomCase != null){
            Piece laPiece =  getPiecebyNomCase(nomCase,board);
            return laPiece != null && laPiece.getCouleurPiece() != maCouleur;
        }
        return false;
    }

    /**
     * @param nomCase
     * @param board
     * @return une ArrayList<String> contenant toutes les cases où peut se rendre la piece qui se trouve en nomCase
     */
    public static ArrayList<String> getDeplacementsAutorises(String nomCase, Map<String,Piece> board){
        ArrayList<String> res = new ArrayList<>();
        Piece maPiece = getPiecebyNomCase(nomCase, board);
        if (maPiece != null) {
            switch (maPiece.getStringNomMateriel()){
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

    /**
     * @param plateauActuel
     * @param maCouleur
     * @return la case où se trouve le roi de couleur maCouleur
     */
    public static String getCaseRoi(Map<String,Piece> plateauActuel, Couleur maCouleur){
        String res = "ERROR";
        for (Entry<String,Piece> entry : plateauActuel.entrySet()){
            Piece pieceActuelle =  entry.getValue();

            if ( pieceActuelle.getCouleurPiece() == maCouleur && pieceActuelle.getStringNomMateriel().equals("ROI")){
                res = entry.getKey();
            }
        }
        return res;
    }


    /**
     * @param plateauActuel
     * @param caseRoi
     * @return un booléen correspondant au fait que le roi en caseRoi est echec et mat
     */
    public static boolean isCheckMate(Map<String,Piece> plateauActuel, String caseRoi) {
        Couleur couleurRoi = getPiecebyNomCase(caseRoi,plateauActuel).getCouleurPiece();
        System.out.println("Déplacement autorisés roi noir " + getDeplacementsAutorises(caseRoi,plateauActuel));
        System.out.println("Test isCheck = " + isCheck(plateauActuel,caseRoi));
        if (getDeplacementsAutorises(caseRoi,plateauActuel).size()==0 /*&& isCheck(plateauActuel,caseRoi)*/){
            System.out.println("Dans le if");
            for (Entry<String,Piece> entry : plateauActuel.entrySet()) {
                if (entry.getValue().getCouleurPiece().equals(couleurRoi)){
                    ArrayList<String> destAutorises = getDeplacementsAutorises(entry.getKey(),plateauActuel);
                    for (int i = 0 ; i < destAutorises.size() ; i++) {
                        System.out.println("Tentative de déplacement de "+entry.getKey()+ " -> "+destAutorises.get(i));
                        Map<String, Piece> plateauTest = testDeplacerPiece(new HashMap<>(plateauActuel), entry.getKey(), destAutorises.get(i));
                        if (! isCheck(plateauTest,caseRoi)) {
                            System.out.println("Plus d'échec en faisant "+entry.getKey()+ " -> "+destAutorises.get(i));
                            return false;
                        }
                    }
                }
            }
            return true;
        }

        return false;
    }


    /**
     * @param plateauActuel
     * @param nomCase
     * @return un booléen qui indique si la pièce se trouvant en nomCase risque de se faire manger.
     */
    public static boolean estMenacee(Map<String,Piece> plateauActuel, String nomCase){
        Couleur couleurPiece = getPiecebyNomCase(nomCase,plateauActuel).getCouleurPiece();

        for (Entry<String,Piece> entry : plateauActuel.entrySet()) {
            Piece pieceActuelle = entry.getValue();
            if (!pieceActuelle.getCouleurPiece().equals(couleurPiece) ){ // Pour chaque piece de couleur adverse
                if(pieceActuelle.getMateriel().equals(Piece.NomMateriel.ROI) ) { // on regarde que nomCase ne soit pas voisine de ce roi
                    ArrayList<String> directionDeplacements = new ArrayList<>() {
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

                    int i = 0;
                    while (i < directionDeplacements.size()) {
                        String nouvelleCase = caseVoisin(nomCase, directionDeplacements.get(i));
                        if (nouvelleCase != null && nouvelleCase.equals(nomCase))
                            return true;
                        i++;
                    }
                } else
                    if( getDeplacementsAutorises(entry.getKey(),plateauActuel).contains(nomCase)) {
                        System.out.println(nomCase + " est menacée par " + entry.getKey() + " !");
                        return true;
                }
            }
        }

        return false;
    }

    /**
     * @param plateauActuel
     * @param caseRoi
     * @return true si le roi en caseRoi est menacé, false sinon
     */
    public static boolean isCheck(Map<String,Piece> plateauActuel, String caseRoi){
        return estMenacee(plateauActuel,caseRoi);
    }

    /**
     * @param plateauActuel
     * @param caseDepart
     * @param caseDestination
     * @return un plateau où un déplacement de la piece de caseDepart à caseDestination a été effectué sans vérification.
     * UTILITE : permet de déplacer une piece puis de voir si c'était un déplacement légal,
     * ou de voir si la situation du joueur est améliorée dans le cadre de l'IA
     */
    public static Map<String, Piece> testDeplacerPiece(Map<String, Piece> plateauActuel,String caseDepart, String caseDestination) {
        System.out.println("--------\nDéplacement SansVerif : "+caseDepart + " -> "+caseDestination + " // "+plateauActuel);
        plateauActuel.put(caseDestination,plateauActuel.get(caseDepart));
        plateauActuel.remove(caseDepart);
        return plateauActuel;
    }


    /**
     * @param plateauActuel
     * @param caseDepart
     * @param caseDestination
     * @return un plateau si le déplacement de caseDepart à caseDestination n'a pas mis le roi de la couleur du joueur en échec.
     * null sinon.
     */
    private static Map<String, Piece> imaginePlateau(Map<String, Piece> plateauActuel, String caseDepart, String caseDestination){
        Couleur maCouleur = plateauActuel.get(caseDepart).getCouleurPiece();
        plateauActuel = testDeplacerPiece(new HashMap<>(plateauActuel),caseDepart,caseDestination);
        if (!isCheck(plateauActuel,getCaseRoi(plateauActuel,maCouleur))) { // Quand je me déplace je ne créé pas d'echec a mon propre roi
            return plateauActuel;
        }
        else
            return null;
    }

    /**
     * @param board
     * @param caseDepart
     * @param caseDestination
     *
     * si la piece en caseDepart peut se rendre en caseDestination et que ca ne met pas son propre roi en echec
     * alors on déplace ladite piece.
     */
    public static void deplacerPiece(Map<String,Piece> board,String caseDepart, String caseDestination ){
        if (getDeplacementsAutorises(caseDepart,board).contains(caseDestination) &&
                imaginePlateau(new HashMap<>(board),caseDepart,caseDestination) != null ) {
            board.put(caseDestination,board.get(caseDepart));
            board.remove(caseDepart);

            // Verifier s'il y a echec ou echec et mat !

        } else {
            System.out.println("Déplacement Illégal !");
        }
    }



    /**
     * @param board
     * @param nomPiece
     * @param couleurJoueur
     * @param caseDestination
     * @return une liste de caseDepart pouvant se rendre en caseDestination
     */
    private static ArrayList<String> piecesConcernees(Map<String,Piece> board, String nomPiece, Couleur couleurJoueur, String caseDestination){
        ArrayList<String> res = new ArrayList<>();

        for (Entry<String,Piece> entry : board.entrySet()){
            Piece pieceActuelle =  entry.getValue();
            if ( pieceActuelle.getCouleurPiece() == couleurJoueur && pieceActuelle.getStringNomMateriel().equals(nomPiece)){
                if (getDeplacementsAutorises(entry.getKey(),board).contains(caseDestination)){
                    res.add(entry.getKey());
                }
            }
        }
        return res;
    }


    /**
     * Transforme un deplacement notation echec en un string contenant caseDepart et caseDestination
     *
     * @param board
     * @param deplacement e4 ; exf ; exf4 ;Qc4 ; Qxc4 ; Rab8 ; R8a7 ; Raxc8 ; R8xc4 ; Qa8c6 ; Qa8xc6
     * @param couleurJoueur
     * @return String: "caseDepart,caseDestination"
     */
    public static String stringToDepartDestination(Map<String, Piece> board, String deplacement, Couleur couleurJoueur){
        String laPiece = "";
        String caseDestination = "";
        char precision = ' ';

        char lettre = deplacement.charAt(0);
        switch (lettre){
            case 'Q' : laPiece = "DAME" ; break;
            case 'K' : laPiece = "ROI" ;  break;
            case 'B' : laPiece = "FOU" ;  break;
            case 'N' : laPiece = "CAVALIER" ;  break;
            case 'R' : laPiece = "TOUR" ;  break;
            case 'a': case 'b' : case 'c' : case 'd' :
                case 'e' : case 'f' : case 'g' : case 'h':
                laPiece = "PION" ; break;
            default : return "ERROR";
        }

        switch (laPiece){
            case "PION" : //e4 ; exf ; exf4 ;
                switch (deplacement.length()){
                    case 2 : caseDestination = deplacement; break;
                    case 3 : System.out.println("Il faut preciser le numero qu'on mange"); break;
                    case 4 :
                        precision = deplacement.charAt(0);
                        caseDestination = ""+deplacement.charAt(2) + deplacement.charAt(3); break;
                }
                break;
            default : //  Qc4 ; Qxc4 ; Rab8 ; R8a7 ; Raxc8 ; R8xc4 ; Qa8c6 ; Qa8xc6
                switch (deplacement.length()){
                    case 3 : caseDestination = ""+deplacement.charAt(1) + deplacement.charAt(2); break;
                    case 4 :
                        if (deplacement.charAt(1) == 'x') caseDestination = ""+deplacement.charAt(2) + deplacement.charAt(3);
                        else {
                            precision = deplacement.charAt(1);
                            caseDestination = ""+deplacement.charAt(2) + deplacement.charAt(3);
                        }
                        break;
                    case 5 :
                        if (deplacement.contains("x")) {
                            precision = deplacement.charAt(1);
                            caseDestination = "" + deplacement.charAt(3) + deplacement.charAt(4);
                        } else
                            return ""+deplacement.charAt(1) + deplacement.charAt(2)+","+deplacement.charAt(3) + deplacement.charAt(4);
                        break;
                    case 6 :
                        return ""+deplacement.charAt(1) + deplacement.charAt(2)+","+deplacement.charAt(4) + deplacement.charAt(5);
                }
        }


        ArrayList<String> piecesDepart = piecesConcernees(board,laPiece,couleurJoueur,caseDestination);
        if (piecesDepart.size() == 1)
            return piecesDepart.get(0)+','+caseDestination;
        else if (piecesDepart.size() > 1){
            ArrayList<String> resDepart = new ArrayList<>();
            for (String s : piecesDepart)
                if (s.contains(""+precision))
                    resDepart.add(s);

            if (resDepart.size() == 1)
                return resDepart.get(0)+','+caseDestination;
            else
                return "ERROR";
        }
        return "ERROR";
    }
}
