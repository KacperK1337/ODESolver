package pl.kacperk.table;

import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public class PointsHandler {

    private final ArrayList<Double> tValues = new ArrayList<>();
    private final ArrayList<Double> xValues = new ArrayList<>();

    public PointsHandler() {
    }

    public ArrayList<Double> getTValues() {
        return tValues;
    }

    public ArrayList<Double> getXValues() {
        return xValues;
    }

    public void update(double t, double x) {
        tValues.add(t);
        xValues.add(x);
    }

    public XYChart.Series<Double, Double> getSeries() {
        XYChart.Series<Double, Double> series = new XYChart.Series<>();
        for (int i = 0; i < tValues.size(); i++) {
            Double tValue = tValues.get(i);
            Double xValue = xValues.get(i);
            if (!xValue.isInfinite()) {
                series
                        .getData()
                        .add(new XYChart.Data<>(tValue, xValue));
            }
        }
        return series;
    }
}
