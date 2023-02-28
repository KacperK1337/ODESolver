package pl.kacperk.table;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class PointTX {

    private final String time;
    private final String x;

    public PointTX(String time, String x) {
        this.time = time;
        this.x = x;
    }

    public String getTime() {
        return time;
    }

    public String getX() {
        return x;
    }

    public static ArrayList<PointTX> getTXPoints(ArrayList<Double> t, ArrayList<Double> x) {
        NumberFormat formatter = new DecimalFormat("#0.0000000000");
        ArrayList<PointTX> points = new ArrayList<>();
        for (int i = 0; i < t.size(); i++) {
            points.add(
                    new PointTX(
                            formatter.format(t.get(i)), formatter.format(x.get(i))
                    )
            );
        }
        return points;
    }
}
