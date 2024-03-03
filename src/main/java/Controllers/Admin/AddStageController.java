package Controllers.Admin;
import Controllers.AfficherOffreStageController;
import Controllers.Alert;
import entities.OffreStage;
import entities.TypeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import services.OffreStageService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddStageController implements Initializable {
    OffreStageService os= new OffreStageService();
    @FXML
    private RadioButton Presentiel;

    @FXML
    private RadioButton Remote;

    private ToggleGroup toggleGroup;

    @FXML
    private Button addBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField competance;

    @FXML
    private TextField desc_soc;

    @FXML
    private TextField desc_stage;

    @FXML
    private TextField nom_soc;

    @FXML
    private TextField titre;
    @FXML
    private Label errorLabel;

@FXML
    private Popup popup;

    public void setPopup(Popup popup) {
        this.popup = popup;
    }
@FXML
    private void closePopup() {
        popup.hide();
    }

    @FXML
    public void AjouterStage(ActionEvent actionEvent) throws IOException {
        //controle de saisies
        if (titre.getText().isEmpty()) {
            errorLabel.setText("Le titre ne peut pas être vide.");
            return;
        }
        if (desc_stage.getText().isEmpty()) {
            errorLabel.setText("La description du stage ne peut pas être vide.");
            return;
        }
        if (competance.getText().isEmpty()) {
            errorLabel.setText("La compétance demandé ne peut pas être vide.");
            return;
        }
        if (desc_soc.getText().isEmpty()) {
            errorLabel.setText("La description du société ne peut pas être vide.");
            return;
        }
        if (nom_soc.getText().isEmpty()) {
            errorLabel.setText("Le nom du société ne peut pas être vide.");
            return;
        }
        if (selectedRadioButton == null) {
            errorLabel.setText("Veuillez sélectionner le type de stage.");
            return;
        }
        TypeStage typeStage = null;

        if (selectedRadioButton != null) {
            if (selectedRadioButton == Remote) {
                typeStage = TypeStage.remote;
            } else if (selectedRadioButton == Presentiel) {
                typeStage = TypeStage.presentiel;
            }
        } else {

            return;
        }

        boolean addedSuccessfully = os.add(new OffreStage(titre.getText(), desc_stage.getText(),
                competance.getText(), desc_soc.getText(), typeStage, nom_soc.getText()));
        Node node = (Node) actionEvent.getSource();
        Window ownerWindow = node.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Alert.fxml"));
        AnchorPane popupContent = loader.load();
        Popup popup = new Popup();
        popup.getContent().add(popupContent);
        Alert controller = loader.getController();
        if (addedSuccessfully) {
            errorLabel.setText("");
            controller.setCustomMsg("Ajout avec succès");
            controller.setCustomTitle("Succès!");
            controller.setImageView("/images/approuve.png");
            controller.setClosePopupStyle("button-success");
        } else {
            controller.setCustomMsg("Échec de l'ajout du stage");
            controller.setCustomTitle("Échec!");
            controller.setImageView("/images/rejete.png");
            controller.setClosePopupStyle("button-failed");
        }
        controller.setPopup(popup);
        popup.show(ownerWindow);
        StageController.getInstance().refreshPage();

    }


    private RadioButton selectedRadioButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toggleGroup = new ToggleGroup();
        Remote.setToggleGroup(toggleGroup);
        Presentiel.setToggleGroup(toggleGroup);

        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            selectedRadioButton = (RadioButton) newValue;
            if (selectedRadioButton != null) {
                if (selectedRadioButton == Remote) {
                    System.out.println("Remote selected");
                } else if (selectedRadioButton == Presentiel) {
                    System.out.println("Presentiel selected");
                }
            }
        });

    }




}
