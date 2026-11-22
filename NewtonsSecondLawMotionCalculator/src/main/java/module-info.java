module com.mycompany.newtonssecondlawmotioncalculator {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.joml;
    requires static lombok;
    requires java.base;

    opens com.mycompany.newtonssecondlawmotioncalculator to javafx.fxml;
    exports com.mycompany.newtonssecondlawmotioncalculator;
}
