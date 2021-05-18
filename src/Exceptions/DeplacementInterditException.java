package Exceptions;

public class DeplacementInterditException extends Exception{

    public DeplacementInterditException(String message){
        super("Erreur saisie Déplacement : "+message);
    }
}
