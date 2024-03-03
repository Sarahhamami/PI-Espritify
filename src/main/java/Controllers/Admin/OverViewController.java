package Controllers.Admin;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import services.DossierStageService;
import services.EntretienService;
import services.OffreStageService;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.control.Tooltip;
import services.UserService;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

public class OverViewController implements Initializable {
    @FXML
    private Label TotalStage;
    @FXML
    private Label totalEtudiant;

    @FXML
    private Label totalPostule;

    @FXML
    private BarChart<String, Number> barchart;
    @FXML
    private LineChart<String, Number> linechart;

    @FXML
    private StackedBarChart<String, Number> stackedChart;
    @FXML
    private PieChart pieChart;
    @FXML
    private Button btnOverview;

    @FXML
    private Button btnStage;

    @FXML
    private Button btnDossierStage;
    @FXML
    private Button btnEntretien;
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

    public void initializeBarchart(){
        // Sample data - replace this with actual data from your application
        int remoteStages = offreStageService.getCountType("remote");
        int onsiteStages = offreStageService.getCountType("presentiel");
        barchart.setStyle("-fx-background-color: white;");
        // Create axes
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        // Create bar chart
        barchart.setTitle("Nombre de stages par type");
        xAxis.setLabel("Type de stage");
        yAxis.setLabel("Nombre de stages");

        // Add data to the chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Nombre de stages");
        series.getData().add(new XYChart.Data<>("À distance", remoteStages));
        series.getData().add(new XYChart.Data<>("En présentiel", onsiteStages));

        // Add series to chart
        barchart.getData().add(series);

        // Apply custom color to all bars
        for (XYChart.Series<String, Number> s : barchart.getData()) {
            for (XYChart.Data<String, Number> d : s.getData()) {
                applyCustomColorToBar(d.getNode(), "#990000");
            }
        }
    }
    public void initializeLinechart(){
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Month");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Value");

        // Create line chart
        linechart.setTitle("Nombre d'entretien par mois");
        linechart.setCreateSymbols(true); // Show symbols on data points
        linechart.setLegendVisible(true); // Show legend

        // Add data series to chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Series 1");

        // Add sample data points
        series.getData().add(new XYChart.Data<>("Janvier", entretienService.getCountByMonth(2024,1)));
        series.getData().add(new XYChart.Data<>("Février", entretienService.getCountByMonth(2024,2)));
        series.getData().add(new XYChart.Data<>("Mars", entretienService.getCountByMonth(2024,3)));
        series.getData().add(new XYChart.Data<>("Avril", entretienService.getCountByMonth(2024,4)));
        series.getData().add(new XYChart.Data<>("Mai", entretienService.getCountByMonth(2024,5)));
        series.getData().add(new XYChart.Data<>("Juin", entretienService.getCountByMonth(2024,6)));
        series.getData().add(new XYChart.Data<>("Juillet", entretienService.getCountByMonth(2024,7)));
        series.getData().add(new XYChart.Data<>("Août", entretienService.getCountByMonth(2024,8)));
        // Add series to chart
        linechart.getData().add(series);
        performForecasting();
        addTrendLine();
    }


    private void addTrendLine() {
        // Fit a straight line to the data
        double[] xValues = new double[]{1, 2, 3, 4, 5, 6, 7, 8}; // Month numbers
        double[] yValues = new double[8]; // Number of entretiens for each month
        for (int i = 0; i < 8; i++) {
            yValues[i] = entretienService.getCountByMonth(2024, i + 1);
        }

        LinearInterpolator interpolator = new LinearInterpolator();
        PolynomialSplineFunction trendFunction = interpolator.interpolate(xValues, yValues);

        // Add trend line to the chart
        XYChart.Series<String, Number> trendSeries = new XYChart.Series<>();
        trendSeries.setName("Trend Line");
        for (int i = 0; i < 8; i++) {
            double trendValue = trendFunction.value(i + 1);
            trendSeries.getData().add(new XYChart.Data<>(String.valueOf(i + 1), trendValue));
        }
        linechart.getData().add(trendSeries);
    }

    private void performForecasting() {
        // Use forecasting techniques to predict future values
        // You can use ARIMA, exponential smoothing, or other forecasting methods here
        // Forecasting code goes here...
    }

    private OffreStageService offreStageService=new OffreStageService();
    private EntretienService entretienService=new EntretienService();
    public void initializeStackedchart(){
        Map<String, Map<String, Integer>> data = entretienService.getDataByLocationAndDate();

        // Create axes
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Date");
        yAxis.setLabel("Number of Stages");

        // Create stacked bar chart
        stackedChart.setTitle("Nombre d'entretien par lieu et Date");
        stackedChart.setCategoryGap(10); // Gap between categories

        stackedChart.setLegendVisible(true); // Show legend
        stackedChart.setLegendSide(Side.RIGHT);
        // Add data to the chart
        for (String location : data.keySet()) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(location);

            Map<String, Integer> locationData = data.get(location);
            for (String date : locationData.keySet()) {
                series.getData().add(new XYChart.Data<>(date, locationData.get(date)));
            }

            stackedChart.getData().add(series);
        }
    }

    public void initializePiechart(){

        Map<String, Integer> etatCountMap = entretienService.getCountByEtat();
        // Add data to the pie chart
        for (String etat : etatCountMap.keySet()) {
            String label;
            if (etat.equals("1")) {
                label = "Accepter";
            } else {
                label = "Réfuser";
            }
            PieChart.Data data = new PieChart.Data(label, etatCountMap.get(etat));
            pieChart.getData().add(data);
            pieChart.setTitle("Acceptation vs. Refus des Demandes");
            // Add tooltip to show count
            Tooltip tooltip = new Tooltip();
            tooltip.setText(label + ": " + etatCountMap.get(etat));
            Tooltip.install(data.getNode(), tooltip);
        }
    }
    private UserService userService=new UserService();
    private DossierStageService dossierStageService=new DossierStageService();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       initializeBarchart();
       initializeLinechart();
       initializeStackedchart();
       initializePiechart();
       totalEtudiant.setText(String.valueOf(userService.getCountEtudiant()));
        totalPostule.setText(String.valueOf(dossierStageService.getCountDossier()));
        TotalStage.setText(String.valueOf(offreStageService.getCountOffre()));

    }

    // Helper method to calculate total count of all states

    // Helper method to calculate total count of all states
    private int getTotalCount(Map<String, Integer> etatCountMap) {
        int totalCount = 0;
        for (int count : etatCountMap.values()) {
            totalCount += count;
        }
        return totalCount;


    }


    private void applyCustomColorToBar(javafx.scene.Node bar, String css) {
        if (bar != null) {
            bar.setStyle("-fx-bar-fill: " + css + ";");
        }
    }
}
