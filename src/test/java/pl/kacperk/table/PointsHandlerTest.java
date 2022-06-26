package pl.kacperk.table;

import javafx.scene.chart.XYChart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PointsHandlerTest {
    private PointsHandler pointsHandler;
    private XYChart.Series<Double, Double> series;

    @BeforeEach
    public void beforeEach() {
        pointsHandler = new PointsHandler();
    }

    @Test
    public void update_normalValues_correctResult() {
        //given
        double t0 = 0;
        double x0 = 1;

        //when
        pointsHandler.update(t0, x0);

        //then
        Assertions.assertEquals(pointsHandler.getTValues().get(0), t0);
        Assertions.assertEquals(pointsHandler.getXValues().get(0), x0);

        Assertions.assertEquals(pointsHandler.getTValues().size(), 1);
        Assertions.assertEquals(pointsHandler.getXValues().size(), 1);
    }

    @Test
    public void getSeries_normalValues_correctResult() {
        //given
        double t0 = 0;
        double x0 = 1;
        pointsHandler.update(t0, x0);

        //when
        series = pointsHandler.getSeries();

        //then
        Assertions.assertEquals(series.getData().get(0).getXValue(), t0);
        Assertions.assertEquals(series.getData().get(0).getYValue(), x0);

        Assertions.assertEquals(series.getData().size(), 1);
    }

    @Test
    public void getSeries_infiniteValues_emptySeries() {
        //given
        pointsHandler.update(1, Double.POSITIVE_INFINITY);
        pointsHandler.update(2, Double.NEGATIVE_INFINITY);

        //when
        series = pointsHandler.getSeries();

        //then
        org.assertj.core.api.Assertions.assertThat(series.getData()).isEmpty();
    }
}
