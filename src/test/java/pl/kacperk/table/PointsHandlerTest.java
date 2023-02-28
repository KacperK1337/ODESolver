package pl.kacperk.table;

import javafx.scene.chart.XYChart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PointsHandlerTest {

    private PointsHandler underTest;

    @BeforeEach
    public void beforeEach() {
        underTest = new PointsHandler();
    }

    @Test
    public void update_correctValues_correctResult() {
        //given
        double t0 = 0;
        double x0 = 1;

        //when
        underTest.update(t0, x0);

        //then
        assertThat(underTest.getTValues().get(0)).isEqualTo(t0);
        assertThat(underTest.getXValues().get(0)).isEqualTo(x0);
        assertThat(underTest.getTValues().size()).isEqualTo(1);
        assertThat(underTest.getXValues().size()).isEqualTo(1);
    }

    @Test
    public void getSeries_correctValues_correctResult() {
        //given
        double t0 = 0;
        double x0 = 1;
        underTest.update(t0, x0);

        //when
        XYChart.Series<Double, Double> series = underTest.getSeries();

        //then
        assertThat(series.getData().get(0).getXValue()).isEqualTo(t0);
        assertThat(series.getData().get(0).getYValue()).isEqualTo(x0);
        assertThat(series.getData().size()).isEqualTo(1);
    }

    @Test
    public void getSeries_infiniteValues_emptySeries() {
        //given
        underTest.update(1, Double.POSITIVE_INFINITY);
        underTest.update(2, Double.NEGATIVE_INFINITY);

        //when
        XYChart.Series<Double, Double> series = underTest.getSeries();

        //then
        assertThat(series.getData()).isEmpty();
    }
}
