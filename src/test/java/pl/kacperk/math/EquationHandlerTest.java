package pl.kacperk.math;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.kacperk.exception.ExceptionHandler;

public class EquationHandlerTest {
    private EquationHandler equationHandler;
    private ExceptionHandler exceptionHandler;
    private String equationString;

    @BeforeEach
    public void beforeEach() {
        exceptionHandler = new ExceptionHandler();
        equationHandler = new EquationHandler(exceptionHandler);
    }

    @Test
    public void getEquation_correctSyntax_success() throws Exception {
        //given
        equationString = "t+x";

        //when
        equationHandler.getEquation(equationString);

        //then
        org.assertj.core.api.Assertions.assertThat(exceptionHandler.getExceptionMessage()).isNull();
    }

    @Test
    public void getEquation_emptyEquationString_throwsException() {
        //given
        equationString = "";

        //when
        Throwable throwable = org.assertj.core.api.Assertions.catchThrowable(() ->
                equationHandler.getEquation(equationString));

        //then
        org.assertj.core.api.Assertions.assertThat(throwable)
                .isInstanceOf(Exception.class)
                .hasMessage(exceptionHandler.getExceptionMessage());
    }

    @Test
    public void getEquation_incorrectSyntax_throwsException() {
        //given
        equationString = "t+z";

        //when
        Throwable throwable = org.assertj.core.api.Assertions.catchThrowable(() ->
                equationHandler.getEquation(equationString));

        //then
        org.assertj.core.api.Assertions.assertThat(throwable)
                .isInstanceOf(Exception.class)
                .hasMessage(exceptionHandler.getExceptionMessage());
    }
}
