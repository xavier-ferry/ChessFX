package Controllers;

import Controllers.modelControllers.PartieController;
import Controllers.viewControllers.*;
import Exceptions.DeplacementInterditException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;

import java.io.*;

public class SuperController {
    private BorderPane borderPane;

    private PartieController partieController;

    private ControllerDeroulement controllerDeroulement;
    private ControllerEchiquier controllerEchiquier;
    private ControllerDeplacement controllerDeplacement;
    private ControllerStatut controllerStatut;
    private ControllerDebug controllerDebug;


    public SuperController() throws IOException {
        //TODO : Gerer le lancement d'une nouvelle partie onClick pour eviter de relancer l'application
        System.out.println("SuperController - Constructeur");

        partieController = new PartieController();
        borderPane = new BorderPane();

        String pathToViews = "../Views/";
        FXMLLoader deroulementLoader = new FXMLLoader(getClass().getResource(pathToViews+"deroulement.fxml"));
        FXMLLoader echiquierLoader = new FXMLLoader(getClass().getResource(pathToViews+"echiquier.fxml"));
        FXMLLoader deplacementLoader = new FXMLLoader(getClass().getResource(pathToViews+"deplacement.fxml"));
        FXMLLoader statutLoader = new FXMLLoader(getClass().getResource(pathToViews+"statut.fxml"));
        FXMLLoader debugLoader = new FXMLLoader(getClass().getResource(pathToViews+"debug.fxml"));


        borderPane.setLeft(deroulementLoader.load());
        borderPane.setCenter(echiquierLoader.load());
        borderPane.setRight(deplacementLoader.load());
        borderPane.setTop(statutLoader.load());
        borderPane.setBottom(debugLoader.load());


        this.controllerDeroulement = deroulementLoader.getController();
        this.controllerEchiquier = echiquierLoader.getController();
        this.controllerDeplacement = deplacementLoader.getController();
        this.controllerStatut = statutLoader.getController();
        this.controllerDebug = debugLoader.getController();

        controllerEchiquier.createPlateuDeJeu(partieController.getEchiquier());

        // Initialisation des actions sur les différents Button et Textfields
        initialiserDep();
        initialiserChargementFichier();
        initialiserNouvellePartie();

    }


    public BorderPane getBorderPane() {
        return borderPane;
    }

    public void initialiserDep(){
        controllerDeplacement.dep.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 6 ? change : null));
        controllerDeplacement.dep.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                demandeDeplacement(controllerDeplacement.dep.getText(0,controllerDeplacement.dep.getLength()-1));
                 controllerDeplacement.dep.setText("");
            }
        });
    }
    public void initialiserChargementFichier(){
        controllerStatut.chargementFichier.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                lectureFichier(controllerStatut.chargementFichier.getText(0, controllerStatut.chargementFichier.getLength()-1)); // -1 pour retirer l'ENTRER
                controllerStatut.chargementFichier.setText("");
            }
        });
    }
    public void initialiserNouvellePartie(){
        controllerStatut.nouvellePartie.setOnAction(actionEvent -> {
            System.out.println("Nouvelle partie demandée");
            lancerNouvellePartie();
        });
    }

    private void lancerNouvellePartie(){

    }

    private void demandeDeplacement(String deplacement){

        //String deplacement = controllerDeplacement.dep.getText();

        try { // Si la saisie du déplacement est correcte.
            String [] cases = partieController.getPartie().getPlateau().stringToDepartDestination(deplacement, partieController.getPartie().getJoueurActif());
            String depart =cases[0];
            String destination = cases[1];

            System.out.println("TEST APRES saisie déplacement");

            partieController.getPartie().jouerCoup(depart,destination);
            controllerEchiquier.updateGridPanel(depart,destination,partieController.getEchiquier());
            controllerDeroulement.updateCoupPartie(deplacement);
            partieController.ajouterHistoriqueCoup(deplacement);
            int nbRep = partieController.ajouterPlateauHistorique(); // Retourne le nombre de répétition pour eviter un parcours supplémentaire plus tard

            String statutPartie;

            switch (partieController.getPartie().statutPartie(nbRep)){
                case -1 : statutPartie = "Nul"; break;
                case 1 : statutPartie = "Echec aux "+partieController.getPartie().getJoueurActif().getCouleurOpposee().toString(); break;
                case 2 : statutPartie = "Echec et mat. Victoire du joueur "+partieController.getPartie().getJoueurActif(); break;
                default: // case 0
                    statutPartie = "Partie en cours ...";
            }



            controllerDebug.setStatutDebug(statutPartie);

            partieController.getPartie().changerJoueur();

        } catch (DeplacementInterditException e) { // Sinon on met juste à jour l'affichage en bas et on continue d'attendre un coup correct.
            controllerDebug.setStatutDebug(e.getMessage());
        }

    }



    private void lectureFichier(String filename){
        String pathToExemplePartie = "/Users/xavierwork/IdeaProjects/ChessFX/exemplePartie/";
        String extensionFichier = ".txt";

        File fichier =new File(pathToExemplePartie+filename+extensionFichier);
        FileReader fileR = null;
        try {
            fileR = new FileReader(fichier);
            BufferedReader bufferedR = new BufferedReader(fileR);

            String s;
            String[] mots =null;

            while((s=bufferedR.readLine())!=null) {
                mots=s.split(" ");
                for (String wrd : mots) {
                    if (! (wrd.charAt(1) == '.')){ // Si on est pas dans le cadre d'un numéro de coup
                        demandeDeplacement(wrd);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


}
