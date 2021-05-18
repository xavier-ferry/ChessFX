package Models.Pieces;

import Models.Couleur;
import Models.Piece;

import java.util.ArrayList;
import java.util.Map;

public class Cavalier implements Piece {

    private String nomPiece;
    private Couleur couleurPiece;


    public Cavalier(Couleur couleur){
        this.nomPiece = "CAVALIER";
        this.couleurPiece = couleur;
    }

    @Override
    public ArrayList<String> getCasesAccessibles(Map<String,Piece> board, String nomCase) {
        ArrayList<String> res = new ArrayList<>();
        ArrayList<String> casesATester = new ArrayList<>();
        char lettreCase = nomCase.charAt(0);
        int numeroCase = Character.getNumericValue(nomCase.charAt(1));

        for (int i = -2 ; i <= 2 ; i+= 4) {
            char nouvelleLettreCase = (char) ((int) lettreCase + i);
            if (nouvelleLettreCase >= 'a' && nouvelleLettreCase <= 'h') {
                if (numeroCase > 1) {
                    casesATester.add("" + nouvelleLettreCase + ((char) numeroCase - 1));
                }
                if (numeroCase < 8) {
                    casesATester.add("" + nouvelleLettreCase + ((char) numeroCase + 1));
                }
            }
        }
        for (int i = -2 ; i <= 2 ; i+= 4) {
            if (numeroCase + i <= 8 && numeroCase + i >= 1) {
                char nouvelleLettreCase = (char) ((int) lettreCase - 1);
                if (lettreCase >= 'a'){
                    casesATester.add("" + nouvelleLettreCase + ((char) numeroCase + i));
                }
                nouvelleLettreCase = (char) ((int) lettreCase + 1);
                if (lettreCase <= 'h'){
                    casesATester.add("" + nouvelleLettreCase + ((char) numeroCase + i));
                }
            }
        }
        for (String s : casesATester) {
            if (bouger(board,s) || manger(board,s)) {
                res.add(s);
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
