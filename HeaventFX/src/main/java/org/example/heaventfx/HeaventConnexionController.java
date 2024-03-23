package org.example.heaventfx;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.IOException;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.io.FileNotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;

public class HeaventConnexionController {

    @FXML
    private Button connexion;

    @FXML
    private Button Quitter;

    @FXML
    private Hyperlink noIns;

    @FXML
    private TextField password;

    @FXML
    private TextField username;

    @FXML
    public void connec(MouseEvent event)throws IOException {
        String usernameInput = username.getText();
        String passwordInput = password.getText();

        InputStream is = getClass().getResourceAsStream("/org/example/heaventfx/users.json");
        if (is == null) {
            throw new FileNotFoundException("Cannot find file: users.json");
        }
        String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        JSONArray jsonArray = new JSONArray(content);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            String storedUsername = jsonObject.getString("username");
            String storedPassword = jsonObject.getString("password");

            if (usernameInput.equals(storedUsername) && passwordInput.equals(storedPassword)) {
                // User is authenticated, proceed with loading the new scene
                Stage stage = (Stage) connexion.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(HeaventApplication.class.getResource("Heavent.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 850, 540);
                stage.setTitle("Heavent");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                return;
            }

        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur d'authentification");
        alert.setHeaderText(null);
        alert.setContentText("Nom d'utilisateur ou mot de passe incorrect");

        alert.showAndWait();
    }

    @FXML
    void noAcc(MouseEvent event)throws IOException {
        Stage stage = (Stage) noIns.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HeaventApplication.class.getResource("HeaventInscription.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Inscription");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }
    @FXML
    public void initialize() {
        Quitter.setOnAction(event -> {
            Platform.exit();
        });
    }
}