package pl.kacperk.math;

import org.junit.jupiter.api.Test;
import org.mariuszgromada.math.mxparser.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class EulerTest {

    @Test
    public void nextIteration_forwardMethod_correctResult() {
        //given
        Euler underTestForward = new Euler(EulerMethod.Forward);
        double t = 1;
        double x = 1;
        double h = 0.1;
        Function ode = new Function("f(t, x) = t + x");

        //when
        double result = underTestForward.nextIteration(t, x, h, ode);

        //then
        assertThat(result).isEqualTo(
                x + h * (ode.calculate(t, x))
        );
    }

    @Test
    public void nextIteration_midpointMethod_correctResult() {
        //given
        Euler underTestMidpoint = new Euler(EulerMethod.Midpoint);
        double t = 1;
        double x = 1;
        double h = 0.1;
        Function ode = new Function("f(t, x) = t + x");

        //when
        double result = underTestMidpoint.nextIteration(t, x, h, ode);

        //then
        assertThat(result).isEqualTo(
                x + h * (ode.calculate(t + (h / 2), x + (h / 2) * ode.calculate(t, x)))
        );
    }
}
