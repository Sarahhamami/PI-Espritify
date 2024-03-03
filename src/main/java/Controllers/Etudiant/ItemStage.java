package Controllers.Etudiant;

import Controllers.Admin.AddDossierController;
import Controllers.Alert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;
import javafx.stage.Window;
import services.DossierStageService;
import services.OffreStageService;

import java.io.IOException;

public class ItemStage {
    private int id_offre;

    private String comp;


    private String descSoc;


    private String descStage;

    private String nomSoc;


    private String titreStage;

    private String typeStage;

    public void setComp(String comp) {
        this.comp = comp;
    }

    public void setDescSoc(String descSoc) {
        this.descSoc = descSoc;
    }

    public void setDescStage(String descStage) {
        this.descStage = descStage;
    }

    public void setNomSoc(String nomSoc) {
        this.nomSoc = nomSoc;
    }

    public void setTitreStage(String titreStage) {
        this.titreStage = titreStage;
    }

    public void setTypeStage(String typeStage) {
        this.typeStage = typeStage;
    }

    public void setId_offre(int id_offre) {
        this.id_offre = id_offre;
    }

    public void VoirDesc(ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Window ownerWindow = node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Etudiant/descOffre.fxml"));
        BorderPane popupContent = loader.load();
        Popup popup = new Popup();
        popup.getContent().add(popupContent);
        DescOffreController controller = loader.getController();
        controller.setDescStage(descStage);
        controller.setComp(comp);
        controller.setDescSoc(descSoc);
        controller.setTitreStage(titreStage);
        controller.setNomSoc(nomSoc);
        controller.setTypeStage(typeStage);
        controller.setPopup(popup);
        popup.show(ownerWindow);
    }
    private DossierStageService dossierStageService=new DossierStageService();
    public void participer(ActionEvent actionEvent) throws IOException {
        boolean participate= dossierStageService.didParticipate(76, id_offre);
        Node node = (Node) actionEvent.getSource();
        Window ownerWindow = node.getScene().getWindow();

        if (participate) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Alert.fxml"));
            AnchorPane popupContent = loader.load();
            Popup popup = new Popup();
            popup.getContent().add(popupContent);
            Alert controller = loader.getController();
            controller.setCustomMsg("Vous avez déjà participer à cet offre");
            controller.setCustomTitle("Échec!");
            controller.setImageView("/images/rejete.png");
            controller.setClosePopupStyle("button-failed");
            controller.setPopup(popup);
            popup.show(ownerWindow);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Etudiant/AddDossier.fxml"));
            BorderPane popupContent = loader.load();
            Popup popup = new Popup();
            popup.getContent().add(popupContent);
            AddDossierStageController controller= loader.getController();


            // this will be modified when the session is ready
            controller.setId_user(76);
            controller.setId_offre(id_offre);
            controller.setPopup(popup);
            popup.show(ownerWindow);
        }

    }
}
