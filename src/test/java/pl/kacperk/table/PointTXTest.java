package pl.kacperk.table;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class PointTXTest {
    private ArrayList<Double> t;
    private ArrayList<Double> x;

    @BeforeEach
    public void beforeEach() {
        t = new ArrayList<>();
        x = new ArrayList<>();
    }

    @Test
    public void getTXPoints_normalValues_correctResult() {
        //given
        double t0 = 0;
        double x0 = 1;
        NumberFormat formatter = new DecimalFormat("#0.0000000000");

        //when
        t.add(t0);
        x.add(x0);
        ArrayList<PointTX> points = PointTX.getTXPoints(t, x);

        //then
        Assertions.assertEquals(points.get(0).getTime(), formatter.format(t0));
        Assertions.assertEquals(points.get(0).getX(), formatter.format(x0));

        Assertions.assertEquals(points.size(), 1);
    }
}
