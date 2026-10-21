module com.mycompany.newtonssecondlawmotioncalculator {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.newtonssecondlawmotioncalculator to javafx.fxml;
    exports com.mycompany.newtonssecondlawmotioncalculator;
}
