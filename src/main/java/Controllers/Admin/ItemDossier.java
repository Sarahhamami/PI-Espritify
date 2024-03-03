package Controllers.Admin;

import Controllers.Alert;
import Controllers.ShowImageController;
import entities.TypeStage;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.StringConverter;
import services.DossierStageService;
import services.UserService;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ItemDossier {
    private DossierStageService ds=new DossierStageService();
    @FXML
    private Button convention;

    @FXML
    private Button copie_cin;

    @FXML
    private Button cv;
    private String urlCopie_cin ;
    private String urlCv;
    private String nom_etudiant;

    public String getNom_etudiant() {
        return nom_etudiant;
    }

    public void setNom_etudiant(String nom_etudiant) {
        this.nom_etudiant = nom_etudiant;
    }

    public String getUrlCv() {
        return urlCv;
    }

    public void setUrlCv(String urlCv) {
        this.urlCv = urlCv;
    }

    public String getUrlCopie_cin() {
        return urlCopie_cin;
    }

    public void setUrlCopie_cin(String urlCopie_cin) {
        this.urlCopie_cin = urlCopie_cin;
    }



    public void removeItem(ActionEvent actionEvent) throws IOException {

        boolean deletedSucceffully= ds.deleteDos(id_user,id_offre);
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
            controller.setCustomMsg("Échec de suprression du dossier");
            controller.setCustomTitle("Échec!");
            controller.setImageView("/images/rejete.png");
            controller.setClosePopupStyle("button-failed");
        }
        controller.setPopup(popup);
        popup.show(ownerWindow);
        FXMLLoader loader2 =new FXMLLoader(getClass().getResource("/Admin/DossierStage.fxml"));
        Parent root =loader2.load();
        DossierStage sc=loader2.getController();
        convention.getScene().setRoot(root);
    }
    private int id_offre;

    public void setId_offre(int id_offre) {
        this.id_offre = id_offre;
    }
    private String offre_stage;

    public String getOffre_stage() {
        return offre_stage;
    }

    public void setOffre_stage(String offre_stage) {
        this.offre_stage = offre_stage;
    }
    private String urlConvention;

    public String getUrlConvention() {
        return urlConvention;
    }

    public void setUrlConvention(String urlConvention) {
        this.urlConvention = urlConvention;
    }

    private int id_user;
    public void setId_user(int id) {
        this.id_user=id;
    }
    @FXML
    public void handleClicks(ActionEvent actionEvent) throws IOException {
        Image image=null;
        File file=null;
        if (actionEvent.getSource()==copie_cin){

            System.out.println(urlCopie_cin);
            file = new File(urlCopie_cin);


        }else if (actionEvent.getSource()==cv){
            file = new File(urlCv);

        }else if (actionEvent.getSource()==convention){
            file = new File(urlConvention);

        }
        image = new Image(file.toURI().toString());
        displayImage(image);
    }
    private void displayImage(Image image) {
        // Create an ImageView to display the image
        ImageView imageView = new ImageView(image);

        // Create a new stage to display the image
        Stage imageStage = new Stage();
        imageStage.setTitle("Selected Image");
        imageStage.setScene(new Scene(new Group(imageView), image.getWidth(), image.getHeight()));
        imageStage.show();
    }
    @FXML
    public void modifierDossier(ActionEvent actionEvent) {
       try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/ModifierDossier.fxml"));
            BorderPane popupContent = loader.load();

            Popup popup = new Popup();
            popup.getContent().add(popupContent);
            ModifierDossierController controller = loader.getController();
            UserService userService=new UserService();

            controller.setId_user(id_user);
            controller.setId_offre(id_offre);
            controller.setCv_path(urlCv);
            controller.setNom_etudiant(nom_etudiant);
            controller.setOffre_stage(offre_stage);
            controller.setUrlCopieCin(urlCopie_cin);
            controller.setConvention_path(urlConvention);
            controller.setPopup(popup);

            Stage primaryStage = (Stage) convention.getScene().getWindow();
            popup.show(primaryStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
