
package com.mycompany.antreanyuk;

import java.io.IOException;
import javafx.fxml.FXML;

public class HomeController {

    @FXML
    private void bukaPasien() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void bukaAdmin() throws IOException {
        App.setRoot("admin");
    }
}
