package pl.kacperk.exception;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mariuszgromada.math.mxparser.Function;
import pl.kacperk.table.PointTX;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ExceptionHandlerTest {

    private ExceptionHandler underTest;

    @BeforeEach
    void setUp() {
        underTest = new ExceptionHandler();
    }

    @Test
    void checkStringEquation_notEmptyStringEquation_success() throws Exception {
        // given
        String stringEquation = "test";

        // when
        underTest.checkStringEquation(stringEquation);

        // then
        assertThat(underTest.getExceptionMessage()).isNull();
    }

    @Test
    void checkStringEquation_emptyStringEquation_throwsException() {
        // given
        String stringEquation = "";

        // when
        // then
        assertThatThrownBy(() -> underTest.checkStringEquation(stringEquation))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Equation syntax was not entered.");
    }

    @Test
    void checkEquation_correctEquationSyntax_success() throws Exception {
        // given
        Function equation = new Function("f(t, x) = t + x");

        // when
        underTest.checkEquation(equation);

        // then
        assertThat(underTest.getExceptionMessage()).isNull();
    }

    @Test
    void checkEquation_incorrectEquationSyntax_throwsException() {
        // given
        Function equation = new Function("f(t, x) = t + abc");

        // when
        // then
        assertThatThrownBy(() -> underTest.checkEquation(equation))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Invalid equation syntax.");
    }

    @Test
    void checkStepAndCompartment_correctStepAndCompartment_success() throws Exception {
        // given
        double h = 1;
        double a = 1;
        double b = 5;

        // when
        underTest.checkStepAndCompartment(h, a, b);

        // then
        assertThat(underTest.getExceptionMessage()).isNull();
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, 0})
    void checkStepAndCompartment_incorrectStep_throwsException(double h) {
        // given
        double a = 1;
        double b = 5;

        // when
        // then
        assertThatThrownBy(() -> underTest.checkStepAndCompartment(h, a, b))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Step h is smaller or equal 0.");
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1",
            "1, 0"
    })
    void checkStepAndCompartment_incorrectCompartment_throwsException(double a, double b) {
        // given
        double h = 1;

        // when
        // then
        assertThatThrownBy(() -> underTest.checkStepAndCompartment(h, a, b))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Compartment end (b) is less or equal beginning (a).");
    }

    @ParameterizedTest
    @ValueSource(doubles = {4, 5})
    void checkStepAndCompartment_incorrectStepToCompartment_throwsException(double h) {
        // given
        double a = 1;
        double b = 5;

        // when
        // then
        assertThatThrownBy(() -> underTest.checkStepAndCompartment(h, a, b))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Step h is greater or equal compartment length.");
    }

    @Test
    void checkFileName_correctFileName_success() throws Exception {
        // given
        String fileName = "test.txt";

        // when
        underTest.checkFileName(fileName);

        // then
        assertThat(underTest.getExceptionMessage()).isNull();
    }

    @Test
    void checkFileName_emptyFileName_throwException() {
        // given
        String fileName = "";

        // when
        // then
        assertThatThrownBy(() -> underTest.checkFileName(fileName))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("File name was not entered.");
    }

    @Test
    void checkFileName_notTextFile_throwException() {
        // given
        String fileName = "test.jpg";

        // when
        // then
        assertThatThrownBy(() -> underTest.checkFileName(fileName))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Entered file is not a text file.");
    }

    @Test
    void checkPoints_correctPoints_success() throws Exception {
        // given
        ObservableList<PointTX> points = FXCollections.observableArrayList();
        points.add(new PointTX("1", "1"));

        // when
        underTest.checkPoints(points);

        // then
        assertThat(underTest.getExceptionMessage()).isNull();
    }

    @Test
    void checkPoints_emptyPoints_throwsException() {
        // given
        ObservableList<PointTX> points = FXCollections.observableArrayList();

        // when
        // then
        assertThatThrownBy(() -> underTest.checkPoints(points))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("No series was generated.");
    }

    @Test
    void checkEnteredParams_correctParams_success() throws Exception {
        // given
        String tLeftText = "test";
        String tRightText = "test";
        String stepText = "test";
        String initialConditionText = "test";

        // when
        underTest.checkEnteredParams(tLeftText, tRightText, stepText, initialConditionText);

        // then
        assertThat(underTest.getExceptionMessage()).isNull();
    }

    @ParameterizedTest
    @CsvSource({
            "'', test, test, test",
            "test, '', test, test",
            "test, test, '', test",
            "test, test, test, ''"
    })
    void checkEnteredParams_emptyParams_throwsException(
            String tLeftText, String tRightText, String stepText, String initialConditionText
    ) {
        // when
        // then
        assertThatThrownBy(() -> underTest.checkEnteredParams(tLeftText, tRightText, stepText, initialConditionText))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Not all params have been entered.");

    }

}