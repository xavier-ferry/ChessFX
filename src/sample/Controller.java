package sample;

import Structure.Partie;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Controller {
    @FXML
    private GridPane gridPane ;
    private Partie maPartie;

    @FXML
    private TextArea coupAJouer;

    public void initialize() {
        this.maPartie = new Partie();
        createPlateuDeJeu();

    }
    // MERCI : https://stackoverflow.com/questions/37619867/how-to-display-gridpane-object-grid-lines-permanently-and-without-using-the-set/40408598
    private StackPane createCell(String nomCase) {

        StackPane cell = new StackPane();

        if (maPartie.getPlateau().getPiecebyNomCase(nomCase) != null ) {
            String nomFile = maPartie.getPlateau().getPiecebyNomCase(nomCase).toString();
            Image img = new Image("file:///Users/xavierwork/IdeaProjects/ChessFX/img/" + nomFile + ".png");

            ImageView view = new ImageView(img);
            view.setFitHeight(50);
            view.setPreserveRatio(true);
            cell.getChildren().add(view);
        }
        cell.getStyleClass().add("cell");
        return cell;
    }

    public void createPlateuDeJeu(){
        for (int x = 0 ; x < 8 ; x++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setFillWidth(true);
            cc.setHgrow(Priority.ALWAYS);
            gridPane.getColumnConstraints().add(cc);
        }

        for (int y = 0 ; y < 8 ; y++) {
            RowConstraints rc = new RowConstraints();
            rc.setFillHeight(true);
            rc.setVgrow(Priority.ALWAYS);
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
                gridPane.add(newCell, x, 8-y);
            }
        }

        gridPane.getStyleClass().add("grid");
    }

    public void updateGridPanel(String caseDepart, String caseDestination){
  /*      Node depart = null ;
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == 2 && GridPane.getRowIndex(node) == 2) {
                depart = node;
            }
            if (GridPane.getColumnIndex(node) == 5 && GridPane.getRowIndex(node) == 5) {
                gridPane.getChildren().set(5*8+5,depart);
            }
            System.out.println(node.toString());
        }
        gridPane.getChildren().remove(2,2);*/
        gridPane.getChildren().set(10,createCell("e4"));
            //do something
    }
    public void printHelloWorld(){
        System.out.println(coupAJouer.getText());
        maPartie.getPlateau().deplacerPiece("e2","e4");
        updateGridPanel("e2","e4");
    }

}
