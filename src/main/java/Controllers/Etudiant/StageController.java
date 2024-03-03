package Controllers.Etudiant;

import Controllers.Admin.ItemController;
import entities.OffreStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.OffreStageService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StageController implements Initializable {
    @FXML
    private Button btnDossierStage;
    @FXML
    private Button btnStage;

    @FXML
    private Button btnEntretien;

    @FXML
    private Button btnMenus;

    @FXML
    private Button btnOverview;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSignout;
    @FXML
    private VBox pnItems = null;
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
    private OffreStageService offreStageService=new OffreStageService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<OffreStage> lst= offreStageService.readAll();

        for (OffreStage offreStage : lst) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Etudiant/ItemStage.fxml"));
                Node node = loader.load();

                ItemStage itemController = loader.getController();

                itemController.setId_offre(offreStage.getId());
                // Set data for the UI components
                ((Label) node.lookup("#titre")).setText(offreStage.getTitre());
                ((Label) node.lookup("#nom_soc")).setText(offreStage.getNomSociete());
                ((Label) node.lookup("#type")).setText(offreStage.getTypeStage().name());
                itemController.setComp(offreStage.getCompetance());
                itemController.setDescSoc(offreStage.getDescription_societe());
                itemController.setDescStage(offreStage.getDescription());
                itemController.setTitreStage(offreStage.getTitre());
                itemController.setNomSoc(offreStage.getNomSociete());
                itemController.setTypeStage(offreStage.getTypeStage().name());

                // Add node to the UI
                pnItems.getChildren().add(node);

                // Add mouse event handlers
                node.setOnMouseEntered(event -> {
                    node.setStyle("-fx-background-color : #ffffff");
                });
                node.setOnMouseExited(event -> {
                    node.setStyle("-fx-background-color : #ffffff");
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }
}
