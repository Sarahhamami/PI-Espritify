package Controllers.Admin;

import Controllers.Alert;
import entities.TypeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import services.OffreStageService;

import java.io.IOException;

public class ItemController {


    private OffreStageService os =new OffreStageService();
    @FXML
    private Label comp;

    @FXML
    private Label desc_soc;

    @FXML
    private Label desc_stage;
    @FXML
    private Label type;
    @FXML
    private Label nom_soc;

    @FXML
    private HBox itemC;

    @FXML
    private Label titre;

   private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre.getText();
    }

    @FXML
    public void modifierStage(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/ModifierStage.fxml"));
            BorderPane popupContent = loader.load();

            Popup popup = new Popup();
            popup.getContent().add(popupContent);
            ModifierStage controller = loader.getController();

            controller.setCompetance(comp.getText());
            controller.setDesc_stage(desc_stage.getText());
            controller.setDesc_soc(desc_soc.getText());
            controller.setNom_soc(nom_soc.getText());
            controller.setTitre(titre.getText());
            System.out.println(id);
            controller.setId(id);
            if (type.getText().equals(TypeStage.presentiel.name()))
                controller.setPresentiel();

            else if (type.getText().equals(TypeStage.remote.name()))
                controller.setRemote();

            controller.setPopup(popup);
            Stage primaryStage = (Stage) comp.getScene().getWindow();
            popup.show(primaryStage);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    public void removeItem(ActionEvent actionEvent) throws IOException {
        boolean deletedSucceffully= os.delete(id);
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
        } else {
            controller.setCustomMsg("Échec de suprression du stage");
            controller.setCustomTitle("Échec!");
            controller.setImageView("/images/rejete.png");
            controller.setClosePopupStyle("button-failed");
        }
        controller.setPopup(popup);
        popup.show(ownerWindow);
        FXMLLoader loader2 =new FXMLLoader(getClass().getResource("/Admin/Stage.fxml"));
        Parent root =loader2.load();
        StageController sc=loader2.getController();
        nom_soc.getScene().setRoot(root);

    }
}
