module org.example.heaventfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires java.net.http;


    opens org.example.heaventfx to javafx.fxml;
    exports org.example.heaventfx;
}