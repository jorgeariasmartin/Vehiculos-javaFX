module controllers.vehiculos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.sql;
    requires java.desktop;


    opens controllers.vehiculos to javafx.fxml;
    exports controllers.vehiculos;
}