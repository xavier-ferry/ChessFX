package Deplacements;

public class CaseUtils {
    private static String caseBas(String nomCase){
        char lettre = nomCase.charAt(0);
        int ligne = Character.getNumericValue(nomCase.charAt(1));
        ligne -= 1;

        if ( ligne >= 1 && ligne <= 8 ){
            return "" + lettre + ligne;
        } else {
            return null;
        }
    }
    private static String caseHautGauche(String nomCase){
        char lettre = nomCase.charAt(0);
        int ligne = Character.getNumericValue(nomCase.charAt(1));

        char lettreGauche = (char) ((int)lettre -1);
        ligne += 1;

        if ( ligne >= 1 && ligne <= 8 && lettreGauche >= 'a'){
            return "" + lettreGauche + ligne;
        } else {
            return null;
        }
    }
    private static String caseHautDroite(String nomCase){
        char lettre = nomCase.charAt(0);
        int ligne = Character.getNumericValue(nomCase.charAt(1));

        char lettreDroite = (char) ((int)lettre +1);
        ligne += 1;

        if ( ligne >= 1 && ligne <= 8 && lettreDroite <= 'h'){
            return "" + lettreDroite + ligne;
        } else {
            return null;
        }
    }
    private static String caseBasDroite(String nomCase){
        char lettre = nomCase.charAt(0);
        int ligne = Character.getNumericValue(nomCase.charAt(1));

        char lettreDroite = (char) ((int)lettre +1);
        ligne -= 1;

        if ( ligne >= 1 && ligne <= 8 && lettreDroite <= 'h'){
            return "" + lettreDroite + ligne;
        } else {
            return null;
        }
    }
    private static String caseBasGauche(String nomCase){
        char lettre = nomCase.charAt(0);
        int ligne = Character.getNumericValue(nomCase.charAt(1));

        char lettreGauche = (char) ((int)lettre -1);
        ligne -= 1;

        if ( ligne >= 1 && ligne <= 8 && lettreGauche >= 'a'){
            return "" + lettreGauche + ligne;
        } else {
            return null;
        }
    }
    private static String caseGauche(String nomCase){
        char lettre = nomCase.charAt(0);
        int ligne = Character.getNumericValue(nomCase.charAt(1));

        char lettreGauche = (char) ((int)lettre -1);
        if ( ligne >= 1 && ligne <= 8 && lettreGauche >= 'a'){
            return "" + lettreGauche + ligne;
        } else {
            return null;
        }
    }
    private static String caseDroite(String nomCase){
        char lettre = nomCase.charAt(0);
        int ligne = Character.getNumericValue(nomCase.charAt(1));

        char lettreDroite = (char) ((int)lettre +1);
        if ( ligne >= 1 && ligne <= 8 && lettreDroite <= 'h'){
            return "" + lettreDroite + ligne;
        } else {
            return null;
        }
    }
    private static String caseHaut(String nomCase){
        char lettre = nomCase.charAt(0);
        int ligne = Character.getNumericValue(nomCase.charAt(1));
        ligne += 1;
        if ( ligne >= 1 && ligne <= 8){
            return "" + lettre + ligne;
        } else {
            return null;
        }
    }
}