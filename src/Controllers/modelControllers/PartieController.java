package Controllers.modelControllers;

import Models.Partie;
import Models.Piece;

import java.util.Map;

public class PartieController {
    private Partie partie;


    public PartieController(){
        partie = new Partie();

    }

    public Partie getPartie(){ return partie;}
    public Map<String, Piece> getEchiquier(){return partie.getEchiquier();}

    public void ajouterHistoriqueCoup(String coup) {
        partie.ajouterHistoriqueCoup(coup);
    }
    public int ajouterPlateauHistorique(){ return partie.ajouterHistoriquePlateau();}
}
