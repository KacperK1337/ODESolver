package pl.kacperk.table;

import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class PointTXTest {

    @Test
    public void getTXPoints_normalValues_correctResult() {
        //given
        double t0 = 1.123;
        double x0 = 1.123;
        ArrayList<Double> t = new ArrayList<>();
        ArrayList<Double> x = new ArrayList<>();
        t.add(t0);
        x.add(x0);
        NumberFormat formatter = new DecimalFormat("#0.0000000000");

        //when
        ArrayList<PointTX> points = PointTX.getTXPoints(t, x);

        //then
        assertThat(points.get(0).getTime()).isEqualTo(formatter.format(t0));
        assertThat(points.get(0).getX()).isEqualTo(formatter.format(x0));
        assertThat(points.size()).isEqualTo(1);
    }
}
