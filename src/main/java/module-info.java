module com.mycompany.antreanyuk {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.antreanyuk to javafx.fxml;
    exports com.mycompany.antreanyuk;
}
