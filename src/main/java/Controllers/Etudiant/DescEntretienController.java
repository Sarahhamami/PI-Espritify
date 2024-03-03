package Controllers.Etudiant;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Popup;

public class DescEntretienController {

    @FXML
    private Label date;

    @FXML
    private Label desc;

    @FXML
    private Label etat;

    @FXML
    private Label lieu;

    @FXML
    private Label type;

    public void setDate(String date) {
        this.date.setText(date);
    }

    public void setDesc(String desc) {
        this.desc.setText(desc);
    }

    public void setEtat(String etat) {
        this.etat.setText(etat);
    }

    public void setLieu(String lieu) {
        this.lieu.setText(lieu);
    }

    public void setType(String type) {
        this.type.setText(type);
    }

    private Popup popup;

    public void setPopup(Popup popup) {
        this.popup = popup;
    }

    public void closePopup(ActionEvent actionEvent) {
        this.popup.hide();
    }
}
