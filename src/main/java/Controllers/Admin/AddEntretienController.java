package Controllers.Admin;

import Controllers.Alert;
import entities.Dossier_stage;
import entities.Entretien;
import entities.OffreStage;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import javafx.stage.Window;
import javafx.util.StringConverter;
import services.EntretienService;
import services.OffreStageService;
import services.UserService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AddEntretienController implements Initializable {
    @FXML
    private Button ajouterbtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private ChoiceBox<OffreStage> choiceBoxOffre;

    @FXML
    private DatePicker date;

    @FXML
    private TextField desc;

    @FXML
    private ChoiceBox<User> choiceBoxUser;

    @FXML
    private TextField type;
    @FXML
    private TextField lieu;
    private int id_user;
    private int id_offre;
    @FXML
    private Label errorLabel;
    private EntretienService entretienService=new EntretienService();
    @FXML
    public void AjouterEntretien(ActionEvent actionEvent) throws IOException {

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

        OffreStage selectedOffre = choiceBoxOffre.getValue();
        if (selectedOffre != null) {
            id_offre = selectedOffre.getId();

            System.out.println("Debug message in AddEntretienController: id_offre:  "+ id_offre);
        }else {
            errorLabel.setText("Veillez selectionnez un offre de stage.");
            return;
        }
        User selectedUser = choiceBoxUser.getValue();
        if (selectedUser != null) {
            id_user= selectedUser.getId();
            System.out.println("Debug message in AddEntretienController: id_user:  "+ id_user);

        }else {
            errorLabel.setText("Veillez selectionnez un etudiant.");
            return;
        }


        System.out.println("Debug message in AddEntretienController: lieu: " + lieu.getText() +" type: " + type.getText()
        + " desc: "+ desc.getText()
        +" date: "+ date.getValue());

        boolean addedSuccessfully = entretienService.add(new Entretien(id_user, id_offre, type.getText(), desc.getText(),  java.sql.Date.valueOf(date.getValue()), lieu.getText() , false));
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
    @FXML
    private Popup popup;

    public void setPopup(Popup popup) {
        this.popup = popup;
    }

    @FXML
    public void closePopup(ActionEvent actionEvent) {
        popup.hide();
    }
    private UserService userService=new UserService();
    private OffreStageService offreStageService=new OffreStageService();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<User> users= userService.readAll();
        List<OffreStage> offreStages= offreStageService.readAll();
        ObservableList<User> observableListUser= FXCollections.observableList(users);
        ObservableList<OffreStage> offreStageObservableList=FXCollections.observableList(offreStages);
        choiceBoxUser.setItems(observableListUser);
        choiceBoxOffre.setItems(offreStageObservableList);
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
                return object != null ? object.getTitre() : null;
            }

            @Override
            public OffreStage fromString(String string) {
                // This method is not used for ChoiceBox
                return null;
            }
        });
    }
}
