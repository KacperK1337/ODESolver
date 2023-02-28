package pl.kacperk.save;

import javafx.collections.ObservableList;
import org.apache.commons.io.FileUtils;
import pl.kacperk.exception.ExceptionHandler;
import pl.kacperk.table.PointTX;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class SaveHandler {

    private final ExceptionHandler exceptionHandler;

    public SaveHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public void saveTXPoints(String fileName, ObservableList<PointTX> points) throws Exception {
        exceptionHandler.checkFileName(fileName);
        exceptionHandler.checkPoints(points);
        Collection<String> linesCollection = new ArrayList<>();
        for (PointTX point : points) {
            linesCollection.add(point.getTime() + "\t" + point.getX());
        }
        try {
            FileUtils.writeLines(
                    new File(fileName), linesCollection
            );
        } catch (IOException ioException) {
            exceptionHandler.throwNewException("Invalid file path.");
        }
    }

}
