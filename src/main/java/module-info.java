module com.example.indiv {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.indiv to javafx.fxml;
    exports com.example.indiv;
}