package Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Partie {
    public static Map<String,Piece> plateau;

    private static ArrayList<String> historiqueCoup;

    private static Map<Map<String,Piece>,Integer> historiquePlateau;

//    private Joueur joueurBlanc; private Joueur joueurNoir;

    public Partie(){
        plateau = new HashMap<>();
        historiqueCoup = new ArrayList<>();
        historiquePlateau = new HashMap<>();

    }

    public static ArrayList<String> getHistoriqueCoup(){return historiqueCoup;}


    public Map<Map<String, Piece>,Integer> getHistoriquePlateau() {
        return historiquePlateau;
    }{
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
