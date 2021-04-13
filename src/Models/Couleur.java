package Models;

public enum Couleur {NOIR,BLANC;

    public Couleur getCouleurOpposee() {
        if (this == BLANC) return NOIR;
        else return BLANC;
    }

}

