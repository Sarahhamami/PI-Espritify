package Controllers.Admin;

import entities.Entretien;
import entities.OffreStage;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import services.DossierStageService;
import services.EntretienService;
import services.OffreStageService;
import services.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EntretienController implements Initializable {

    @FXML
    private Label TotalStage;

    @FXML
    private Label totalEtudiant;

    @FXML
    private Label totalPostule;

    @FXML
    private Button btnDossierStage;

    @FXML
    private Button btnEntretien;

    @FXML
    private Button btnMenus;

    @FXML
    private Button btnOverview;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSignout;

    @FXML
    private Button btnStage;

    @FXML
    private TextField filterField;

    @FXML
    private VBox pnItems;

    @FXML
    private Pane pnlCustomer;

    @FXML
    private Pane pnlMenus;

    @FXML
    private Pane pnlOrders;

    @FXML
    private Pane pnlOverview;
    @FXML
    public void handleClicks(ActionEvent actionEvent) {
        // this will be transformed based on the work of each button
        if (actionEvent.getSource() == btnDossierStage) {

            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/DossierStage.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (actionEvent.getSource() == btnEntretien) {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/Entretien.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (actionEvent.getSource() == btnOverview) {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/OverView.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(actionEvent.getSource()==btnStage)
        {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/Stage.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);

                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception appropriately
            }
        }
    }

    public void ajouterEntretien(ActionEvent actionEvent) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/AddEntretien.fxml"));
            BorderPane popupContent = loader.load();

            Popup popup = new Popup();
            popup.getContent().add(popupContent);
            AddEntretienController controller = loader.getController();

            controller.setPopup(popup);
            Stage primaryStage = (Stage) btnStage.getScene().getWindow();
            popup.show(primaryStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private EntretienService entretienService=new EntretienService();
    private DossierStageService dossierStageService=new DossierStageService();
    private OffreStageService offreStageService=new OffreStageService();
    private UserService userService=new UserService();
    public void refreshPage() {
        totalEtudiant.setText(String.valueOf(userService.getCountEtudiant()));
        totalPostule.setText(String.valueOf(dossierStageService.getCountDossier()));
        TotalStage.setText(String.valueOf(offreStageService.getCountOffre()));
        List<Entretien> entretiens = entretienService.readAll();

        pnItems.getChildren().clear(); // Clear existing items
        OffreStageService os= new OffreStageService();
        UserService us= new UserService();
        for (Entretien entretien : entretiens) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/ItemEntretien.fxml"));
                Node node = loader.load();

                ItemEntretienController itemController = loader.getController();
                itemController.setId_user(entretien.getId_user());
                itemController.setId_offre(entretien.getId_stage());

                // Set data for the UI components
                OffreStage offreStage= null;
                offreStage= os.readById(entretien.getId_stage());
                User user= null;
                user= us.readById(entretien.getId_user());

                ((Label) node.lookup("#desc")).setText(entretien.getDescription());
                ((Label) node.lookup("#type")).setText(entretien.getType());
                ((Label) node.lookup("#date")).setText(entretien.getDateEntre().toString());
                ((Label) node.lookup("#titre_stage")).setText(offreStage.getTitre());
                ((Label) node.lookup("#lieu")).setText(entretien.getLieu());
                ((Label) node.lookup("#nom_etudiant")).setText(user.getNom());
                if (entretien.isEtat())
                ((Label) node.lookup("#etat")).setText("yes");
                else ((Label) node.lookup("#etat")).setText("yes");
                // Add node to the UI
                pnItems.getChildren().add(node);

                // Add mouse event handlers
                node.setOnMouseEntered(event -> {
                    node.setStyle("-fx-background-color : #ffffff");
                });
                node.setOnMouseExited(event -> {
                    node.setStyle("-fx-background-color : #ffffff");
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            search(); // Call search method whenever text changes in the filterField
        });


    }


    @FXML
    void search() {
        List<Entretien> entretiens = entretienService.readAll();
        OffreStageService offreStageService = new OffreStageService();
        UserService userService = new UserService();
        ObservableList<Entretien> observableList = FXCollections.observableList(entretiens);
        FilteredList<Entretien> filteredData = new FilteredList<>(observableList, p -> true);

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entretien -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                User user = userService.readById(entretien.getId_user());
                OffreStage offreStage = offreStageService.readById(entretien.getId_stage());

                return user.getNom().toLowerCase().contains(lowerCaseFilter)
                        || offreStage.getTitre().toLowerCase().contains(lowerCaseFilter);
            });
        });

        // Clear previous items in the UI
        pnItems.getChildren().clear();

        // Update UI with the filtered data
        for (Entretien entretien : filteredData) {
            // Load FXML file for each item
            Node node = null;
            try {
                node = FXMLLoader.load(getClass().getResource("/Admin/ItemDossier.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (node != null) {
                ((Label) node.lookup("#offre")).setText(offreStageService.readById(entretien.getId_stage()).getTitre());
                ((Label) node.lookup("#user")).setText(userService.readById(entretien.getId_user()).getNom());

                // Add the node to the UI
                pnItems.getChildren().add(node);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshPage();
    }
}
