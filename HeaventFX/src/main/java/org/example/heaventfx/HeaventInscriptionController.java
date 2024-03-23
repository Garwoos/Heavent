package org.example.heaventfx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
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
        private TextField password;

        @FXML
        private TextField username;

        @FXML
        void goCon(MouseEvent event)throws IOException {
            Stage stage = (Stage) accON.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HeaventApplication.class.getResource("HeaventConnexion.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("Connexion");
            stage.setScene(scene);
            stage.show();

        }
        @FXML
        public void initialize() {
            Quitter.setOnAction(event -> {
                Platform.exit();
            });
        }

    }


