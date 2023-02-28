package pl.kacperk;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.mariuszgromada.math.mxparser.Function;
import pl.kacperk.exception.ExceptionHandler;
import pl.kacperk.math.EquationHandler;
import pl.kacperk.math.Euler;
import pl.kacperk.math.EulerMethod;
import pl.kacperk.math.ODEIntegrate;
import pl.kacperk.save.SaveHandler;
import pl.kacperk.table.PointTX;
import pl.kacperk.table.PointsHandler;

public class MainController {

    private final ExceptionHandler exceptionHandler = new ExceptionHandler();
    private final ObservableList<PointTX> tableValues = FXCollections.observableArrayList();
    private final SaveHandler saveHandler = new SaveHandler(exceptionHandler);
    private final EquationHandler equationHandler = new EquationHandler(exceptionHandler);

    @FXML
    private Button btnCalculate;

    @FXML
    private ChoiceBox<EulerMethod> methods;

    @FXML
    private TextField tLeft;

    @FXML
    private TextField tRight;

    @FXML
    private TextField step;

    @FXML
    private TextField initialCondition;

    @FXML
    private TextField info;

    @FXML
    private TextField equation;

    @FXML
    private ScatterChart<Double, Double> txChart;

    @FXML
    private NumberAxis tAxis;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private Button btnSaveSeries;

    @FXML
    private TextField textFileName;

    @FXML
    private TableView<PointTX> table;

    @FXML
    private TableColumn<PointTX, String> time;

    @FXML
    private TableColumn<PointTX, String> x;

    @FXML
    void btnCalculateClicked() {
        table.getItems().clear();
        txChart.getData().clear();
        try {
            String tLeftText = tLeft.getText();
            String tRightText = tRight.getText();
            String stepText = step.getText();
            String initialConditionText = initialCondition.getText();
            exceptionHandler.checkEnteredParams(tLeftText, tRightText, stepText, initialConditionText);

            double a = Double.parseDouble(tLeftText);
            double b = Double.parseDouble(tRightText);
            double h = Double.parseDouble(stepText);
            double x0 = Double.parseDouble(initialConditionText);
            Function ode = equationHandler.getEquation(equation.getText());
            EulerMethod method = methods.getValue();
            PointsHandler pointsHandler = new PointsHandler();
            ODEIntegrate odeIntegrate = new ODEIntegrate(
                    a, b, x0, ode, new Euler(method), pointsHandler, exceptionHandler
            );
            odeIntegrate.integrate(h);
            tableValues.addAll(PointTX.getTXPoints(
                    pointsHandler.getTValues(), pointsHandler.getXValues()
            ));
            XYChart.Series<Double, Double> series = pointsHandler.getSeries();
            txChart.getData().addAll(series);

            info.setStyle("-fx-border-color: #422775;");
            info.setText("Calculations successful");
        } catch (Exception ex) {
            info.setStyle("-fx-border-color: red;");
            info.setText("Cannot calculate. " + exceptionHandler.getExceptionMessage());
        }
    }

    @FXML
    void btnSaveSeriesClicked() {
        try {
            String fileName = textFileName.getText();
            saveHandler.saveTXPoints(fileName, tableValues);

            info.setStyle("-fx-border-color: #422775;");
            info.setText("Saved series to " + fileName);
        } catch (Exception ex) {
            info.setStyle("-fx-border-color: red;");
            info.setText("Cannot save. " + exceptionHandler.getExceptionMessage());
        }
    }

    @FXML
    void initialize() {
        assert btnCalculate != null : "fx:id=\"btnCalculate\" was not injected: check your FXML file 'main.fxml'.";
        assert btnSaveSeries != null : "fx:id=\"btnSaveSeries\" was not injected: check your FXML file 'main.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'main.fxml'.";
        assert tLeft != null : "fx:id=\"tLeft\" was not injected: check your FXML file 'main.fxml'.";
        assert tRight != null : "fx:id=\"tRight\" was not injected: check your FXML file 'main.fxml'.";
        assert time != null : "fx:id=\"time\" was not injected: check your FXML file 'main.fxml'.";
        assert x != null : "fx:id=\"x\" was not injected: check your FXML file 'main.fxml'.";
        assert step != null : "fx:id=\"step\" was not injected: check your FXML file 'main.fxml'.";
        assert methods != null : "fx:id=\"method\" was not injected: check your FXML file 'main.fxml'.";
        assert initialCondition != null : "fx:id=\"x0\" was not injected: check your FXML file 'main.fxml'.";
        assert tAxis != null : "fx:id=\"tAxis\" was not injected: check your FXML file 'main.fxml'.";
        assert txChart != null : "fx:id=\"txChart\" was not injected: check your FXML file 'main.fxml'.";
        assert xAxis != null : "fx:id=\"xAxis\" was not injected: check your FXML file 'main.fxml'.";
        assert info != null : "fx:id=\"info\" was not injected: check your FXML file 'main.fxml'.";
        assert equation != null : "fx:id=\"equation\" was not injected: check your FXML file 'main.fxml'.";

        txChart.setLegendVisible(false);
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        x.setCellValueFactory(new PropertyValueFactory<>("x"));
        table.setItems(tableValues);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        methods.getItems().addAll(EulerMethod.Forward, EulerMethod.Midpoint);
        methods.setValue(EulerMethod.Midpoint);
        Platform.runLater(() -> table.requestFocus());
    }
}
