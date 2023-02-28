package pl.kacperk.exception;

import javafx.collections.ObservableList;
import org.mariuszgromada.math.mxparser.Function;
import pl.kacperk.table.PointTX;

public class ExceptionHandler {

    private String exceptionMessage;

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void throwNewException(String exceptionMessage) throws Exception {
        this.exceptionMessage = exceptionMessage;
        throw new Exception(exceptionMessage);
    }

    public void checkStringEquation(String stringEquation) throws Exception {
        if (stringEquation.equals("")) {
            throwNewException("Equation syntax was not entered.");
        }
    }

    public void checkEquation(Function equation) throws Exception {
        if (!equation.checkSyntax()) {
            throwNewException("Invalid equation syntax.");
        }
    }

    public void checkStepAndCompartment(double h, double a, double b) throws Exception {
        if (h <= 0) {
            throwNewException("Step h is smaller or equal 0.");
        }
        if (b <= a) {
            throwNewException("Compartment end (b) is less or equal beginning (a).");
        }
        if (h >= (b - a)) {
            throwNewException("Step h is greater or equal compartment length.");
        }
    }

    public void checkFileName(String fileName) throws Exception {
        if (fileName.equals("")) {
            throwNewException("File name was not entered.");
        }
        if (!fileName.endsWith(".txt")) {
            throwNewException("Entered file is not a text file.");
        }
    }

    public void checkPoints(ObservableList<PointTX> points) throws Exception {
        if (points.isEmpty()) {
            throwNewException("No series was generated.");
        }
    }

    public void checkEnteredParams(String tLeftText,
                                   String tRightText,
                                   String stepText,
                                   String initialConditionText) throws Exception {
        if (tLeftText.isEmpty() || tRightText.isEmpty() || stepText.isEmpty() || initialConditionText.isEmpty()) {
            throwNewException("Not all params have been entered.");
        }
    }
}
