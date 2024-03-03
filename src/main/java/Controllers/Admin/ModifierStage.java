package Controllers.Admin;

import Controllers.Alert;
import entities.OffreStage;
import entities.TypeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Window;
import services.OffreStageService;

public class ModifierStage implements  Initializable{
    private OffreStageService os= new OffreStageService();

    @FXML
    private RadioButton Presentiel;

    @FXML
    private RadioButton Remote;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField competance;

    @FXML
    private TextField desc_soc;

    @FXML
    private TextField desc_stage;

    @FXML
    private Button modifierBtn;

    @FXML
    private TextField nom_soc;

    @FXML
    private TextField titre;
    @FXML
    private Popup popup;
    private int id;
    @FXML
    private Label errorLabel;

    public void setId(int id) {
        this.id = id;
    }

    public void setPresentiel() {
        Presentiel.setSelected(true);
        Remote.setSelected(false);
    }

    public void setRemote() {
        Presentiel.setSelected(false);
        Remote.setSelected(true);
    }

    public void setTitre(String titre) {
        this.titre.setText(titre);
    }

    public void setCompetance(String competance) {
        this.competance.setText(competance);
    }

    public void setDesc_soc(String desc_soc) {
        this.desc_soc.setText(desc_soc);
    }

    public void setDesc_stage(String desc_stage) {
        this.desc_stage.setText(desc_stage);
    }

    public void setNom_soc(String nom_soc) {
        this.nom_soc.setText(nom_soc);
    }

    public void setPopup(Popup popup) {
        this.popup = popup;
    }
    @FXML
    public void modifierStage(ActionEvent actionEvent) throws IOException {
        if (titre.getText().isEmpty()) {
            errorLabel.setText("Le titre ne peut pas être vide.");
            return; // Exit the method if the titre field is empty
        }
        if (desc_stage.getText().isEmpty()) {
            errorLabel.setText("La description du stage ne peut pas être vide.");
            return; // Exit the method if the titre field is empty
        }
        if (competance.getText().isEmpty()) {
            errorLabel.setText("La compétance demandé ne peut pas être vide.");
            return; // Exit the method if the titre field is empty
        }
        if (desc_soc.getText().isEmpty()) {
            errorLabel.setText("La description du société ne peut pas être vide.");
            return; // Exit the method if the titre field is empty
        }
        if (nom_soc.getText().isEmpty()) {
            errorLabel.setText("Le nom du société ne peut pas être vide.");
            return; // Exit the method if the titre field is empty
        }
        if (selectedRadioButton == null) {
            errorLabel.setText("Veuillez sélectionner le type de stage.");
            return; // Exit the method if no toggle button is selected
        }
        TypeStage typeStage = null;

        TypeStage type=null;
            if (Presentiel.isSelected()){
                type=TypeStage.presentiel;
            }else if(Remote.isSelected()) {
                type=TypeStage.remote;
            }

        boolean modifiedSucceffully= os.update(new OffreStage(id, titre.getText(), desc_stage.getText(), competance.getText(), desc_soc.getText(), type , nom_soc.getText()));
        Node node = (Node) actionEvent.getSource();
        Window ownerWindow = node.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Alert.fxml"));
        AnchorPane popupContent = loader.load();
        Popup popup = new Popup();
        popup.getContent().add(popupContent);
        Alert controller = loader.getController();
        if (modifiedSucceffully) {
            errorLabel.setText("");
            controller.setCustomMsg("Modifier avec succès");
            controller.setCustomTitle("Succès!");
            controller.setImageView("/images/approuve.png");
            controller.setClosePopupStyle("button-success");
        } else {
            controller.setCustomMsg("Échec de modification du stage");
            controller.setCustomTitle("Échec!");
            controller.setImageView("/images/rejete.png");
            controller.setClosePopupStyle("button-failed");
        }
        controller.setPopup(popup);
        popup.show(ownerWindow);




    }
    @FXML
    public void closePopup(ActionEvent actionEvent) {
        popup.hide();
    }



    @FXML
    private ToggleGroup toggleGroup;
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
