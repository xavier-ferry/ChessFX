package Controllers.viewControllers;

import Models.Piece;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

import java.util.Map;


public class ControllerEchiquier {
    @FXML
    public GridPane gridPane;

    public void initialize(){
        System.out.println("ControllerEchiquier - initialize");
    }


    private int getIndexByNomCase(String nomCase){
        int a = ((int) nomCase.charAt(0) - (int) 'a')*8;
        int b =  Character.getNumericValue(nomCase.charAt(1))-1;
        return a+b;
    }



    // VERSION 2.0

    public void updateCaseGridPane(String nomCase,Map<String,Piece> board) {
        StackPane cell = (StackPane) gridPane.getChildren().get(getIndexByNomCase(nomCase));
        cell.getChildren().clear();

        if (board.get(nomCase) != null){
            String nomFile = board.get(nomCase).toString();
            Image img = new Image("file:///Users/xavierwork/IdeaProjects/ChessFX/img/" + nomFile + ".png");
            //TODO : Problème ici ->
            ImageView view = new ImageView(img);
            view.setFitHeight(50);
            view.setPreserveRatio(true);
            cell.getChildren().add(view);

        }
        cell.getStyleClass().add("cell");

    }

    public void updateDeplacementGridPane(String caseDepart, String caseDestination,Map<String,Piece> board){
        updateCaseGridPane(caseDepart,board);
        updateCaseGridPane(caseDestination,board);
    }
    public void updateTotalGridPane(Map<String,Piece> board){
        for (int x = 1 ; x <= 8 ; x++) {
            for (int y = 0; y < 8; y++) {
                String laCase = (char) ((int) 'a' + x - 1) + String.valueOf(y + 1);
                updateCaseGridPane(laCase, board);
            }
        }
    }

    public void creerPlateauDeJeu(Map<String,Piece> board){
        for (int x = 0 ; x < 8 ; x++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setFillWidth(true);

            gridPane.getColumnConstraints().add(cc);
        }

        for (int y = 0 ; y < 8 ; y++) {
            RowConstraints rc = new RowConstraints();
            rc.setFillHeight(true);

            gridPane.getRowConstraints().add(rc);
        }

        for (int x = 1 ; x <= 8 ; x++) {
            for (int y = 0 ; y < 8 ; y++) {
                String s = (char)((int)'a' +x-1) + String.valueOf(y+1);
                StackPane newCell = new StackPane();
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

        updateTotalGridPane(board);
    }

}

/*  --- VERSION 1.0
private void updateCell(StackPane cell, String nomCase,Map<String,Piece> board){
        //System.out.println("Avant - " + nomCase + " - "+ cell.getChildren());
        if (board.get(nomCase) != null){
            System.out.println(cell.getChildren());
        //if (getPiecebyNomCase(nomCase, Partie.plateau) != null ) {
          //  String nomFile = getPiecebyNomCase(nomCase,Partie.plateau).toString();
            String nomFile = board.get(nomCase).toString();
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
    private StackPane createCell(String nomCase,Map<String,Piece> board) {

        StackPane cell = new StackPane();
        updateCell(cell,nomCase,board);

        return cell;
    }

    public void createPlateuDeJeu(Map<String,Piece> board){
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
                StackPane newCell =createCell(s,board);
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
        System.out.println(gridPane.hashCode());
    }


    public void totalUpdateGridPanel(Map<String,Piece> board){
        for (int x = 1 ; x <= 8 ; x++) {
            for (int y = 0; y < 8; y++) {
                String s = (char) ((int) 'a' + x - 1) + String.valueOf(y + 1);
                System.out.println(s);
                updateCell((StackPane) gridPane.getChildren().get(getIndexByNomCase(s)), s, board);
            }
        }
    }

    public void updateGridPanel(String caseDepart, String caseDestination,Map<String,Piece> board){
        System.out.println("MAJ du gridPane "+ gridPane.hashCode()+" : "+caseDepart+" -> "+caseDestination);
        int indiceDepart = getIndexByNomCase(caseDepart);
        updateCell((StackPane) gridPane.getChildren().get(indiceDepart),caseDepart,board);
        int indiceArrivee = getIndexByNomCase(caseDestination);
        updateCell((StackPane) gridPane.getChildren().get(indiceArrivee),caseDestination,board);
    }

 */
