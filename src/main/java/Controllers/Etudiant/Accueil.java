package Controllers.Etudiant;

import Controllers.Admin.AddDossierController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;


public class Accueil {

    @FXML
    private Button btnMenus;
    @FXML
    private Button btnDossierStage;

    @FXML
    private Button btnEntretien;

    @FXML
    private Button btnOverview;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSignout;

    @FXML
    private Button btnStage;



    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnOverview) {

            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Etudiant/Accueil.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if (actionEvent.getSource() == btnStage) {

            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Etudiant/Stage.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (actionEvent.getSource() == btnEntretien) {

            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Etudiant/Entretien.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void voiceAssistant(ActionEvent actionEvent) {
        System.out.println("voice asistant clicked");

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Etudiant/VoiceAssistantpopup.fxml"));
            AnchorPane popupContent = loader.load();

            Popup popup = new Popup();
            popup.getContent().add(popupContent);
            VoiceAssistantpopup controller = loader.getController();

            controller.setPopup(popup);
            Stage primaryStage = (Stage) btnMenus.getScene().getWindow();
            popup.show(primaryStage);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
