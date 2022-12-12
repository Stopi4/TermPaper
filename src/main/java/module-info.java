module com.University.TempPaper {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.University.TempPaper to javafx.fxml;
    exports com.University.TempPaper;
    exports com.University.TempPaper.Controllers;
    opens com.University.TempPaper.Controllers to javafx.fxml;
}