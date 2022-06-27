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
    private Function ode;

    @BeforeEach
    public void beforeEach() throws Exception {
        t = 1;
        x = 2;
        h = 0.1;
        String equationString = "t+x";
        ExceptionHandler exceptionHandler = new ExceptionHandler();
        ode = new EquationHandler(exceptionHandler).getEquation(equationString);
    }

    @Test
    public void nextIteration_forwardMethod_correctResult() {
        //given
        Euler eulerForward = new Euler(EulerMethod.Forward);

        //when
        double result = eulerForward.nextIteration(t, x, h, ode);

        //then
        Assertions.assertEquals(result, x + h * (ode.calculate(t, x)));
    }

    @Test
    public void nextIteration_midpointMethod_correctResult() {
        //given
        Euler eulerMidpoint = new Euler(EulerMethod.Midpoint);

        //when
        double result = eulerMidpoint.nextIteration(t, x, h, ode);

        //then
        Assertions.assertEquals(result, x + h * (ode.calculate(t + (h / 2), x + (h / 2) *
                ode.calculate(t, x))));
    }
}
