package Controllers;

import Controllers.modelControllers.PartieController;
import Controllers.viewControllers.*;
import Exceptions.CustomException;
import Models.Couleur;
import Models.Partie;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

import static Controllers.DeplacementsUtils.stringToDepartDestination;
import static Models.Utils.PartieUtils.jouerCoup;

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

        controllerEchiquier.createPlateuDeJeu();
        initialiserDep();

    }


    public BorderPane getBorderPane() {
        return borderPane;
    }

    public void initialiserDep(){
        System.out.println("TEST SuperController");
        controllerDeplacement.dep.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 6 ? change : null));
        controllerDeplacement.dep.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                System.out.println("ENTRER");
                controllerDeplacement.dep.setText(controllerDeplacement.dep.getText(0, controllerDeplacement.dep.getLength()-1));
                if (controllerDeplacement.dep.getText().length()>=2) {
                    demandeDeplacement();
                    controllerDeplacement.dep.setText("");
                }
            }
        });
    }

    public void demandeDeplacement(){

        try {
            String deplacement = controllerDeplacement.dep.getText();

            String tmp = stringToDepartDestination(Partie.plateau,deplacement, Couleur.BLANC);
            String [] cases = tmp.split(",");
            String depart =cases[0];
            String destination = cases[1];

            System.out.println(depart + " -> " + destination);
            //Verifier que le deplacement est correcte.
            String statutPartie = jouerCoup(Partie.plateau,depart,destination);

            controllerEchiquier.updateGridPanel(depart,destination);
            controllerDeroulement.updateCoupPartie(deplacement);
            partieController.ajouterHistoriqueCoup(deplacement);
            partieController.ajouterPlateauHistorique();
            controllerDebug.setStatutDebug(statutPartie);

        } catch (CustomException e) {
            controllerDebug.setStatutDebug(e.getMessage());
            System.out.println(e+ " : "+controllerDeplacement.dep.getText());
            //e.printStackTrace();
        }


    }



}
