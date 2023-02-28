package pl.kacperk.math;

import org.mariuszgromada.math.mxparser.Function;
import pl.kacperk.exception.ExceptionHandler;

public class EquationHandler {

    private final ExceptionHandler exceptionHandler;

    public EquationHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public Function getEquation(String stringEquation) throws Exception {
        exceptionHandler.checkStringEquation(stringEquation);
        Function equation = new Function("f(t, x) = " + stringEquation);
        exceptionHandler.checkEquation(equation);
        return equation;
    }

}
