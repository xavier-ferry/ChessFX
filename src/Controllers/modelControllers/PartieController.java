package Controllers.modelControllers;

import Models.Couleur;
import Models.Partie;
import Models.Piece;

public class PartieController {
    private Partie partie;


    public PartieController(){
        partie = new Partie();
        initialiserPlateau();

    }



    private void initialiserPlateau(){
        // PARTIE BLANC
        partie.plateau.put("a1",new Piece(Piece.NomMateriel.TOUR, Couleur.BLANC));
        partie.plateau.put("b1",new Piece(Piece.NomMateriel.CAVALIER,Couleur.BLANC));
        partie.plateau.put("c1",new Piece(Piece.NomMateriel.FOU,Couleur.BLANC));
        partie.plateau.put("d1",new Piece(Piece.NomMateriel.DAME,Couleur.BLANC));
        partie.plateau.put("e1",new Piece(Piece.NomMateriel.ROI,Couleur.BLANC));
        partie.plateau.put("f1",new Piece(Piece.NomMateriel.FOU,Couleur.BLANC));
        partie.plateau.put("g1",new Piece(Piece.NomMateriel.CAVALIER,Couleur.BLANC));
        partie.plateau.put("h1",new Piece(Piece.NomMateriel.TOUR,Couleur.BLANC));
        for(int i = 0; i < 8; ++i){
            partie.plateau.put((char)((int)'a'+i)+String.valueOf(2),new Piece(Piece.NomMateriel.PION,Couleur.BLANC));
        }

        // PARTIE NOIR
        partie.plateau.put("a8",new Piece(Piece.NomMateriel.TOUR,Couleur.NOIR));
        partie.plateau.put("b8",new Piece(Piece.NomMateriel.CAVALIER,Couleur.NOIR));
        partie.plateau.put("c8",new Piece(Piece.NomMateriel.FOU,Couleur.NOIR));
        partie.plateau.put("d8",new Piece(Piece.NomMateriel.DAME,Couleur.NOIR));
        partie.plateau.put("e8",new Piece(Piece.NomMateriel.ROI,Couleur.NOIR));
        partie.plateau.put("f8",new Piece(Piece.NomMateriel.FOU,Couleur.NOIR));
        partie.plateau.put("g8",new Piece(Piece.NomMateriel.CAVALIER,Couleur.NOIR));
        partie.plateau.put("h8",new Piece(Piece.NomMateriel.TOUR,Couleur.NOIR));
        for(int i = 0; i < 8; ++i){
            partie.plateau.put((char)((int)'a'+i)+String.valueOf(7),new Piece(Piece.NomMateriel.PION,Couleur.NOIR));
        }


    }
    public void ajouterHistoriqueCoup(String coup) {
        partie.getHistoriqueCoup().add(coup);
    }

    public int ajouterPlateauHistorique(){
        /*Integer nb = partie.getHistoriquePlateau().get(Partie.plateau);
        if (nb.equals(null)) nb = 0;
        nb++;
        partie.getHistoriquePlateau().put(Partie.plateau,nb);

        return nb;*/


        return 0;
    }
}
