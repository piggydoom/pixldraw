module com.piggydoom {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.base;
    requires transitive javafx.graphics;
    requires javafx.swing;
    requires transitive java.desktop;
    opens com.piggydoom to javafx.fxml;
    exports com.piggydoom;
}

