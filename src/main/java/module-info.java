module com.blueblazes13.carzzz {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.blueblazes13.carzzz to javafx.fxml;
    exports com.blueblazes13.carzzz;
    requires com.google.gson;
    opens com.blueblazes13.carzzz.model to com.google.gson;
    requires java.desktop;
}
