package pl.kacperk.save;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.kacperk.exception.ExceptionHandler;
import pl.kacperk.table.PointTX;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveHandlerTest {
    private SaveHandler saveHandler;
    private ExceptionHandler exceptionHandler;
    private String fileName;
    ObservableList<PointTX> points;

    @BeforeAll
    public static void beforeAll() throws IOException {
        File directory = new File("testdump");
        FileUtils.cleanDirectory(directory);
    }

    @BeforeEach
    public void beforeEach() {
        exceptionHandler = new ExceptionHandler();
        saveHandler = new SaveHandler(exceptionHandler);
    }

    @Test
    public void saveTXPoints_overwritingExistingFile_pointsSaved() throws Exception {
        //given
        fileName = "testdump\\series.txt";
        File dirPath = new File("testdump");
        PointTX pointTX = new PointTX("1", "1");
        points = FXCollections.observableArrayList(pointTX);

        //when
        FileUtils.writeStringToFile(new File(fileName), "sample", StandardCharsets.UTF_8);
        saveHandler.saveTXPoints(fileName, points);

        //then
        org.assertj.core.api.Assertions.assertThat(dirPath.list()).isNotEmpty();
        Assertions.assertEquals(pointTX.getTime() + "\t" + pointTX.getX(),
                Files.readAllLines(Path.of(fileName)).get(0));

        org.assertj.core.api.Assertions.assertThat(exceptionHandler.getExceptionMessage()).isNull();
    }

    @Test
    public void saveTXPoints_creatingNewFile_pointsSaved() throws Exception {
        //given
        fileName = "testdump\\series.txt";
        File dirPath = new File("testdump");
        PointTX pointTX = new PointTX("1", "1");
        points = FXCollections.observableArrayList(pointTX);

        //when
        saveHandler.saveTXPoints(fileName, points);

        //then
        org.assertj.core.api.Assertions.assertThat(dirPath.list()).isNotEmpty();
        Assertions.assertEquals(pointTX.getTime() + "\t" + pointTX.getX(),
                Files.readAllLines(Path.of(fileName)).get(0));

        org.assertj.core.api.Assertions.assertThat(exceptionHandler.getExceptionMessage()).isNull();
    }

    @Test
    public void saveTXPoints_emptyFileName_throwsException() {
        //given
        fileName = "";
        PointTX pointTX = new PointTX("1", "1");
        points = FXCollections.observableArrayList(pointTX);

        //when
        Throwable throwable =
                org.assertj.core.api.Assertions.catchThrowable(() -> saveHandler.saveTXPoints(fileName, points));

        //then
        org.assertj.core.api.Assertions.assertThat(throwable)
                .isInstanceOf(Exception.class)
                .hasMessage(exceptionHandler.getExceptionMessage());
    }

    @Test
    public void saveTXPoints_notATextFile_throwsException() {
        //given
        fileName = "testdump\\series.png";
        PointTX pointTX = new PointTX("1", "1");
        points = FXCollections.observableArrayList(pointTX);

        //when
        Throwable throwable =
                org.assertj.core.api.Assertions.catchThrowable(() -> saveHandler.saveTXPoints(fileName, points));

        //then
        org.assertj.core.api.Assertions.assertThat(throwable)
                .isInstanceOf(Exception.class)
                .hasMessage(exceptionHandler.getExceptionMessage());
    }

    @Test
    public void saveTXPoints_emptyObservableList_throwsException() {
        //given
        fileName = "testdump\\series.txt";
        points = FXCollections.observableArrayList();

        //when
        Throwable throwable =
                org.assertj.core.api.Assertions.catchThrowable(() -> saveHandler.saveTXPoints(fileName, points));

        //then
        org.assertj.core.api.Assertions.assertThat(throwable)
                .isInstanceOf(Exception.class)
                .hasMessage(exceptionHandler.getExceptionMessage());
    }

    @Test
    public void saveTXPoints_notExistingFilePath_throwsIOException() {
        //given
        fileName = "Z:\\testdump\\series.txt";
        PointTX pointTX = new PointTX("1", "1");
        points = FXCollections.observableArrayList(pointTX);

        //when
        Throwable throwable =
                org.assertj.core.api.Assertions.catchThrowable(() -> saveHandler.saveTXPoints(fileName, points));

        //then
        org.assertj.core.api.Assertions.assertThat(throwable)
                .isInstanceOf(Exception.class)
                .hasMessage(exceptionHandler.getExceptionMessage());
    }
}
