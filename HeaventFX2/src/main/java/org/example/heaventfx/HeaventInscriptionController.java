package org.example.heaventfx;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

            try {
                registerUser(emailText, usernameText, passwordText, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void registerUser(String email, String username, String password, boolean isAdmin) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("src/main/resources/org/example/heaventfx/users.json");

        // Relecture de la liste des utilisateurs
        List<User> users;
        if (file.exists()) {
            users = mapper.readValue(file, new TypeReference<List<User>>() {});
        } else {
            users = new ArrayList<>();
        }

        // Ajout du nouvel utilisateur
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setIsAdmin(isAdmin);
        users.add(newUser);

        // ecriture de la liste des utilisateurs dans le fichier
        mapper.writeValue(file, users);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Bravo");
        alert.setHeaderText(null);
        alert.setContentText("Votre compte a été créé avec succès!");
        alert.showAndWait();
    }
}

