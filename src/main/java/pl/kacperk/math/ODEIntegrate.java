package pl.kacperk.math;

import org.mariuszgromada.math.mxparser.Function;
import pl.kacperk.exception.ExceptionHandler;
import pl.kacperk.table.PointsHandler;

public class ODEIntegrate {

    private final double a;
    private final double b;
    private final double x0;
    private final Function ode;
    private final Euler euler;
    private final PointsHandler pointsHandler;
    private final ExceptionHandler exceptionHandler;

    public ODEIntegrate(double a, double b, double x0, Function ode, Euler euler,
                        PointsHandler pointsHandler, ExceptionHandler exceptionHandler) {
        this.a = a;
        this.b = b;
        this.x0 = x0;
        this.ode = ode;
        this.euler = euler;
        this.pointsHandler = pointsHandler;
        this.exceptionHandler = exceptionHandler;
    }

    public void integrate(double h) throws Exception {
        exceptionHandler.checkStepAndCompartment(h, a, b);
        int n = (int) (((b - a) / h) + 1);
        double[] t_i = new double[n];
        double[] x_i = new double[n];
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                t_i[i] = a;
                x_i[i] = x0;
            } else {
                t_i[i] += a + (i * h);
                x_i[i] = euler.nextIteration(t_i[i - 1], x_i[i - 1], h, ode);
            }
            pointsHandler.update(t_i[i], x_i[i]);
        }
    }
}
