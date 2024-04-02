package org.example.heaventfx;

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
import javafx.scene.control.PasswordField;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.List;

public class HeaventConnexionController {

    @FXML
    private Button connexion;

    private List<User> users;

    @FXML
    private Button Quitter;

    @FXML
    private Hyperlink noIns;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    public void connec(MouseEvent event)throws IOException {
        String usernameInput = username.getText();
        String passwordInput = password.getText();

        for (User user : users) {
            String storedUsername = user.getUsername();
            String storedPassword = user.getPassword();

            if (usernameInput.equals(storedUsername) && passwordInput.equals(storedPassword)) {
                // User is authenticated, proceed with loading the new scene
                UserSession.getInstance().setUsername(storedUsername);
                Stage stage = (Stage) connexion.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(HeaventApplication.class.getResource("Heavent.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 850, 540);
                stage.setTitle("Heavent");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.centerOnScreen();
                stage.show();
                HeaventController heaventController = fxmlLoader.getController();
                UserSession.getInstance().setUser(user);
                heaventController.setUsername(storedUsername);

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
        stage.centerOnScreen();
        stage.show();

    }
    @FXML
    public void initialize() {
        Quitter.setOnAction(event -> {
            Platform.exit();
        });
        rechargerJson();
    }

    private void rechargerJson() {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("src/main/resources/org/example/heaventfx/users.json");
        try {
            users = mapper.readValue(file, new TypeReference<List<User>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}