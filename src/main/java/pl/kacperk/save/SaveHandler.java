package pl.kacperk.save;

import javafx.collections.ObservableList;
import org.apache.commons.io.FileUtils;
import pl.kacperk.exception.ExceptionHandler;
import pl.kacperk.table.PointTX;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

//Handles saving operations
//Supports saving calculated t and x points to text file
public class SaveHandler {
    private final ExceptionHandler exceptionHandler;

    public SaveHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public void saveTXPoints(String fileName, ObservableList<PointTX> points) throws Exception {
        checkForExceptions(fileName, points);
        Collection<String> linesCollection = new ArrayList<>();
        for (PointTX point : points) {
            linesCollection.add(point.getTime() + "\t" + point.getX());
        }
        try {
            FileUtils.writeLines(new File(fileName), linesCollection);
        } catch (IOException ioException) {
            exceptionHandler.setExceptionMessage(ioException.getMessage());
            exceptionHandler.throwNewException();
        }
    }

    private void checkForExceptions(String fileName, ObservableList<PointTX> points) throws Exception {
        String exceptionMessage;
        if (fileName.equals("")) {
            exceptionMessage = "file name was not entered";
            exceptionHandler.setExceptionMessage(exceptionMessage);
            exceptionHandler.throwNewException();
        }
        if (!fileName.endsWith(".txt")) {
            exceptionMessage = "entered file is not a text file";
            exceptionHandler.setExceptionMessage(exceptionMessage);
            exceptionHandler.throwNewException();
        }
        if (points.isEmpty()) {
            exceptionMessage = "no series was generated";
            exceptionHandler.setExceptionMessage(exceptionMessage);
            exceptionHandler.throwNewException();
        }
    }
}
