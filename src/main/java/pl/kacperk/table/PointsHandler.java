package pl.kacperk.table;

import javafx.scene.chart.XYChart;

import java.util.ArrayList;

//Stores calculated t and x values
//Can create XYChart series.
public class PointsHandler {
    private final ArrayList<Double> tValues = new ArrayList<>();
    private final ArrayList<Double> xValues = new ArrayList<>();

    public PointsHandler() {
    }

    public void update(double t, double x) {
        tValues.add(t);
        xValues.add(x);
    }

    public ArrayList<Double> getTValues() {
        return tValues;
    }

    public ArrayList<Double> getXValues() {
        return xValues;
    }

    public XYChart.Series<Double, Double> getSeries() {
        XYChart.Series<Double, Double> series = new XYChart.Series<>();
        for (int i = 0; i < tValues.size(); i++) {
            if (!xValues.get(i).isInfinite()) { //To not put infinite values on chart
                series.getData().add(new XYChart.Data<>(tValues.get(i), xValues.get(i)));
            }
        }
        return series;
    }
}
