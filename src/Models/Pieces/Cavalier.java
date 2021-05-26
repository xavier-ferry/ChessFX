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
        ArrayList<String> casesATester = getCasesDefendues(board,nomCase);

        for (String s : casesATester) {
            if (bouger(board,s) || manger(board,s))
                res.add(s);
        }

        return res;
    }

    @Override
    public ArrayList<String> getCasesDefendues(Map<String,Piece> board, String nomCase){
        ArrayList<String> res = new ArrayList<>();
        char lettreCase = nomCase.charAt(0);
        int numeroCase = Character.getNumericValue(nomCase.charAt(1));

        for (int i = -2 ; i <= 2 ; i+= 4) {
            char nouvelleLettreCase = (char) ((int) lettreCase + i);
            if (nouvelleLettreCase >= 'a' && nouvelleLettreCase <= 'h') {
                if (numeroCase > 1) {
                    res.add("" + nouvelleLettreCase + ((char) numeroCase - 1));
                }
                if (numeroCase < 8) {
                    res.add("" + nouvelleLettreCase + ((char) numeroCase + 1));
                }
            }
        }
        for (int i = -2 ; i <= 2 ; i+= 4) {
            if (numeroCase + i <= 8 && numeroCase + i >= 1) {
                char nouvelleLettreCase = (char) ((int) lettreCase - 1);
                if (lettreCase >= 'a'){
                    res.add("" + nouvelleLettreCase + ((char) numeroCase + i));
                }
                nouvelleLettreCase = (char) ((int) lettreCase + 1);
                if (lettreCase <= 'h'){
                    res.add("" + nouvelleLettreCase + ((char) numeroCase + i));
                }
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
