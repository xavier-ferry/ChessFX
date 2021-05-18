package Models.Pieces;

import Models.Couleur;
import Models.Piece;
import Models.Plateau;

import java.util.ArrayList;
import java.util.Map;


public class Roi implements Piece {

    private String nomPiece;
    private Couleur couleurPiece;


    public Roi(Couleur couleur){
        this.nomPiece = "ROI";
        this.couleurPiece = couleur;
    }

    @Override
    public ArrayList<String> getCasesAccessibles(Map<String,Piece> board, String nomCase) {
        // TODO : Gérer les roques
        ArrayList<String> directionDeplacements = new ArrayList<>(){
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
        ArrayList<String> res = new ArrayList<>();
        int i = 0;
        while (i < directionDeplacements.size()){
            String nouvelleCase = caseVoisin(nomCase,directionDeplacements.get(i));

            /*if (bouger(nouvelleCase,board) || manger(nouvelleCase,board,roi.getCouleurPiece()))
              // Tester si le roi se retrouve en echec
                 res.add(nouvelleCase);

            i++;*/

            /* Problème avec cette version de la fonction :*/
            if (bouger(board,nouvelleCase) || manger(board,nouvelleCase)){
                // Tester si le roi se retrouve en echec !
                Plateau plateauTest = new Plateau(board);
                plateauTest.deplacerPieceSansVerif(nomCase,nouvelleCase);
                if (! plateauTest.isCheck(nouvelleCase))
/*
                Map<String,Piece> plateauTest = new HashMap<>(board);
                plateauTest = testDeplacerPiece(plateauTest,nomCase,nouvelleCase);
                if (! isCheck(plateauTest,nouvelleCase))
*/
                    res.add(nouvelleCase);
                else
                    System.out.println("Echec si je me déplace en "+nouvelleCase);
            }
            i++;


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
