package Controllers.Etudiant;

import javafx.event.ActionEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Popup;

public class DescOffreController {
    @FXML
    private Button cancelBtn;

    @FXML
    private Label comp;

    @FXML
    private Label descSoc;

    @FXML
    private Label descStage;

    @FXML
    private Label nomSoc;

    @FXML
    private Label titreStage;

    @FXML
    private Label typeStage;

    public void setComp(String comp) {
        this.comp.setText(comp);
    }

    public void setDescSoc(String descSoc) {
        this.descSoc.setText(descSoc);
    }

    public void setDescStage(String descStage) {
        this.descStage.setText(descStage);
    }

    public void setNomSoc(String nomSoc) {
        this.nomSoc.setText(nomSoc);
    }

    public void setTitreStage(String titreStage) {
        this.titreStage.setText(titreStage);
    }

    public void setTypeStage(String typeStage) {
        this.typeStage.setText(typeStage);
    }
    private Popup popup;

    public void setPopup(Popup popup) {
        this.popup = popup;
    }

    public void closePopup(ActionEvent actionEvent) {
        this.popup.hide();
    }
}
