package pl.kacperk.math;

import org.mariuszgromada.math.mxparser.Function;
import pl.kacperk.exception.ExceptionHandler;

//Builds function (equation) f(t, x) from String.
public class EquationHandler {
    private final ExceptionHandler exceptionHandler;

    public EquationHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public Function getEquation(String stringEquation) throws Exception {
        Function equation = new Function("f(t, x) = " + stringEquation);
        checkForExceptions(stringEquation, equation);
        return equation;
    }

    private void checkForExceptions(String stringEquation, Function equation) throws Exception {
        String exceptionMessage;
        if (stringEquation.equals("")) {
            exceptionMessage = "equation syntax was not entered";
            exceptionHandler.setExceptionMessage(exceptionMessage);
            exceptionHandler.throwNewException();
        }
        if (!equation.checkSyntax()) {
            exceptionMessage = "invalid equation syntax";
            exceptionHandler.setExceptionMessage(exceptionMessage);
            exceptionHandler.throwNewException();
        }
    }
}
