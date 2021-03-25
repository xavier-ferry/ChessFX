package Structure;

public class Partie {
    private final Plateau plateau;
//    private Joueur joueurBlanc; private Joueur joueurNoir;

    public Partie(){
        plateau = new Plateau();
    }

    public Plateau getPlateau(){return plateau; }

    @Override
    public String toString() {
        return "Partie{" +
                "plateau=" + plateau.toString() +
                '}';
    }
}
