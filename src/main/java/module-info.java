module com.app.progettoispw202324 {
    requires javafx.controls;
    requires javafx.fxml;
    requires log4j.api;
    requires java.sql;


    opens com.app.progettoispw202324 to javafx.fxml;
    exports com.app.progettoispw202324;
    exports com.app.progettoispw202324.util;
    opens com.app.progettoispw202324.util to javafx.fxml;
    exports com.app.progettoispw202324.allertBox;
    opens com.app.progettoispw202324.allertBox to javafx.fxml;
}