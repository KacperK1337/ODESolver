package pl.kacperk.math;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariuszgromada.math.mxparser.Function;
import pl.kacperk.exception.ExceptionHandler;
import pl.kacperk.table.PointsHandler;

import static org.assertj.core.api.Assertions.assertThat;

public class ODEIntegrateTest {

    private ODEIntegrate underTest;
    private double a;
    private double b;
    private double x0;
    private Function ode;
    private Euler euler;
    private PointsHandler pointsHandler;
    private ExceptionHandler exceptionHandler;
    private double h;

    @BeforeEach
    public void beforeEach() {
        a = 1;
        b = 5;
        x0 = 1;
        ode = new Function("f(t, x) = t + x");
        euler = new Euler(EulerMethod.Midpoint);
        pointsHandler = new PointsHandler();
        exceptionHandler = new ExceptionHandler();
        underTest = new ODEIntegrate(a, b, x0, ode, euler, pointsHandler, exceptionHandler);
        h = 0.1;
    }

    @Test
    public void integrate_correctValues_pointsHandlerUpdated() throws Exception {
        //when
        underTest.integrate(h);

        //then
        assertThat(pointsHandler.getTValues()).isNotEmpty();
        assertThat(pointsHandler.getXValues()).isNotEmpty();
        assertThat(pointsHandler.getTValues().size()).isEqualTo(pointsHandler.getXValues().size());
    }

    @Test
    public void integrate_correctValues_correctResult() throws Exception {
        //when
        underTest.integrate(h);

        //then
        assertThat(pointsHandler.getTValues().get(0)).isEqualTo(a);
        assertThat(pointsHandler.getXValues().get(0)).isEqualTo(x0);
        assertThat(pointsHandler.getTValues().get(1)).isEqualTo(a + h);
        assertThat(pointsHandler.getXValues().get(1)).isEqualTo(euler.nextIteration(a, x0, h, ode));
    }

}
