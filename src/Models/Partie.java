package Models;

import Exceptions.DeplacementInterditException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Partie {
    private Plateau plateau;

    private  Couleur joueurActif;
    private  ArrayList<String> historiqueCoup;
    private  Map<Map<String,Piece>,Integer> historiquePlateau;

//    private Joueur joueurBlanc; private Joueur joueurNoir;

    public Partie(){
        plateau = new Plateau();
        historiqueCoup = new ArrayList<>();
        historiquePlateau = new HashMap<>();
        joueurActif = Couleur.BLANC;

    }
    public Couleur getJoueurActif(){return joueurActif;}
    public void changerJoueur(){ joueurActif = joueurActif.getCouleurOpposee();}

    public void ajouterHistoriqueCoup(String coup){historiqueCoup.add(coup);}
    public int ajouterHistoriquePlateau(){
        if (historiquePlateau.get(plateau.getEchiquier()) == null) {
            historiquePlateau.put(plateau.getEchiquier(), 1);
            return 1;
        }
        else {
            Integer res = Integer.valueOf(historiquePlateau.get(plateau.getEchiquier()));
            historiquePlateau.replace(plateau.getEchiquier(), res + 1);

            return res.intValue()+1;
        }
    }

    public Plateau getPlateau() {
        return plateau;
    }
    public Map<String, Piece> getEchiquier(){return plateau.getEchiquier();}

    @Override
    public String toString() {
        return "Partie{" +
                "plateau=" + plateau.toString() +
                "historiqueCoup = " + historiqueCoup +
                '}';
    }

    public void jouerCoup(String caseDepart, String caseDestination) throws DeplacementInterditException {
        if (getEchiquier().get(caseDepart).getCouleurPiece() != joueurActif)
            throw new DeplacementInterditException("C'est au joueur "+joueurActif+" de jouer.");
        else
            getPlateau().deplacerPiece(caseDepart,caseDestination);
    }

    /**
     * Retourne un entier correspondant au statut de la partie
     * @param nbRepetition pour vérifier le Nul par répétition
     * @return -1 : nul | 0 : en cours | 1 : echec | 2 : echec et mat
     */
    public int statutPartie(int nbRepetition){
        Couleur couleurOpposee = joueurActif.getCouleurOpposee();
        String caseRoi = getPlateau().getCaseRoi(couleurOpposee);
        if (getPlateau().isCheck(caseRoi)) {
            if (getPlateau().isCheckMate(caseRoi))
                return 2;
            return 1;
        } else { // On veut vérifier qu'il n'y a pas Nul.
            // TODO : Vérifier le Pat, le nul par répétition, le nul par manque de matériel, la règle des 50 coups.
            if (nbRepetition == 3 || plateau.joueurOpposeEstPat(joueurActif.getCouleurOpposee()) || plateau.deuxJoueursManquentDeMateriel())
                return -1;

            // TODO : Possibilité de limiter la partie à 60 coups pour le moment ...
        }
        return 0;
    }




}
