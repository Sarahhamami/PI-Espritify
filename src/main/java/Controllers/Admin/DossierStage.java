package Controllers.Admin;

import entities.Dossier_stage;
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
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import services.DossierStageService;
import services.OffreStageService;
import services.UserService;

public class DossierStage implements Initializable {
    @FXML
    private Label TotalStage;
    @FXML
    private Label totalEtudiant;

    @FXML
    private Label totalPostule;


    @FXML
    private Button btnStage;

    @FXML
    private Button btnMenus;

    @FXML
    private Button btnDossierStage;

    @FXML
    private Button btnOverview;

    @FXML
    private Button btnEntretien;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSignout;

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

    private UserService userService=new UserService();
    private OffreStageService offreStageService=new OffreStageService();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        refreshPage();

    }
    private static DossierStage instance;
    public static DossierStage getInstance() {
        if (instance == null) {
            instance = new DossierStage();
        }
        return instance;
    }
    DossierStageService ds=new DossierStageService();
    public void refreshPage() {
        totalEtudiant.setText(String.valueOf(userService.getCountEtudiant()));

        totalPostule.setText(String.valueOf(ds.getCountDossier()));
        TotalStage.setText(String.valueOf(offreStageService.getCountOffre()));
        List<Dossier_stage> o = ds.readAll();

        pnItems.getChildren().clear(); // Clear existing items
        OffreStageService os= new OffreStageService();
        UserService us= new UserService();
        for (Dossier_stage dossierStage : o) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/ItemDossier.fxml"));
                Node node = loader.load();

                ItemDossier itemController = loader.getController();
                itemController.setId_user(dossierStage.getId());
                itemController.setId_offre(dossierStage.getId_offre());
                itemController.setUrlCopie_cin(dossierStage.getCopie_cin());
                itemController.setUrlCv(dossierStage.getCv());
                itemController.setUrlConvention(dossierStage.getConvention());
                // Set data for the UI components
                OffreStage offreStage= null;
                offreStage= os.readById(dossierStage.getId_offre());
                User user= null;
                user= us.readById(dossierStage.getId());

                 //((Button) node.lookup("#cv"));
                //((Label) node.lookup("#convention")).setText(dossierStage.getConvention());
                //((Label) node.lookup("#copie_cin")).setText(dossierStage.getCopie_cin());

                ((Label) node.lookup("#offre")).setText(offreStage.getTitre());
                ((Label) node.lookup("#user")).setText(user.getNom());
                itemController.setOffre_stage(offreStage.getTitre());
                itemController.setNom_etudiant(user.getNom());
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
    public void ajouterDossier(ActionEvent actionEvent) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/AddDossier.fxml"));
            BorderPane popupContent = loader.load();

            Popup popup = new Popup();
            popup.getContent().add(popupContent);
            AddDossierController controller = loader.getController();

            controller.setPopup(popup);
            Stage primaryStage = (Stage) btnStage.getScene().getWindow();
            popup.show(primaryStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void search() {
        List<Dossier_stage> dossierList = ds.readAll();
        OffreStageService offreStageService = new OffreStageService();
        UserService userService = new UserService();
        ObservableList<Dossier_stage> observableList = FXCollections.observableList(dossierList);
        FilteredList<Dossier_stage> filteredData = new FilteredList<>(observableList, p -> true);

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(dossier -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                User user = userService.readById(dossier.getId());
                OffreStage offreStage = offreStageService.readById(dossier.getId_offre());

                return user.getNom().toLowerCase().contains(lowerCaseFilter)
                        || offreStage.getTitre().toLowerCase().contains(lowerCaseFilter);
            });
        });

        // Clear previous items in the UI
        pnItems.getChildren().clear();

        // Update UI with the filtered data
        for (Dossier_stage stage : filteredData) {
            // Load FXML file for each item
            Node node = null;
            try {
                node = FXMLLoader.load(getClass().getResource("/Admin/ItemDossier.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (node != null) {
                ((Label) node.lookup("#offre")).setText(offreStageService.readById(stage.getId_offre()).getTitre());
                ((Label) node.lookup("#user")).setText(userService.readById(stage.getId()).getNom());

                // Add the node to the UI
                pnItems.getChildren().add(node);
            }
        }
    }

}
