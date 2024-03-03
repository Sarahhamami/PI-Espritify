package Controllers.Admin;

import Controllers.Alert;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import services.EntretienService;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import services.UserService;

public class ItemEntretienController {
    @FXML
    private Label date;

    @FXML
    private Label desc;

    @FXML
    private Label etat;

    @FXML
    private HBox itemC;

    @FXML
    private Label lieu;

    @FXML
    private Label nom_etudiant;

    @FXML
    private Label titre_stage;

    @FXML
    private Label type;

    public void handleClicks(ActionEvent actionEvent) {
    }

    public void modifierEntretien(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/ModifierEntretien.fxml"));
            BorderPane popupContent = loader.load();

            Popup popup = new Popup();
            popup.getContent().add(popupContent);
            ModifierEntretienController controller = loader.getController();


            controller.setId_user(id_user);
            controller.setId_offre(id_offre);
            controller.setDesc(desc.getText());
            controller.setLieu(lieu.getText());
            controller.setType(type.getText());
            controller.setDate(date.getText());
            controller.setPopup(popup);

            Stage primaryStage = (Stage) desc.getScene().getWindow();
            popup.show(primaryStage);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private EntretienService entretienService=new EntretienService();
    public void removeItem(ActionEvent actionEvent) throws IOException {
        System.out.println("DEBUG MESSAGE IN ITEMENTRETIENCONTROLLER: id_user "+ id_user);
        System.out.println("Debug message in ItemEntretienController: id_offre "+id_offre);
        boolean deletedSucceffully= entretienService.deleteEntre(id_user,id_offre);
        Node node = (Node) actionEvent.getSource();
        Window ownerWindow = node.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Alert.fxml"));
        AnchorPane popupContent = loader.load();
        Popup popup = new Popup();
        popup.getContent().add(popupContent);
        Alert controller = loader.getController();
        if (deletedSucceffully) {
            controller.setCustomMsg("supprimé avec succès");
            controller.setCustomTitle("Succès!");
            controller.setImageView("/images/approuve.png");
            controller.setClosePopupStyle("button-success");
            FXMLLoader loader2 =new FXMLLoader(getClass().getResource("/Admin/Entretien.fxml"));
            Parent root =loader2.load();
            EntretienController sc=loader2.getController();
            titre_stage.getScene().setRoot(root);
        } else {
            controller.setCustomMsg("Échec de suprression de l'entretien");
            controller.setCustomTitle("Échec!");
            controller.setImageView("/images/rejete.png");
            controller.setClosePopupStyle("button-failed");
        }
        controller.setPopup(popup);
        popup.show(ownerWindow);

    }
    private int id_user;

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
    private int id_offre;

    public int getId_offre() {
        return id_offre;
    }

    public void setId_offre(int id_offre) {
        this.id_offre = id_offre;
    }
}
