package Controllers;
import entities.OffreStage;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import services.OffreStageService;

import javax.swing.*;

public class AfficherOffreStageController {
    private final OffreStageService os = new OffreStageService();

    @FXML
    private TableColumn<OffreStage, Integer> col_id;

    @FXML
    private TableColumn<OffreStage, String> col_titre;

    @FXML
    private TableColumn<OffreStage, String> col_desc;


    @FXML
    private TextField RDescription;

    @FXML
    private TextField RList;

    @FXML
    private TextField RTitre;
    @FXML
    private TableView<OffreStage> table_offre;

    @FXML
    private TextField filterField;




    int index = -1;

    public void setTable_offre(TableView<OffreStage> table_offre) {
        this.table_offre = table_offre;
    }

    public void setRDescription(String RDescription) {
        this.RDescription.setText(RDescription);
    }

    public void setRList(String RList) {
        this.RList.setText(RList);
    }

    public void setRTitre(String RTitre) {
        this.RTitre.setText(RTitre);
    }


    @FXML
    void getSelected(MouseEvent event) {

            index = table_offre.getSelectionModel().getSelectedIndex();
            if (index <= -1){

                return;
            }
            RDescription.setText(col_desc.getCellData(index).toString());
            RTitre.setText(col_titre.getCellData(index).toString());


    }

    int getId() {
        int index = table_offre.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            return table_offre.getItems().get(index).getId();
        } else {
            return -1; // or any default value to indicate no selection
        }
    }

    public void Edit() {

            String value1 = RTitre.getText();
            String value2 = RDescription.getText();
            int selectedId = getId();
            if (selectedId != -1) { // Proceed only if a valid selection exists
                //os.update(new OffreStage(selectedId, value1, value2)); // Assuming OffreStage constructor accepts ID, title, and description
                UpdateTable();
            } else {
                // Handle case where no selection is made
                // For example, show a message to the user indicating no selection
                JOptionPane.showMessageDialog(null, "Please select an item.");
            }
      search();


    }


    public void UpdateTable(){
        col_id.setCellValueFactory(new PropertyValueFactory<OffreStage,Integer>("id"));
        col_titre.setCellValueFactory(new PropertyValueFactory<OffreStage,String>("titre"));
        col_desc.setCellValueFactory(new PropertyValueFactory<OffreStage,String>("description"));


        List<OffreStage> listM = os.readAll();
        ObservableList<OffreStage> observableList = FXCollections.observableList(listM);
        table_offre.setItems(observableList);

        table_offre.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        table_offre.getSelectionModel().selectedItemProperty().addListener(this::selectionChanged);

    }

    private void selectionChanged(ObservableValue<? extends OffreStage> observable, OffreStage oldVal, OffreStage newVal) {
        ObservableList<OffreStage> selectedItems = table_offre.getSelectionModel().getSelectedItems();
        String getSelectedItem = selectedItems.isEmpty() ? "No Selected Item" : newVal.toString();

    }

    public void Delete(){
       int selectedId = getId();
        os.delete(selectedId);
        UpdateTable();
        RTitre.setText("");
        RDescription.setText("");
        search();

    }
    ObservableList<OffreStage> dataList;
    @FXML
    void search() {
        List<OffreStage> listM = os.readAll();
        ObservableList<OffreStage> observableList = FXCollections.observableList(listM);
        FilteredList<OffreStage> filteredData = new FilteredList<>(observableList, p -> true);

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(stage -> {
                if (newValue == null|| newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                return String.valueOf(stage.getId()).toLowerCase().contains(lowerCaseFilter)
                ||stage.getTitre().toLowerCase().contains(lowerCaseFilter) || stage.getDescription().toLowerCase().contains(lowerCaseFilter) ;
            });
        });

        SortedList<OffreStage> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table_offre.comparatorProperty());
        table_offre.setItems(sortedData);
    }

}
