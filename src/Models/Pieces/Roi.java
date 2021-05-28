package Models.Pieces;

import Models.Couleur;
import Models.Piece;
import Models.Plateau;

import java.util.ArrayList;
import java.util.Map;


public class Roi implements Piece {

    private String nomPiece;
    private Couleur couleurPiece;
    private boolean hasMoved;


    public Roi(Couleur couleur){
        this.nomPiece = "ROI";
        this.couleurPiece = couleur;
        this.hasMoved = false;
    }

    public void setHasMoved(){hasMoved = true;}


    @Override
    public ArrayList<String> getDirections(){
        return new ArrayList<>(){
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
    }

    @Override
    public ArrayList<String> getCasesDefendues(Map<String,Piece> board, String nomCase){
        ArrayList<String> directionDeplacements = getDirections();
        ArrayList<String> res = new ArrayList<>();

        for (String maDirection : directionDeplacements){
            String nouvelleCase = caseVoisin(nomCase, maDirection);
            if (nouvelleCase != null)
                res.add(nouvelleCase);
        }

        return res;
    }

    @Override
    public ArrayList<String> getCasesAccessibles(Map<String,Piece> board, String nomCase) {
        // TODO : Gérer les roques : ajouter un attribut booleen aDejaBougé au Roi et aux Tours.
        // TODO : Gérer la prise en passant
        // TODO : vérifier que cette version fonctionne

        ArrayList<String> res = new ArrayList<>();
        for (String nouvelleCase : getCasesDefendues(board,nomCase)){

            if (bouger(board,nouvelleCase) || manger(board,nouvelleCase)){
                // Tester si le roi se retrouve en echec !
                Plateau plateauTest = new Plateau(board);
                plateauTest.deplacerPieceSansVerif(nomCase,nouvelleCase);
                if (! plateauTest.isCheck(nouvelleCase))
                    res.add(nouvelleCase);
            }


        }
        return res;
    }

    public String toString(){
        return nomPiece+couleurPiece.toString();
    }

    @Override
    public Couleur getCouleurPiece() {
        return couleurPiece;
    }

    @Override
    public String getNomPiece() {
        return nomPiece;
    }
}
