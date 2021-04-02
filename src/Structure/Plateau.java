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
    public Piece getPiecebyNomCase(String nomCase){
        return echiquier.get(nomCase);
    }

    public ArrayList<String> getDeplacementsAutorises(String nomCase){
        ArrayList<String> res = new ArrayList<>();
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

    public String getCaseRoi(Map<String,Piece> plateauActuel, Couleur maCouleur){
        String res = "ERROR";
        for (Map.Entry entry : plateauActuel.entrySet()){
            Piece pieceActuelle =  (Piece) entry.getValue();

            if ( pieceActuelle.getCouleurPiece() == maCouleur && pieceActuelle.getNomMateriel()=="ROI"){
                res = (String) entry.getKey();
            }
        }
        return res;
    }

    public boolean isCheck(Map<String,Piece> plateauActuel, String caseRoi){
        System.out.println("Test isCheck - "+ caseRoi);
        Couleur couleurRoi = getPiecebyNomCase(caseRoi).getCouleurPiece();
        for (Map.Entry entry : plateauActuel.entrySet()){
            Piece pieceActuelle =  (Piece) entry.getValue();
            System.out.println("d2"+ plateauActuel.get("d2"));
            if (pieceActuelle.getCouleurPiece().equals("NOIR") && pieceActuelle.getNomMateriel().equals("DAME"))
                System.out.println("######## "+plateauActuel.get("d2")+" ---- "+getDeplacementsAutorises((String)entry.getKey()));
            //System.out.println("Déplacements autorisés de "+entry.getKey() + " = " +getDeplacementsAutorises((String) entry.getKey()));
            if ( !pieceActuelle.getCouleurPiece().equals(couleurRoi) && getDeplacementsAutorises((String) entry.getKey()).contains(caseRoi)){
                System.out.println("ECHEC sur les "+couleurRoi.toString());
                return true;
            }
        }
        System.out.println("Pas d'ECHEC sur les "+couleurRoi.toString());
        return false;
    }


    public Map<String, Piece> testDeplacerPiece(Map<String, Piece> plateauActuel,String caseDepart, String caseDestination) {
        System.out.println("Déplacement test : "+caseDepart + " -> "+caseDestination);
        plateauActuel.put(caseDestination,plateauActuel.get(caseDepart));
        plateauActuel.remove(caseDepart);
        return plateauActuel;
    }

    public Map<String, Piece> imaginePlateau(Map<String, Piece> plateauActuel, String caseDepart, String caseDestination){
        Couleur maCouleur = plateauActuel.get(caseDepart).getCouleurPiece();
        System.out.println("\n----\nAvant testDéplacement "+caseDepart+" -> "+caseDestination+" ----\nCase D2 : "+plateauActuel.get("d2") +"\nCase D3 : "+plateauActuel.get("d3") +"\nCase D4 : "+plateauActuel.get("d4"));
        plateauActuel = testDeplacerPiece(new HashMap<>(plateauActuel),caseDepart,caseDestination);
        System.out.println("Après testDéplacement ----\nCase D2 : "+plateauActuel.get("d2") +"\nCase D3 : "+plateauActuel.get("d3") +"\nCase D4 : "+plateauActuel.get("d4")+"\n----\n");
        if (!isCheck(plateauActuel,getCaseRoi(plateauActuel,maCouleur))) {
            System.out.println("Le roi "+ maCouleur+" n'est pas en echec");
            return plateauActuel;
        }
        else
            return null;
    };

    public void deplacerPiece(String caseDepart, String caseDestination){
        if (getDeplacementsAutorises(caseDepart).contains(caseDestination) &&
        imaginePlateau(new HashMap<>(echiquier),caseDepart,caseDestination) != null ) {
            echiquier.put(caseDestination,echiquier.get(caseDepart));
            echiquier.remove(caseDepart);
        } else {
            System.out.println("Déplacement Illégal !");
        }
        System.out.println("D3 = "+echiquier.get("d3"));
        System.out.println("D2 = "+echiquier.get("d2"));
        System.out.println("D4 = "+echiquier.get("d4"));
    }


}
