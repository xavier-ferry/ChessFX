package Structure;

import java.util.ArrayList;
import java.util.Map;


public class Piece {
    public enum NomMateriel {PION,TOUR,CAVALIER,FOU,DAME,ROI}
    private final NomMateriel piece;
    private final Couleur couleurPiece;


    public Piece(NomMateriel maPiece, Couleur maCouleur){
        this.piece = maPiece;
        this.couleurPiece = maCouleur;
    }

    @Override
    public String toString() {
        String res = "";
        switch (couleurPiece){
            case BLANC: res = piece+"BLANC";break;
            case NOIR: res = piece+"NOIR";
        }
        return res;
    }

    public Couleur getCouleurPiece(){return couleurPiece;}
    public String getNomMateriel(){return piece.toString();}

}

