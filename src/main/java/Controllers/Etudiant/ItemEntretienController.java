package Controllers.Etudiant;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;
import javafx.stage.Window;

import java.io.IOException;

public class ItemEntretienController {

    private String date;


    private String desc;


    private String etat;


    private String lieu;


    private String type;

    public void setDate(String date) {
        this.date = date;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void VoirDesc(ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Window ownerWindow = node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Etudiant/descEntretien.fxml"));
        BorderPane popupContent = loader.load();
        Popup popup = new Popup();
        popup.getContent().add(popupContent);
        DescEntretienController controller = loader.getController();
        controller.setDesc(desc);
        controller.setDate(date);
        controller.setEtat(etat);
        controller.setLieu(lieu);
        controller.setType(type);
        controller.setPopup(popup);
        popup.show(ownerWindow);
    }

    public void participer(ActionEvent actionEvent) {
    }
}
