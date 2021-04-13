package Models;

import java.util.HashMap;
import java.util.Map;

public class Plateau {
    public Map<String, Piece> echiquier;

    public Plateau(){
        this.echiquier = new HashMap<>();
        initialiserPlateau();
    }

    private void initialiserPlateau(){
        // PARTIE BLANC
        echiquier.put("a1",new Piece(Piece.NomMateriel.TOUR,Couleur.BLANC));
        echiquier.put("b1",new Piece(Piece.NomMateriel.CAVALIER,Couleur.BLANC));
        echiquier.put("c1",new Piece(Piece.NomMateriel.FOU,Couleur.BLANC));
        echiquier.put("d1",new Piece(Piece.NomMateriel.DAME,Couleur.BLANC));
        echiquier.put("e1",new Piece(Piece.NomMateriel.ROI,Couleur.BLANC));
        echiquier.put("f1",new Piece(Piece.NomMateriel.FOU,Couleur.BLANC));
        echiquier.put("g1",new Piece(Piece.NomMateriel.CAVALIER,Couleur.BLANC));
        echiquier.put("h1",new Piece(Piece.NomMateriel.TOUR,Couleur.BLANC));
        for(int i = 0; i < 8; ++i){
            echiquier.put((char)((int)'a'+i)+String.valueOf(2),new Piece(Piece.NomMateriel.PION,Couleur.BLANC));
        }

        // PARTIE NOIR
        echiquier.put("a8",new Piece(Piece.NomMateriel.TOUR,Couleur.NOIR));
        echiquier.put("b8",new Piece(Piece.NomMateriel.CAVALIER,Couleur.NOIR));
        echiquier.put("c8",new Piece(Piece.NomMateriel.FOU,Couleur.NOIR));
        echiquier.put("d8",new Piece(Piece.NomMateriel.DAME,Couleur.NOIR));
        echiquier.put("e8",new Piece(Piece.NomMateriel.ROI,Couleur.NOIR));
        echiquier.put("f8",new Piece(Piece.NomMateriel.FOU,Couleur.NOIR));
        echiquier.put("g8",new Piece(Piece.NomMateriel.CAVALIER,Couleur.NOIR));
        echiquier.put("h8",new Piece(Piece.NomMateriel.TOUR,Couleur.NOIR));
        for(int i = 0; i < 8; ++i){
            echiquier.put((char)((int)'a'+i)+String.valueOf(7),new Piece(Piece.NomMateriel.PION,Couleur.NOIR));
        }


    }

    @Override
    public String toString() {
        return "Plateau{" +
                "board=" + echiquier +
                '}';
    }

    public Map<String, Piece> getEchiquier() { return echiquier; }






}
