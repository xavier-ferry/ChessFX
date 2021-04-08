package sample;

import Structure.Couleur;
import Structure.Partie;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.ArrayList;

import static Deplacements.PlateauUtils.*;

public class Controller {
    @FXML
    private GridPane gridPane ;
    private Partie maPartie;

    @FXML
    private TextArea dep;
    @FXML
    private TextArea dest;

    @FXML
    private TextArea txtTest;

    @FXML
    private TextArea txtDebug;

    public void initialize() {
        this.maPartie = new Partie();
        createPlateuDeJeu();

    }

    private void updateCell(StackPane cell,String nomCase){

        //System.out.println("Avant - " + nomCase + " - "+ cell.getChildren());
        if (getPiecebyNomCase(nomCase,maPartie.getPlateau().getEchiquier()) != null ) {
            String nomFile = getPiecebyNomCase(nomCase,maPartie.getPlateau().getEchiquier()).toString();
            Image img = new Image("file:///Users/xavierwork/IdeaProjects/ChessFX/img/" + nomFile + ".png");

            ImageView view = new ImageView(img);
            view.setFitHeight(50);
            view.setPreserveRatio(true);
            cell.getChildren().add(view);
          //  System.out.println("Après - " + nomCase + " - "+cell.getChildren());
        } else {
            cell.getChildren().clear();
            //System.out.println("Else - " + nomCase + " - "+cell.getChildren());
        }
        cell.getStyleClass().add("cell");

    }
    // MERCI : https://stackoverflow.com/questions/37619867/how-to-display-gridpane-object-grid-lines-permanently-and-without-using-the-set/40408598
    private StackPane createCell(String nomCase) {

        StackPane cell = new StackPane();
        updateCell(cell,nomCase);

        return cell;
    }

    public void createPlateuDeJeu(){
        for (int x = 0 ; x < 8 ; x++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setFillWidth(true);
            //cc.setHgrow(Priority.ALWAYS);
            gridPane.getColumnConstraints().add(cc);
        }

        for (int y = 0 ; y < 8 ; y++) {
            RowConstraints rc = new RowConstraints();
            rc.setFillHeight(true);
            //rc.setVgrow(Priority.ALWAYS);
            gridPane.getRowConstraints().add(rc);
        }

        for (int x = 1 ; x <= 8 ; x++) {
            for (int y = 0 ; y < 8 ; y++) {
                String s = (char)((int)'a' +x-1) + String.valueOf(y+1);
                StackPane newCell =createCell(s);
                if ((y % 2 == 0 && x % 2 == 1) || (y%2 == 1 && x %2 == 0)){
                    newCell.getStyleClass().add("cell-noir");
                } else {
                    newCell.getStyleClass().add("cell-blanche");
                }
                newCell.getStyleClass().add("cell-"+s);
                gridPane.add(newCell, x, 8-y);
            }
        }

        gridPane.getStyleClass().add("grid");
    }

    private int getIndexByNomCase(String nomCase){
        int a = ((int) nomCase.charAt(0) - (int) 'a')*8;
        int b =  Character.getNumericValue(nomCase.charAt(1))-1;
        return a+b;
    }

    public void updateGridPanel(String caseDepart, String caseDestination){
        int indiceDepart = getIndexByNomCase(caseDepart);
        updateCell((StackPane) gridPane.getChildren().get(indiceDepart),caseDepart);
        int indiceArrivee = getIndexByNomCase(caseDestination);
        updateCell((StackPane) gridPane.getChildren().get(indiceArrivee),caseDestination);
    }
    public void testerCoup(){
        String depart = dep.getText();
        String destination = dest.getText();
        System.out.println(depart + " -> " + destination);
        deplacerPiece(depart,destination,maPartie.getPlateau().getEchiquier());
        updateGridPanel(depart,destination);

        dep.setText("");
        dest.setText("");

    }

    public void debugOnClick() {

        String tmp = stringToDepartDestination(maPartie.getPlateau().getEchiquier(),txtTest.getText(), Couleur.BLANC);
        if (tmp != "ERROR" ){
            String [] cases = tmp.split(",");
            String depart =cases[0];
            String destination = cases[1];

            System.out.println(depart + " -> " + destination);
            deplacerPiece(depart,destination,maPartie.getPlateau().getEchiquier());
            updateGridPanel(depart,destination);

            String txt = String.join(", ",cases);
            txtDebug.setText(txt);
        } else {
            txtDebug.setText("ERROR deplacement");
        }



    }
}
