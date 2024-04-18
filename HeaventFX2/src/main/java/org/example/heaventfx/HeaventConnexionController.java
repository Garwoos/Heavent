package org.example.heaventfx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.IOException;
import javafx.scene.control.PasswordField;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.List;

public class HeaventConnexionController {

    @FXML
    private Button connexion;



    @FXML
    private Button Quitter;

    @FXML
    private Hyperlink noIns;

    @FXML
    private PasswordField password;

    @FXML
    private TextField userMail;

    public void connec(MouseEvent event) throws IOException {
        String userMailInput = userMail.getText();
        String passwordInput = password.getText();
        passwordInput = String.valueOf(passwordInput.hashCode());

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8443/usersheavent/read/" + userMailInput))
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            // Handle the exception
            e.printStackTrace();
            return;
        }

        // Vérifiez le statut de la réponse et traitez les données de la réponse
        if (response.statusCode() == 200) {
            // Parsez la réponse en JSON et vérifiez le mot de passe
            ObjectMapper mapper = new ObjectMapper();
            List<User> users = mapper.readValue(response.body(), new TypeReference<List<User>>() {});
            if (!users.isEmpty()) {
                User user = users.get(0);

                if (user.getPassword().equals(passwordInput)) {
                    // L'utilisateur est authentifié, procédez au chargement de la nouvelle scène
                    UserSession.getInstance().setMailUser(userMailInput);
                    UserSession.getInstance().setUser(user);
                    Stage stage = (Stage) connexion.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(HeaventApplication.class.getResource("Heavent.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 850, 540);
                    stage.setTitle("Heavent");
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.centerOnScreen();
                    stage.show();
                    UserSession.getInstance().setUser(user);

                    return;
                }
            }
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur d'authentification");
        alert.setHeaderText(null);
        alert.setContentText("Nom d'utilisateur ou mot de passe incorrect");

        alert.showAndWait();
    }

    @FXML
    void noAcc(MouseEvent event) throws IOException {
        Stage stage = (Stage) noIns.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HeaventApplication.class.getResource("HeaventInscription.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Inscription");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();

    }

    @FXML
    public void initialize() {
        Quitter.setOnAction(event -> {
            Platform.exit();
        });
    }
}