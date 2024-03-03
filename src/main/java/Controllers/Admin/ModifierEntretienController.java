package Controllers.Admin;

import Controllers.Alert;
import entities.Entretien;
import entities.OffreStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import services.EntretienService;

import java.io.IOException;
import java.time.LocalDate;

public class ModifierEntretienController {
    @FXML
    private Button ajouterbtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private DatePicker date;

    @FXML
    private TextField desc;

    @FXML
    private TextField lieu;

    @FXML
    private TextField type;

    private int id_user;
    private int id_offre;
    @FXML
    private Label errorLabel;

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setId_offre(int id_offre) {
        this.id_offre = id_offre;
    }

    public void setDate(String date) {
        this.date.setValue(LocalDate.parse(date));
    }

    public void setDesc(String desc) {
        this.desc.setText(desc);
    }

    public void setLieu(String lieu) {
        this.lieu.setText(lieu);
    }

    public void setType(String type) {
        this.type.setText(type);
    }

    public void setPopup(Popup popup) {
        this.popup = popup;
    }
    EntretienService entretienService=new EntretienService();
    public void ModifierEntretien(ActionEvent actionEvent) throws IOException {
        if (type.getText().isEmpty()) {
            errorLabel.setText("Le type de l'entretien ne peut pas être vide.");
            return; // Exit the method if the titre field is empty
        }
        if (desc.getText().isEmpty()) {
            errorLabel.setText("La description de l'entretien ne peut pas être vide.");
            return; // Exit the method if the titre field is empty
        }
        if (lieu.getText().isEmpty()) {
            errorLabel.setText("La lieu ne peut pas être vide.");
            return; // Exit the method if the titre field is empty
        }
        if (date.getValue()==null) {
            errorLabel.setText("La date ne peut pas être vide.");
            return; // Exit the method if the titre field is empty
        }

        LocalDate currentDate = LocalDate.now();
        if (date.getValue().isBefore(currentDate)){
            errorLabel.setText("La date doit être suppérieur à la date d'aujourd'hui");
            return;
        }

        boolean modifiedSucceffully= entretienService.update(new Entretien(id_user, id_offre, type.getText(), desc.getText(),  java.sql.Date.valueOf(date.getValue()), lieu.getText() , false));
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

    private Popup popup;

    public void closePopup(ActionEvent actionEvent) {
        popup.hide();
    }
}
