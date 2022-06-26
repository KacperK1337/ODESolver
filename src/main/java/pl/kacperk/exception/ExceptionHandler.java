package pl.kacperk.exception;

//Helps in handling exceptions
public class ExceptionHandler {
    private String exceptionMessage;

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public void throwNewException() throws Exception {
        throw new Exception(getExceptionMessage());
    }
}
