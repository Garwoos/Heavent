module org.example.heaventfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.datatype.jsr310;


    opens org.example.heaventfx to javafx.fxml, com.fasterxml.jackson.databind;

    exports org.example.heaventfx;
}