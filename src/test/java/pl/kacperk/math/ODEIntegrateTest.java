package pl.kacperk.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mariuszgromada.math.mxparser.Function;
import pl.kacperk.exception.ExceptionHandler;
import pl.kacperk.table.PointsHandler;

public class ODEIntegrateTest {
    private ODEIntegrate odeIntegrate;
    private double a;
    private double b;
    private double x0;
    private Function ode;
    private Euler euler;
    private PointsHandler pointsHandler;
    private EquationHandler equationHandler;
    private ExceptionHandler exceptionHandler;
    private double h;

    @BeforeEach
    public void beforeEach() throws Exception {
        x0 = 1;
        String equationString = "t+x";
        euler = new Euler(EulerMethod.Midpoint);
        pointsHandler = new PointsHandler();
        exceptionHandler = new ExceptionHandler();
        ode = new EquationHandler(exceptionHandler).getEquation(equationString);
    }

    @Test
    public void integrate_correctValues_pointsHandlerUpdated() throws Exception {
        //given
        a = 1;
        b = 5;
        h = 0.1;
        odeIntegrate = new ODEIntegrate(a, b, x0, ode, euler, pointsHandler, exceptionHandler);

        //when
        odeIntegrate.integrate(h);

        //then
        org.assertj.core.api.Assertions.assertThat(pointsHandler.getTValues()).isNotEmpty();
        org.assertj.core.api.Assertions.assertThat(pointsHandler.getXValues()).isNotEmpty();
        Assertions.assertEquals(pointsHandler.getTValues().size(), pointsHandler.getXValues().size());

        org.assertj.core.api.Assertions.assertThat(exceptionHandler.getExceptionMessage()).isNull();
    }

    @Test
    public void integrate_correctValues_correctResult() throws Exception {
        //given
        a = 1;
        b = 5;
        h = 0.1;
        odeIntegrate = new ODEIntegrate(a, b, x0, ode, euler, pointsHandler, exceptionHandler);

        //when
        odeIntegrate.integrate(h);

        //then
        Assertions.assertEquals(pointsHandler.getTValues().get(0), a);
        Assertions.assertEquals(pointsHandler.getXValues().get(0), x0);

        Assertions.assertEquals(pointsHandler.getTValues().get(1), a + h);
        Assertions.assertEquals(pointsHandler.getXValues().get(1),
                euler.nextIteration(a, x0, h, ode));

        org.assertj.core.api.Assertions.assertThat(exceptionHandler.getExceptionMessage()).isNull();
    }

    @ParameterizedTest
    @CsvSource({"-1", "0"})
    public void integrate_stepBelowOrEqualZero_throwsException(double step) {
        //given
        a = 1;
        b = 5;
        h = step;
        odeIntegrate = new ODEIntegrate(a, b, x0, ode, euler, pointsHandler, exceptionHandler);

        //when
        Throwable throwable =
                org.assertj.core.api.Assertions.catchThrowable(() -> odeIntegrate.integrate(h));

        //then
        org.assertj.core.api.Assertions.assertThat(throwable)
                .isInstanceOf(Exception.class)
                .hasMessage(exceptionHandler.getExceptionMessage());
    }

    @ParameterizedTest
    @CsvSource({"5, 1", "1, 1"})
    public void integrate_compartmentEndBeforeOrEqualStart_throwsException(double start, double end) {
        //given
        a = start;
        b = end;
        h = 0.1;
        odeIntegrate = new ODEIntegrate(a, b, x0, ode, euler, pointsHandler, exceptionHandler);

        //when
        Throwable throwable =
                org.assertj.core.api.Assertions.catchThrowable(() -> odeIntegrate.integrate(h));

        //then
        org.assertj.core.api.Assertions.assertThat(throwable)
                .isInstanceOf(Exception.class)
                .hasMessage(exceptionHandler.getExceptionMessage());
    }

    @ParameterizedTest
    @CsvSource({"5", "4"})
    public void integrate_stepGreaterOrEqualCompartmentLength_throwsException(double step) {
        //given
        a = 1;
        b = 5;
        h = step;
        odeIntegrate = new ODEIntegrate(a, b, x0, ode, euler, pointsHandler, exceptionHandler);

        //when
        Throwable throwable =
                org.assertj.core.api.Assertions.catchThrowable(() -> odeIntegrate.integrate(h));

        //then
        org.assertj.core.api.Assertions.assertThat(throwable)
                .isInstanceOf(Exception.class)
                .hasMessage(exceptionHandler.getExceptionMessage());
    }
}
