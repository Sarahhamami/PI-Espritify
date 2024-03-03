package Controllers.Admin;

import Controllers.Alert;
import entities.Dossier_stage;
import entities.OffreStage;
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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.StringConverter;
import services.DossierStageService;
import services.OffreStageService;
import services.UserService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.ResourceBundle;

public class AddDossierController implements Initializable {
    @FXML
    private Button ajouterbtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private ChoiceBox<OffreStage> choiceBoxOffre;

    @FXML
    private ChoiceBox<User> choiceBoxUser;

    @FXML
    private Button convention;

    @FXML
    private TextField convention_path;

    @FXML
    private Button copie_cin;

    @FXML
    private TextField copie_cin_path;

    @FXML
    private Button cv;

    @FXML
    private TextField cv_path;
    @FXML
    private Popup popup;
    private String cvPathInProj;
    private String copie_cinPathInProj;
    private String conventionPathInProj;
    private Stage stage;

    private int id_user;
    private int id_offre;
    @FXML
    private Label errorLabel;
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setPopup(Popup popup) {
        this.popup = popup;
    }

    public void closePopup(ActionEvent actionEvent) {popup.hide();
    }
    DossierStageService dossierStageService=new DossierStageService();
    public void AjouterDossier(ActionEvent actionEvent) throws IOException {
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
        User selectedUser = choiceBoxUser.getValue();
        OffreStage selectedOffre = choiceBoxOffre.getValue();
        if (selectedOffre != null) {
            id_offre = selectedOffre.getId();

        }else {
            errorLabel.setText("Veillez selectionnez un offre de stage.");
            return;
        }

        if (selectedUser != null) {
            id_user= selectedUser.getId();

        }else {
            errorLabel.setText("Veillez selectionnez un etudiant.");
            return;
        }





        boolean addedSuccessfully = dossierStageService.add(new Dossier_stage(id_user, cvPathInProj, conventionPathInProj, copie_cinPathInProj,  id_offre));
        Node node = (Node) actionEvent.getSource();
        Window ownerWindow = node.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Alert.fxml"));
        AnchorPane popupContent = loader.load();
        Popup popup = new Popup();
        popup.getContent().add(popupContent);
        Alert controller = loader.getController();
        if (addedSuccessfully) {
            errorLabel.setText("");
            controller.setCustomMsg("Ajout avec succès");
            controller.setCustomTitle("Succès!");
            controller.setImageView("/images/approuve.png");
            controller.setClosePopupStyle("button-success");
        } else {
            controller.setCustomMsg("Échec de l'ajout du stage");
            controller.setCustomTitle("Échec!");
            controller.setImageView("/images/rejete.png");
            controller.setClosePopupStyle("button-failed");
        }
        controller.setPopup(popup);
        popup.show(ownerWindow);


    }

    public void upload(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        File selectedFile = fileChooser.showOpenDialog(stage); // Use the stage passed from the main application
        if (selectedFile != null) {
            // Get the target resource directory path
            String resourcePath = "C:/xampp/htdocs/"; // Modify this according to your project structure
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
                    cv_path.setText( selectedFile.getName());

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

    UserService userService=new UserService();
    OffreStageService offreStageService=new OffreStageService();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<User> userList = userService.readAll();
        List<OffreStage> offreList= offreStageService.readAll();

        ObservableList<User> observableList = FXCollections.observableList(userList);
        ObservableList<OffreStage> observableListOffre = FXCollections.observableList(offreList);
        choiceBoxOffre.setItems(observableListOffre);

        choiceBoxUser.setItems(observableList);
        choiceBoxUser.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User object) {
                return object != null ? object.getNom() : null;
            }

            @Override
            public User fromString(String string) {
                // This method is not used for ChoiceBox
                return null;
            }
        });
        choiceBoxOffre.setConverter(new StringConverter<OffreStage>() {
            @Override
            public String toString(OffreStage object) {
                return object != null ? object.getTitre(): null;
            }

            @Override
            public OffreStage fromString(String string) {
                // This method is not used for ChoiceBox
                return null;
            }
        });
    }
}
