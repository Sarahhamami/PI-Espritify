package Controllers.Admin;

import Controllers.AfficherOffreStageController;
import entities.OffreStage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import services.DossierStageService;
import services.OffreStageService;
import services.UserService;

public class StageController implements Initializable{
    @FXML
    private Label TotalStage;
    @FXML
    private Label totalEtudiant;

    @FXML
    private Label totalPostule;
    private static StageController instance;
    @FXML
    private TextField filterField;
        @FXML
        private VBox pnItems = null;
        @FXML
        private Button btnOverview;

        @FXML
        private Button btnStage;

        @FXML
        private Button btnDossierStage;

        @FXML
        private Button btnMenus;

        @FXML
        private Button btnEntretien;

        @FXML
        private Button btnSettings;

        @FXML
        private Button btnSignout;

        @FXML
        private Pane pnlCustomer;

        @FXML
        private Pane pnlOrders;

        @FXML
        private Pane pnlOverview;

        @FXML
        private Pane pnlMenus;
        private final OffreStageService os = new OffreStageService();
    public static StageController getInstance() {
        if (instance == null) {
            instance = new StageController();
        }
        return instance;
    }
        @Override
        public void initialize(URL location, ResourceBundle resources) {

           refreshPage();

        }
    private UserService userService=new UserService();
    private DossierStageService dossierStageService=new DossierStageService();
    public void refreshPage() {
        totalEtudiant.setText(String.valueOf(userService.getCountEtudiant()));
        System.out.println(userService.getCountEtudiant());
        totalPostule.setText(String.valueOf(dossierStageService.getCountDossier()));
        TotalStage.setText(String.valueOf(os.getCountOffre()));
        List<OffreStage> o = os.readAll();

        pnItems.getChildren().clear(); // Clear existing items

        for (OffreStage offreStage : o) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/ItemStage.fxml"));
                Node node = loader.load();

                ItemController itemController = loader.getController();
                itemController.setId(offreStage.getId());

                // Set data for the UI components
                ((Label) node.lookup("#titre")).setText(offreStage.getTitre());
                ((Label) node.lookup("#desc_stage")).setText(offreStage.getDescription());
                ((Label) node.lookup("#comp")).setText(offreStage.getCompetance());
                ((Label) node.lookup("#desc_soc")).setText(offreStage.getDescription_societe());
                ((Label) node.lookup("#nom_soc")).setText(offreStage.getNomSociete());
                ((Label) node.lookup("#type")).setText(offreStage.getTypeStage().name());

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

        // Add listener for the filter field
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            search(); // Call search method whenever text changes in the filterField
        });
    }
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
    @FXML
    void search() {
        List<OffreStage> listM = os.readAll();
        ObservableList<OffreStage> observableList = FXCollections.observableList(listM);
        FilteredList<OffreStage> filteredData = new FilteredList<>(observableList, p -> true);

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(stage -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                return String.valueOf(stage.getId()).toLowerCase().contains(lowerCaseFilter)
                        || stage.getTitre().toLowerCase().contains(lowerCaseFilter)
                        || stage.getDescription().toLowerCase().contains(lowerCaseFilter) ||
                        stage.getDescription_societe().toLowerCase().contains(lowerCaseFilter) ||
                        stage.getCompetance().toLowerCase().contains(lowerCaseFilter) ||
                        stage.getTypeStage().name().toLowerCase().contains(lowerCaseFilter);

            });

            // Clear previous items in the UI
            pnItems.getChildren().clear();

            // Update UI with the filtered data
            for (OffreStage stage : filteredData) {
                // Initialize node for each item
                Node node = null; // Update this line based on your actual FXML structure
                try {
                    node = FXMLLoader.load(getClass().getResource("/Admin/ItemStage.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (node != null) {
                    ((Label) node.lookup("#titre")).setText(stage.getTitre());
                    ((Label) node.lookup("#desc_stage")).setText(stage.getDescription());
                    ((Label) node.lookup("#comp")).setText(stage.getCompetance());
                    ((Label) node.lookup("#desc_soc")).setText(stage.getDescription_societe());
                    ((Label) node.lookup("#nom_soc")).setText(stage.getNomSociete());
                    ((Label) node.lookup("#type")).setText(stage.getTypeStage().name());

                    // Update other fields as needed
                    pnItems.getChildren().add(node); // Add the node to the UI
                }
            }
        });
    }


    @FXML
    public void clickAdd(ActionEvent actionEvent) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/AddStage.fxml"));
            BorderPane popupContent = loader.load();

            Popup popup = new Popup();
            popup.getContent().add(popupContent);
            AddStageController controller = loader.getController();
            controller.setPopup(popup);

            Stage primaryStage = (Stage) btnStage.getScene().getWindow();
            popup.show(primaryStage);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}



