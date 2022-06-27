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

public class ODESolverController {
    private final ExceptionHandler exceptionHandler = new ExceptionHandler();
    private final ObservableList<PointTX> tableValues = FXCollections.observableArrayList();
    private final SaveHandler saveHandler = new SaveHandler(exceptionHandler);
    private final EquationHandler equationHandler = new EquationHandler(exceptionHandler);

    @FXML
    private Button btnCalculate; //To solve ODE

    @FXML
    private ChoiceBox<EulerMethod> methods; //Contains Euler's method variants

    @FXML
    private TextField tLeft; //Beginning of compartment (a)

    @FXML
    private TextField tRight; //End of compartment (b)

    @FXML
    private TextField step; //(h)

    @FXML
    private TextField initialCondition; //(x0)

    @FXML
    private TextField info; //Field to display informations/errors

    @FXML
    private TextField equation; //ODE

    @FXML
    private ScatterChart<Double, Double> txChart;

    @FXML
    private NumberAxis tAxis;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private Button btnSaveSeries; //To save table series

    @FXML
    private TextField textFileName; //Filename to save table series

    @FXML
    private TableView<PointTX> table;

    @FXML
    private TableColumn<PointTX, String> time;

    @FXML
    private TableColumn<PointTX, String> x;

    @FXML
    void btnCalculateClicked() {
        table.getItems().clear(); //Clearing table and chart on every new calculation
        txChart.getData().clear();
        boolean paramsParsed = false;
        try {
            double a = Double.parseDouble(tLeft.getText());  //Parsing params
            double b = Double.parseDouble(tRight.getText());
            double h = Double.parseDouble(step.getText());
            double x0 = Double.parseDouble(initialCondition.getText());
            paramsParsed = true;
            Function ode = equationHandler.getEquation(equation.getText());
            EulerMethod method = methods.getValue(); //Getting Euler's variant
            PointsHandler pointsHandler = new PointsHandler();
            ODEIntegrate odeIntegrate = new ODEIntegrate(
                    a, b, x0, ode, new Euler(method), pointsHandler, exceptionHandler);
            odeIntegrate.integrate(h); //Solving ODE

            tableValues.addAll(PointTX.getTXPoints
                    (pointsHandler.getTValues(), pointsHandler.getXValues())); //Adding points to table

            XYChart.Series<Double, Double> series = pointsHandler.getSeries();
            txChart.getData().addAll(series); //Creating chart on calculated points

            info.setStyle("-fx-border-color: #422775;");
            info.setText("Calculations successful");
        } catch (Exception e) { //If something above fails
            if (!paramsParsed) {
                exceptionHandler.setExceptionMessage("not all params have been entered");
            }
            info.setStyle("-fx-border-color: red;");
            info.setText("Cannot calculate, " + exceptionHandler.getExceptionMessage());
        }
    }

    @FXML
    void btnSaveSeriesClicked() {
        try {
            String fileName = textFileName.getText();
            saveHandler.saveTXPoints(fileName, tableValues); //Save series to entered file

            info.setStyle("-fx-border-color: #422775;");
            info.setText("Saved series to " + fileName);
        } catch (Exception e) { //If saving fails
            info.setStyle("-fx-border-color: red;");
            info.setText("Cannot save, " + exceptionHandler.getExceptionMessage());
        }
    }

    @FXML
    void initialize() {
        assert btnCalculate != null : "fx:id=\"btnCalculate\" was not injected: check your FXML file 'odeSolver.fxml'.";
        assert btnSaveSeries != null : "fx:id=\"btnSaveSeries\" was not injected: check your FXML file 'odeSolver.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'odeSolver.fxml'.";
        assert tLeft != null : "fx:id=\"tLeft\" was not injected: check your FXML file 'odeSolver.fxml'.";
        assert tRight != null : "fx:id=\"tRight\" was not injected: check your FXML file 'odeSolver.fxml'.";
        assert time != null : "fx:id=\"time\" was not injected: check your FXML file 'odeSolver.fxml'.";
        assert x != null : "fx:id=\"x\" was not injected: check your FXML file 'odeSolver.fxml'.";
        assert step != null : "fx:id=\"step\" was not injected: check your FXML file 'odeSolver.fxml'.";
        assert methods != null : "fx:id=\"method\" was not injected: check your FXML file 'odeSolver.fxml'.";
        assert initialCondition != null : "fx:id=\"x0\" was not injected: check your FXML file 'odeSolver.fxml'.";
        assert tAxis != null : "fx:id=\"tAxis\" was not injected: check your FXML file 'odeSolver.fxml'.";
        assert txChart != null : "fx:id=\"txChart\" was not injected: check your FXML file 'odeSolver.fxml'.";
        assert xAxis != null : "fx:id=\"xAxis\" was not injected: check your FXML file 'odeSolver.fxml'.";
        assert info != null : "fx:id=\"info\" was not injected: check your FXML file 'odeSolver.fxml'.";
        assert equation != null : "fx:id=\"equation\" was not injected: check your FXML file 'odeSolver.fxml'.";

        txChart.setLegendVisible(false); //Removing legend (not needed)
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        x.setCellValueFactory(new PropertyValueFactory<>("x"));
        table.setItems(tableValues);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); //Make table static
        methods.getItems().addAll(EulerMethod.Forward, EulerMethod.Midpoint);
        methods.setValue(EulerMethod.Midpoint); //For default set midpoint variant of Euler's method

        Platform.runLater(() -> table.requestFocus()); //Focus on table (to not cover text-fields prompt text
    }
}
