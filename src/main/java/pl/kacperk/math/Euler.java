package pl.kacperk.math;

import org.mariuszgromada.math.mxparser.Function;
import pl.kacperk.exception.ExceptionHandler;

//Calculate next ODE iteration (x value) depending on Euler method used
//Supports forward and midpoint variants
public class Euler {
    private final EulerMethod eulerMethod;

    public Euler(EulerMethod eulerMethod) {
        this.eulerMethod = eulerMethod;
    }

    public double nextIteration(double t, double x, double h, String ode, ExceptionHandler exceptionHandler) throws Exception {
        Function odeFunction = new EquationHandler(exceptionHandler).getEquation(ode);
        double slope = 0;
        switch (eulerMethod) {
            case Forward -> slope = odeFunction.calculate(t, x);
            case Midpoint -> slope =
                    odeFunction.calculate(t + (h / 2), x + (h / 2) * odeFunction.calculate(t, x));
        }
        return x + h * slope;
    }
}
