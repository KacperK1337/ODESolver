module pl.kacperk.odesolver {
    requires javafx.controls;
    requires javafx.fxml;

    requires MathParser.org.mXparser;
    requires org.apache.commons.io;

    opens pl.kacperk to javafx.fxml;
    exports pl.kacperk.save;
    opens pl.kacperk.save to javafx.fxml;
    exports pl.kacperk.table;
    opens pl.kacperk.table to javafx.fxml;
    exports pl.kacperk.math;
    opens pl.kacperk.math to javafx.fxml;
    exports pl.kacperk;
    exports pl.kacperk.exception;
    opens pl.kacperk.exception to javafx.fxml;
}