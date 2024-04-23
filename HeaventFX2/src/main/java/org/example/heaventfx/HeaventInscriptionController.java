package org.example.heaventfx;

import javafx.application.Platform;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.IOException;

public class HeaventInscriptionController {

    @FXML
    private Hyperlink accON;

    @FXML
    private Button Quitter;

    @FXML
    private Button incription;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    void goCon(MouseEvent event) throws IOException {
        Stage stage = (Stage) accON.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HeaventApplication.class.getResource("HeaventConnexion.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Connexion");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {
        Quitter.setOnAction(event -> Platform.exit());

        incription.setOnAction(event -> {
            String emailText = email.getText();
            String usernameText = username.getText();
            String passwordText = password.getText();
            passwordText = String.valueOf(passwordText.hashCode());

            try {
                registerUser(emailText, usernameText, passwordText, false);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private void registerUser(String email, String username, String password, boolean isAdmin) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        String json = new StringBuilder()
                .append("{")
                .append("\"email\":\"").append(email).append("\",")
                .append("\"username\":\"").append(username).append("\",")
                .append("\"password\":\"").append(password).append("\",")
                .append("\"isAdmin\":").append(isAdmin)
                .append("}").toString();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8443/usersheavent/create"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Bravo");
            alert.setHeaderText(null);
            alert.setContentText("Votre compte a été créé avec succès!");
            alert.showAndWait();
        } else {
            // Gérer l'erreur
        }
    }
}

