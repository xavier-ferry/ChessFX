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
        System.out.println("SuperController - Constructeur");


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


        // Initialisation des actions sur les diff??rents Button et Textfields
        initialiserDep();
        initialiserChargementFichier();
        initialiserNouvellePartie();
        initialiserAfficherEchiquier();


        lancerNouvellePartie(true);
    }


    public BorderPane getBorderPane() {
        return borderPane;
    }

    private void lancerNouvellePartie(boolean premierePartie){
        this.partieController = new PartieController();

        if (premierePartie)
            controllerEchiquier.creerPlateauDeJeu(partieController.getEchiquier());
        else {
            // TODO : remettre tout les affichages ?? 0.
            controllerEchiquier.updateTotalGridPane(partieController.getEchiquier());
            controllerDeroulement.initialize();
        }


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
            System.out.println("Nouvelle partie demand??e");
            lancerNouvellePartie(false);
        });
    }
    public void initialiserAfficherEchiquier(){
        controllerDebug.afficherEchiquier.setOnAction(actionEvent -> {
            System.out.println("Affichage echiquier demand??e");
            System.out.println(partieController.getPartie().getPlateau());
        });
    }


    private void demandeDeplacement(String deplacement){

        //String deplacement = controllerDeplacement.dep.getText();

        try { // Si la saisie du d??placement est correcte.
            String [] cases = partieController.getPartie().getPlateau().stringToDepartDestination(deplacement, partieController.getPartie().getJoueurActif());
            String depart =cases[0];
            String destination = cases[1];

            partieController.getPartie().jouerCoup(depart,destination);
            System.out.println("***\t Coup jou?? ("+partieController.getPartie().getJoueurActif().toString()+") : "+ depart+"\t ->\t"+destination);
            //controllerEchiquier.updateGridPanel(depart,destination,this.partieController.getEchiquier());
            controllerEchiquier.updateDeplacementGridPane(depart,destination,partieController.getEchiquier());
            controllerDeroulement.updateCoupPartie(deplacement);
            partieController.ajouterHistoriqueCoup(deplacement);
            int nbRep = partieController.ajouterPlateauHistorique(); // Retourne le nombre de r??p??tition pour eviter un parcours suppl??mentaire plus tard

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

        } catch (DeplacementInterditException e) { // Sinon on met juste ?? jour l'affichage en bas et on continue d'attendre un coup correct.
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

            while((s=bufferedR.readLine())!=null) { // TODO: Attention, pas tr??s flex comme lecture de fichier ?
                mots=s.split(" ");
                for (String wrd : mots) {
                    if (! (wrd.charAt(1) == '.')){ // Si on est pas dans le cadre d'un num??ro de coup
                        demandeDeplacement(wrd);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            controllerDebug.setStatutDebug(e.getMessage());
            //e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


}
