package Structure;

import java.util.ArrayList;
import java.util.Map;

public class Partie {
    private final Plateau plateau;

    private static ArrayList<String> historiqueCoup;

    private static Map<Map<String,Piece>,Integer> historiquePlateau;

//    private Joueur joueurBlanc; private Joueur joueurNoir;

    public Partie(){
        plateau = new Plateau();
    }

    public Plateau getPlateau(){return plateau; }

    public static ArrayList<String> getHistoriqueCoup(){return historiqueCoup;}
    public static void setHistoriqueCoup(ArrayList<String> historiqueCoup) {
        Partie.historiqueCoup = historiqueCoup;
    }

    public static Map<Map<String, Piece>,Integer> getHistoriquePlateau() {
        return historiquePlateau;
    }
    public static void setHistoriquePlateau(Map< Map<String, Piece>,Integer> historiquePlateau) {
        Partie.historiquePlateau = historiquePlateau;
    }

    @Override
    public String toString() {
        return "Partie{" +
                "plateau=" + plateau.toString() +
                "historiqueCoup = " + historiqueCoup +
                '}';
    }
}
