package Models;


public class Piece {
    public enum NomMateriel {PION,TOUR,CAVALIER,FOU,DAME,ROI}
    private final NomMateriel materiel;
    private final Couleur couleurPiece;


    public Piece(NomMateriel maPiece, Couleur maCouleur){
        this.materiel = maPiece;
        this.couleurPiece = maCouleur;
    }

    @Override
    public String toString() {
        String res = "";
        switch (couleurPiece){
            case BLANC: res = materiel +"BLANC";break;
            case NOIR: res = materiel +"NOIR";
        }
        return res;
    }

    public Couleur getCouleurPiece(){return couleurPiece;}
    public NomMateriel getMateriel(){return materiel;}
    public String getStringNomMateriel(){return materiel.toString();}

}

