package pl.kacperk.save;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.kacperk.exception.ExceptionHandler;
import pl.kacperk.table.PointTX;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SaveHandlerTest {

    private SaveHandler underTest;

    @BeforeEach
    void beforeEach() throws IOException {
        File directory = new File("testdump");
        FileUtils.cleanDirectory(directory);
        ExceptionHandler exceptionHandler = new ExceptionHandler();
        underTest = new SaveHandler(exceptionHandler);
    }

    @Test
    void saveTXPoints_creatingNewFile_pointsSaved() throws Exception {
        //given
        String fileName = "testdump\\series.txt";
        File dirPath = new File("testdump");
        ObservableList<PointTX> points = FXCollections.observableArrayList();
        points.add(new PointTX("1", "1"));

        //when
        underTest.saveTXPoints(fileName, points);

        //then
        assertThat(dirPath.list()).isNotEmpty();
        assertThat(points.get(0).getTime() + "\t" + points.get(0).getX())
                .isEqualTo(Files.readAllLines(Path.of(fileName)).get(0));
    }

    @Test
    void saveTXPoints_overwritingExistingFile_pointsSaved() throws Exception {
        //given
        String fileName = "testdump\\series.txt";
        File dirPath = new File("testdump");
        ObservableList<PointTX> points = FXCollections.observableArrayList();
        points.add(new PointTX("1", "1"));

        //when
        FileUtils.writeStringToFile(new File(fileName), "sample", StandardCharsets.UTF_8);
        underTest.saveTXPoints(fileName, points);

        //then
        assertThat(dirPath.list()).isNotEmpty();
        assertThat(points.get(0).getTime() + "\t" + points.get(0).getX())
                .isEqualTo(Files.readAllLines(Path.of(fileName)).get(0));
    }

    @Test
    void saveTXPoints_notExistingFilePath_throwsException() {
        //given
        String fileName = "13f1eab08c2ea85ecd598f255d67bbf7bccbd09611de73bd8150181afacfaf02:\\testdump\\series.txt";
        ObservableList<PointTX> points = FXCollections.observableArrayList();
        points.add(new PointTX("1", "1"));

        // when
        // then
        assertThatThrownBy(() -> underTest.saveTXPoints(fileName, points))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Invalid file path.");
    }
}
