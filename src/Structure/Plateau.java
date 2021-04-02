package Structure;

import java.util.ArrayList;
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


    public  Map<String,Piece> getEchiquierParCouleur(Couleur couleurRecherchee) {
        Map <String,Piece> piecesParCouleur = new HashMap<>();
        for (Map.Entry<String, Piece> entry : echiquier.entrySet()) {
            if (entry.getValue().getCouleurPiece() == couleurRecherchee){
                piecesParCouleur.put(entry.getKey(),entry.getValue());
            }
        }
        return piecesParCouleur;
    }

    public Piece getPiecebyNomCase(String nomCase){
        return echiquier.get(nomCase);
    }

    public void deplacerPiece(String caseDepart, String caseDestination){
        echiquier.put(caseDestination,echiquier.get(caseDepart));
        echiquier.remove(caseDepart);
    }

    public ArrayList<String> getDeplacementsAutorises(String nomCase){
        ArrayList<String> res = new ArrayList<String>();
        Piece maPiece = getPiecebyNomCase(nomCase);
        if (maPiece != null) {
            switch (maPiece.getNomMateriel()){
                case "PION" : res = Piece.getDeplacementsPion(nomCase, maPiece, this); break;
                case "TOUR" : res = Piece.getDeplacementsTour(nomCase, maPiece, this); break;
                case "FOU"  : res = Piece.getDeplacementsFou(nomCase, maPiece, this); break;
                case "DAME" : res = Piece.getDeplacementsDame(nomCase, maPiece, this); break;
                case "ROI"  : res = Piece.getDeplacementsRoi(nomCase, maPiece, this); break;
                case "CAVALIER" : res = Piece.getDeplacementsCavalier(nomCase, maPiece, this); break;
            }
        }else {
            res.add("emptyCase");
        }

        return res;
    }
}
