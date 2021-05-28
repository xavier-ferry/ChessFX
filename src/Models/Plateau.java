package Models;

import Exceptions.DeplacementInterditException;
import Models.Pieces.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Plateau {

    private Map<String, Piece> echiquier;

    public Plateau(){
        this.echiquier = new HashMap<>();
        initialiserPlateau();
    }
    public Plateau(Map <String,Piece> plateauACopier){
        this.echiquier = new HashMap<>(plateauACopier);
    }

    private void initialiserPlateau(){
        // PARTIE BLANC
        echiquier.put("a1",new Tour(Couleur.BLANC));
        echiquier.put("b1",new Cavalier(Couleur.BLANC));
        echiquier.put("c1",new Fou(Couleur.BLANC));
        echiquier.put("d1",new Dame(Couleur.BLANC));
        echiquier.put("e1",new Roi(Couleur.BLANC));
        echiquier.put("f1",new Fou(Couleur.BLANC));
        echiquier.put("g1",new Cavalier(Couleur.BLANC));
        echiquier.put("h1",new Tour(Couleur.BLANC));
        for(int i = 0; i < 8; ++i){
            echiquier.put((char)((int)'a'+i)+String.valueOf(2),new Pion(Couleur.BLANC));
        }

        // PARTIE NOIR
        echiquier.put("a8",new Tour(Couleur.NOIR));
        echiquier.put("b8",new Cavalier(Couleur.NOIR));
        echiquier.put("c8",new Fou(Couleur.NOIR));
        echiquier.put("d8",new Dame(Couleur.NOIR));
        echiquier.put("e8",new Roi(Couleur.NOIR));
        echiquier.put("f8",new Fou(Couleur.NOIR));
        echiquier.put("g8",new Cavalier(Couleur.NOIR));
        echiquier.put("h8",new Tour(Couleur.NOIR));
        for(int i = 0; i < 8; ++i){
            echiquier.put((char)((int)'a'+i)+String.valueOf(7),new Pion(Couleur.NOIR));
        }


    }
    @Override
    public String toString() {
        String blancs = "";
        String noirs = "";

        for (Map.Entry<String,Piece> entry : echiquier.entrySet()){
            switch (entry.getValue().getCouleurPiece()){
                case NOIR: noirs+= entry.toString() + " - "; break;
                case BLANC: blancs += entry.toString() + " - ";
            }
        }

        return "---\nPlateau{" +
                "board=\n" + blancs +"\n"+noirs+
                "}\n---\n";
    }
    public Map<String, Piece> getEchiquier() { return echiquier; }
    public String getCaseRoi(Couleur couleurRecherchee){
        for (Map.Entry<String,Piece> entry : echiquier.entrySet()){
            Piece pieceActuelle =  entry.getValue();

            if ( pieceActuelle.getCouleurPiece() == couleurRecherchee && pieceActuelle.getNomPiece().equals("ROI")){
                return entry.getKey();
            }
        }
        return "ERROR";
    }

    /**
     * Transforme un deplacement notation echec en un string contenant caseDepart et caseDestination
     *
     * @param deplacement e4 ; exf ; exf4 ;Qc4 ; Qxc4 ; Rab8 ; R8a7 ; Raxc8 ; R8xc4 ; Qa8c6 ; Qa8xc6
     * @param couleurJoueur
     * @return String: "caseDepart,caseDestination"
     */
    public String[] stringToDepartDestination(String deplacement, Couleur couleurJoueur) throws DeplacementInterditException{
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
            default : throw new DeplacementInterditException("la lettre ne concerne aucune pièce");
        }

        switch (laPiece){
            case "PION" : //e4 ; exf ; exf4 ;
                switch (deplacement.length()){
                    case 2 : caseDestination = deplacement; break;
                    case 3 : throw new DeplacementInterditException("Il faut preciser le numero qu'on mange");
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
                            return new String[] {""+deplacement.charAt(1) + deplacement.charAt(2),""+deplacement.charAt(3) + deplacement.charAt(4)};
                        break;
                    case 6 :
                        return new String[] {""+deplacement.charAt(1) + deplacement.charAt(2),""+deplacement.charAt(4) + deplacement.charAt(5)};
                }
        }


        ArrayList<String> piecesDepart = piecesConcernees(laPiece,couleurJoueur,caseDestination);
        if (piecesDepart.size() == 1)
            return new String[] {piecesDepart.get(0),caseDestination};
            //return piecesDepart.get(0)+','+caseDestination;
        else if (piecesDepart.size() > 1){
            ArrayList<String> resDepart = new ArrayList<>();
            for (String s : piecesDepart)
                if (s.contains(""+precision))
                    resDepart.add(s);

            if (resDepart.size() == 1)
                return new String[] {resDepart.get(0),caseDestination};
                //return resDepart.get(0)+','+caseDestination;
            else
                throw new DeplacementInterditException("Plusieurs choix possibles");
        }
        throw new DeplacementInterditException("Pas de choix possible");
    }


    /**
     * @param caseRoi
     * @return un booléen correspondant au fait que le roi en caseRoi est echec et mat
     */
    public boolean isCheckMate( String caseRoi) {
        Piece monRoi = echiquier.get(caseRoi);

        ArrayList<String> destAutorises = monRoi.getCasesAccessibles(echiquier,caseRoi);
        for (String uneDestination : destAutorises){
            if (!estMenacee(uneDestination,monRoi.getCouleurPiece()))
                return false;
        }
        //Si je n'ai pas trouvé de déplacement autorisé pour mon roi :

        for (Map.Entry<String,Piece> entry : echiquier.entrySet()){
            if (entry.getValue().getCouleurPiece() == monRoi.getCouleurPiece()){ // Tentative d'interception
                for (String destinationSauvetage : entry.getValue().getCasesAccessibles(getEchiquier(),entry.getKey())) {
                    Plateau plateauTest = new Plateau(this.echiquier);
                    assert(plateauTest.getEchiquier() != null);
                    Map<String,Piece> mapDebug = plateauTest.imaginePlateau(entry.getKey(),destinationSauvetage);
                    if( mapDebug != null)
                        return false;
                }
            }
        }
        return true;
    }

    /**
     * @param nomCase
     * @return le nombre de pièces alliées qui protègent nomCase
     */
    public int estProtegee(String nomCase){
        int res = 0;
        Couleur couleurPiece = echiquier.get(nomCase).getCouleurPiece();

        for (Map.Entry<String,Piece> entry : echiquier.entrySet()) {
            Piece pieceActuelle = entry.getValue();
            if (pieceActuelle.getCouleurPiece().equals(couleurPiece)) { // Pour chaque piece de couleur adverse
                if (echiquier.get(entry.getKey()).getCasesDefendues(echiquier,entry.getKey()).contains(nomCase))
                    res ++;
            }
        }
        return res;
    }

    /**
     * @param nomCase, couleurMenancante
     * @return un booléen qui indique si la pièce se trouvant en nomCase risque de se faire manger.
     * S'il n'existe pas de piece en nomCase on a besoin de connaitre la couleur qui peut être menacer
     */
    public boolean estMenacee(String nomCase, Couleur couleurMenacee){
        for (Map.Entry<String,Piece> entry : echiquier.entrySet()) {
            Piece pieceActuelle = entry.getValue();
            if (!pieceActuelle.getCouleurPiece().equals(couleurMenacee) ){ // Pour chaque piece de couleur adverse
                if(pieceActuelle instanceof Roi){
                    if( pieceActuelle.getCasesDefendues(echiquier,entry.getKey()).contains(nomCase)) { // TODO : Problème si Deux Roi cote a cote ?
                        //if (estProtegee(nomCase) == 0)
                            return true;
                    }
                }else {
                    if( pieceActuelle.getCasesAccessibles(echiquier,entry.getKey()).contains(nomCase)) {
                        return true;
                    }
                }

            }
        }

        return false;
    }

    /**
     * @param caseRoi
     * @return true si le roi en caseRoi est menacé, false sinon
     */
    public boolean isCheck(String caseRoi){
        return estMenacee(caseRoi, echiquier.get(caseRoi).getCouleurPiece());
    }


    /**
     * @param caseDepart
     * @param caseDestination
     * @return un plateau où un déplacement de la piece de caseDepart à caseDestination a été effectué sans vérification.
     * UTILITE : permet de déplacer une piece puis de voir si c'était un déplacement légal,
     * ou de voir si la situation du joueur est améliorée dans le cadre de l'IA
     */
    public Map<String, Piece> deplacerPieceSansVerif(String caseDepart, String caseDestination) {
        echiquier.put(caseDestination,echiquier.get(caseDepart));
        echiquier.remove(caseDepart);
        return echiquier;
    }



    /**
     * @param caseDepart
     * @param caseDestination
     * @return un plateau si le déplacement de caseDepart à caseDestination n'a pas mis le roi de la couleur du joueur en échec.
     * null sinon.
     */
    private Map<String, Piece> imaginePlateau(String caseDepart, String caseDestination){
        Couleur couleurJoueurActif = echiquier.get(caseDepart).getCouleurPiece();
        Plateau plateauTest = new Plateau(echiquier);
        plateauTest.deplacerPieceSansVerif(caseDepart,caseDestination);
        if ( ! plateauTest.isCheck(plateauTest.getCaseRoi(couleurJoueurActif)))
            return plateauTest.echiquier;
        else
            return null;
    }


    /**
     * @param caseDepart
     * @param caseDestination
     *
     * si la piece en caseDepart peut se rendre en caseDestination et que ca ne met pas son propre roi en echec
     * alors on déplace ladite piece.
     */
    public void deplacerPiece(String caseDepart, String caseDestination ) throws DeplacementInterditException{

        if (echiquier.get(caseDepart).getCasesAccessibles(echiquier,caseDepart).contains(caseDestination) &&
        imaginePlateau(caseDepart,caseDestination) != null) {
            echiquier.put(caseDestination,echiquier.get(caseDepart));
            echiquier.remove(caseDepart);

            if (echiquier.get(caseDestination) instanceof Roi || echiquier.get(caseDestination) instanceof Tour){
                echiquier.get(caseDestination).setHasMoved(); // TODO : gérer le roque
            }
        } else {
            throw new DeplacementInterditException("Déplacement Illégal !");
        }
    }

    /**
     * @param nomPiece
     * @param couleurJoueur
     * @param caseDestination
     * @return une liste de caseDepart pouvant se rendre en caseDestination
     */
    private ArrayList<String> piecesConcernees(String nomPiece, Couleur couleurJoueur, String caseDestination) throws DeplacementInterditException {
        if (echiquier.get(caseDestination) != null && echiquier.get(caseDestination).getCouleurPiece() == couleurJoueur)
            throw new DeplacementInterditException("La case destination est déjà controlée par les "+couleurJoueur.toString());

        ArrayList<String> res = new ArrayList<>();

        for (Map.Entry<String,Piece> entry : echiquier.entrySet()){
            Piece pieceActuelle =  entry.getValue();
            if ( pieceActuelle.getCouleurPiece() == couleurJoueur && pieceActuelle.getNomPiece().equals(nomPiece)){
                if (pieceActuelle.getCasesAccessibles(this.echiquier,entry.getKey()).contains(caseDestination)){
                    res.add(entry.getKey());
                }
            }
        }
        return res;
    }


    public boolean joueurOpposeEstPat(Couleur couleurJoueur){
        for (Map.Entry<String,Piece> entry : echiquier.entrySet()) {
            Piece pieceActuelle = entry.getValue();
            if (pieceActuelle.getCouleurPiece() == couleurJoueur &&
                    pieceActuelle.getCasesAccessibles(echiquier,entry.getKey()).size() > 0 )
                return false;
        }
        return true;
    }

    public boolean deuxJoueursManquentDeMateriel() {
        int cavalierBlanc = 0;
        int cavalierNoir = 0;
        int fouBlanc = 0;
        int fouNoir = 0;

        boolean blancEnManque = false;
        boolean noirEnManque = false;

        for (Map.Entry<String, Piece> entry : getEchiquier().entrySet()) {
            Piece pieceActuelle = entry.getValue();
            if (pieceActuelle instanceof Dame)
                return false;
            if (pieceActuelle instanceof Tour)
                return false;
            if (pieceActuelle instanceof Pion)
                return false;
            if (pieceActuelle instanceof Cavalier && pieceActuelle.getCouleurPiece() == Couleur.BLANC)
                cavalierBlanc++;
            if (pieceActuelle instanceof Cavalier && pieceActuelle.getCouleurPiece() == Couleur.NOIR)
                cavalierNoir++;
            if (pieceActuelle instanceof Fou && pieceActuelle.getCouleurPiece() == Couleur.BLANC)
                fouBlanc++;
            if (pieceActuelle instanceof Fou && pieceActuelle.getCouleurPiece() == Couleur.NOIR)
                fouNoir++;
        }
        if ((cavalierBlanc == 2 && fouBlanc == 0) || (cavalierBlanc == 0 && fouBlanc == 1)) blancEnManque = true;
        if ((cavalierNoir == 2 && fouNoir == 0) || (cavalierNoir == 0 && fouNoir == 1)) noirEnManque = true;


        return blancEnManque && noirEnManque;
    }
}