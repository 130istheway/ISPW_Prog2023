module com.app.progettoispw202324 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.app.progettoispw202324 to javafx.fxml;
    exports com.app.progettoispw202324;
}