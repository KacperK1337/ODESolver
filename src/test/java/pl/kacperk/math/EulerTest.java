package pl.kacperk.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariuszgromada.math.mxparser.Function;
import pl.kacperk.exception.ExceptionHandler;

public class EulerTest {
    private double t;
    private double x;
    private double h;
    private String ode;
    private ExceptionHandler exceptionHandler;
    private Function odeFunction;

    @BeforeEach
    public void beforeEach() throws Exception {
        t = 1;
        x = 2;
        h = 0.1;
        ode = "t+x";
        exceptionHandler = new ExceptionHandler();
        odeFunction = new EquationHandler(exceptionHandler).getEquation(ode);
    }

    @Test
    public void nextIteration_forwardMethod_correctResult() throws Exception {
        //given
        Euler eulerForward = new Euler(EulerMethod.Forward);

        //when
        double result = eulerForward.nextIteration(t, x, h, ode, exceptionHandler);

        //then
        Assertions.assertEquals(result, x + h * (odeFunction.calculate(t, x)));
    }

    @Test
    public void nextIteration_midpointMethod_correctResult() throws Exception {
        //given
        Euler eulerMidpoint = new Euler(EulerMethod.Midpoint);

        //when
        double result = eulerMidpoint.nextIteration(t, x, h, ode, exceptionHandler);

        //then
        Assertions.assertEquals(result, x + h * (odeFunction.calculate(t + (h / 2), x + (h / 2) *
                odeFunction.calculate(t, x))));
    }
}
