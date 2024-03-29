package pl.kacperk.math;

import org.mariuszgromada.math.mxparser.Function;

public class Euler {

    private final EulerMethod eulerMethod;

    public Euler(EulerMethod eulerMethod) {
        this.eulerMethod = eulerMethod;
    }

    public double nextIteration(double t, double x, double h, Function ode){
        double slope = 0;
        switch (eulerMethod) {
            case Forward -> slope = ode.calculate(t, x);
            case Midpoint -> slope = ode.calculate(t + (h / 2), x + (h / 2) * ode.calculate(t, x));
        }
        return x + h * slope;
    }

}
