package Controllers.Admin;

import Controllers.Alert;
import entities.Dossier_stage;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.StringConverter;
import services.DossierStageService;
import services.UserService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

public class ModifierDossierController implements Initializable {


    @FXML
    private TextField cv_path;
    @FXML
    private TextField copie_cin_path;

    @FXML
    private Button modifierBtn;

    @FXML
    private TextField nom_etudiant;

    @FXML
    private TextField offre_stage;
    @FXML
    private Button cv;
    @FXML
    private Button convention;

    @FXML
    private Button copie_cin;
    private int id_user;
    @FXML
    private TextField convention_path;
    @FXML
    private Label errorLabel;


    public String getConvention_path() {
        return convention_path.getText();
    }

    public void setConvention_path(String convention_path) {
        this.convention_path.setText(convention_path);
    }

    public void setId_offre(int id){
        this.id_offre=id;
    }
    private int id_offre;

    public void setId_user(int id){
        this.id_user=id;
    }

    public String getCv_path() {
        return cv_path.getText();
    }

    public void setCv_path(String cv_path) {
        this.cv_path.setText(cv_path);
    }

    public TextField getNom_etudiant() {
        return nom_etudiant;
    }

    public void setNom_etudiant(String nom_etudiant) {
        this.nom_etudiant.setText(nom_etudiant);
    }

    public TextField getOffre_stage() {
        return offre_stage;
    }

    public void setOffre_stage(String offre_stage) {
        this.offre_stage.setText(offre_stage);
    }


    public String getUrlCopieCin() {
        return copie_cin_path.getText();
    }

    public void setUrlCopieCin(String urlCopieCin) {
        this.copie_cin_path.setText(urlCopieCin);
    }

    @FXML
    private Popup popup;
    public void setPopup(Popup popup) {
        this.popup = popup;
    }

    public void closePopup(ActionEvent actionEvent) {
        popup.hide();
    }
    private DossierStageService dossierStageService= new DossierStageService();
    public void modifierDossier(ActionEvent actionEvent) throws IOException {
        if (convention_path.getText().isEmpty()) {
            errorLabel.setText("La convention ne peut pas être vide.");
            return; // Exit the method if the titre field is empty
        }
        if (cv_path.getText().isEmpty()) {
            errorLabel.setText("Le CV ne peut pas être vide.");
            return; // Exit the method if the titre field is empty
        }
        if (copie_cin_path.getText().isEmpty()) {
            errorLabel.setText("La copie du CIN ne pas être vide.");
            return; // Exit the method if the titre field is empty
        }

        Dossier_stage dossier= new Dossier_stage(id_user, cv_path.getText(), convention_path.getText(),
                copie_cin_path.getText(), id_offre);
        boolean modifiedSucceffully=dossierStageService.update(dossier);
        Node node = (Node) actionEvent.getSource();
        Window ownerWindow = node.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Alert.fxml"));
        AnchorPane popupContent = loader.load();
        Popup popup = new Popup();
        popup.getContent().add(popupContent);
        Alert controller = loader.getController();
        if (modifiedSucceffully) {
            controller.setCustomMsg("Modifier avec succès");
            controller.setCustomTitle("Succès!");
            controller.setImageView("/images/approuve.png");
            controller.setClosePopupStyle("button-success");
        } else {
            controller.setCustomMsg("Échec de modification du dossier stage");
            controller.setCustomTitle("Échec!");
            controller.setImageView("/images/rejete.png");
            controller.setClosePopupStyle("button-failed");
        }
        controller.setPopup(popup);
        popup.show(ownerWindow);


    }

    private Stage stage;


    public void setStage(Stage stage) {
        this.stage = stage;
    }
    private String cvPathInProj;
    private String copie_cinPathInProj;
    private String conventionPathInProj;




    public void upload(ActionEvent actionEvent) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        File selectedFile = fileChooser.showOpenDialog(stage); // Use the stage passed from the main application
        if (selectedFile != null) {
            // Get the target resource directory path
            String resourcePath = "src/main/resources/images/"; // Modify this according to your project structure
            File resourceDirectory = new File(resourcePath);

            // Ensure the resource directory exists, create if necessary
            if (!resourceDirectory.exists()) {
                resourceDirectory.mkdirs();
            }
            if (actionEvent.getSource()==cv){
                cvPathInProj = resourcePath + selectedFile.getName();
                File destinationFile = new File(cvPathInProj);

                try {
                    // Copy the selected file to the resource directory
                    Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Image uploaded to: " + cvPathInProj);
                    cv_path.setText(selectedFile.getName());

                    // Optionally, load and display the image
                    Image image = new Image(destinationFile.toURI().toString());
                    ImageView imageView = new ImageView(image);
                    // You can add this imageView to your layout for display
                    displayImage(image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if (actionEvent.getSource()==convention){
                conventionPathInProj = resourcePath + selectedFile.getName();
                File destinationFile = new File(conventionPathInProj);

                try {
                    // Copy the selected file to the resource directory
                    Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Image uploaded to: " + conventionPathInProj);
                    convention_path.setText(selectedFile.getName());

                    // Optionally, load and display the image
                    Image image = new Image(destinationFile.toURI().toString());
                    ImageView imageView = new ImageView(image);
                    // You can add this imageView to your layout for display
                    displayImage(image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if (actionEvent.getSource()==copie_cin){
                copie_cinPathInProj = resourcePath + selectedFile.getName();
                File destinationFile = new File(copie_cinPathInProj);

                try {
                    // Copy the selected file to the resource directory
                    Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Image uploaded to: " + copie_cinPathInProj);
                    copie_cin_path.setText(selectedFile.getName());

                    // Optionally, load and display the image
                    Image image = new Image(destinationFile.toURI().toString());
                    ImageView imageView = new ImageView(image);
                    // You can add this imageView to your layout for display
                    displayImage(image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
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






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
