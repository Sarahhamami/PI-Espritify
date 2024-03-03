package Controllers;
import entities.OffreStage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import services.OffreStageService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OffreStageController {
    private final OffreStageService os = new OffreStageService();

    @FXML
    private TextField descriptionTF;

    @FXML
    private TextField titreTF;

    @FXML
    void AjouterOffreStage(ActionEvent event) throws RuntimeException {
        try{

          //  os.add(new OffreStage(titreTF.getText(),descriptionTF.getText()));
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/afficherOffreStage.fxml"));
            try {
                Parent root= loader.load();
                AfficherOffreStageController ac=loader.getController();
                ac.setRTitre(titreTF.getText());
                ac.setRDescription(descriptionTF.getText());

                descriptionTF.getScene().setRoot(root);
                ac.UpdateTable();
                ac.search();


            }catch (IOException e){
                System.out.println(e.getMessage());

            }

        }catch (RuntimeException e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

}
