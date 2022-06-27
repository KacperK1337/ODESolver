package pl.kacperk.math;

import org.mariuszgromada.math.mxparser.Function;
import pl.kacperk.exception.ExceptionHandler;
import pl.kacperk.table.PointsHandler;

//Performs ODE integration with Euler's method (variant is selected at ODEIteration constructor)
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
        checkForExceptions(h);
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

    private void checkForExceptions(double h) throws Exception {
        String exceptionMessage;
        if (h <= 0) {
            exceptionMessage = "Step h cannot be smaller or equal 0";
            exceptionHandler.setExceptionMessage(exceptionMessage);
            exceptionHandler.throwNewException();
        }
        if (b <= a) {
            exceptionMessage = "Compartment end (b) cannot be before or equal to beginning (a)";
            exceptionHandler.setExceptionMessage(exceptionMessage);
            exceptionHandler.throwNewException();
        }
        if (h >= (b - a)) {
            exceptionMessage = "Step h is greater or equal compartment length";
            exceptionHandler.setExceptionMessage(exceptionMessage);
            exceptionHandler.throwNewException();
        }
    }
}
