package org.example.heaventfx;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.time.ZonedDateTime;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

public class HeaventCreationController {

    private String mailUser;

    @FXML
    private Button deconnexion;


    @FXML
    private Button creeEvent;

    @FXML
    private Label usernamelabel;

    @FXML
    private ImageView cloche;

    @FXML
    private ComboBox<String> typeEventComboBox;

    @FXML
    private Button menu;

    @FXML
    private Button menuClose;

    @FXML
    private VBox slider;

    @FXML
    private TextField nomProjetField;

    @FXML
    private DatePicker dateEventField;

    @FXML
    private Button recherche;

    @FXML
    private TextField barreRecherche;

    @FXML
    private ListView<String> searchResults;

    private List<Event> events;

    @FXML
    private Button Accueil;

    @FXML
    private Button Reserv;

    @FXML
    private Button GestionEvent;

    @FXML
    private TextArea descriptionEventField;

    @FXML
    private TextField siegeDispoField;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUtilisateurActuel(String utilisateurActuel) {
        this.mailUser = utilisateurActuel;
    }

    @FXML
    void ReservEvent () {
        try {
            Stage stage = (Stage) Reserv.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HeaventApplication.class.getResource("HeaventUser.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 850, 540);
            stage.setTitle("Heavent");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void EventGestion(MouseEvent event) {
        try {
            Stage stage = (Stage) GestionEvent.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HeaventApplication.class.getResource("HeaventGestionEvent.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 850, 540);
            stage.setTitle("Heavent");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void notif(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HeaventApplication.class.getResource("HeaventNotification.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 475, 450);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle("Notifications");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void search() {
        String query = barreRecherche.getText();
        String url = "http://localhost:8443/eventsheavent/search?query=" + query;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::parseEvents)
                .thenAccept(events -> {
                    Platform.runLater(() -> {
                        ObservableList<String> eventNames = FXCollections.observableArrayList(events.stream().map(Event::getName).collect(Collectors.toList()));
                        searchResults.getItems().clear();
                        searchResults.getItems().addAll(eventNames);
                    });
                })
                .exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });
    }

    private List<Event> parseEvents(String responseBody) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (responseBody.startsWith("[")) {
                events = Arrays.asList(mapper.readValue(responseBody, Event[].class));
            } else {
                System.out.println("Unexpected response: " + responseBody);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return events;
    }



    private void openEventPage(Event selectedEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(HeaventApplication.class.getResource("HeaventEvent.fxml"));
            Scene scene = new Scene(loader.load(), 850, 540);
            HeaventEventController controller = loader.getController();
            controller.initData(selectedEvent);

            Stage currentStage = (Stage) recherche.getScene().getWindow();
            currentStage.setTitle("Heavent");
            currentStage.setScene(scene);
            currentStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void welcome(MouseEvent event) {
        openAccueil();
    }

    private void openAccueil() {
        try {
            FXMLLoader loader = new FXMLLoader(HeaventApplication.class.getResource("Heavent.fxml"));
            Scene scene = new Scene(loader.load(), 850, 540);
            HeaventController controller = loader.getController();
            controller.setStage(stage);

            Stage currentStage = (Stage) Accueil.getScene().getWindow();
            currentStage.setTitle("Heavent");
            currentStage.setScene(scene);
            currentStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void dc(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HeaventApplication.class.getResource("HeaventConnexion.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Inscription");
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    private void createNotificationForEvent(Event event) {
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();

        // pour crée une nouvelle notification
        Notification notification = new Notification();
        notification.setEvent(event);
        notification.setNotificationType("Creation");
        notification.setDate(Date.from(Instant.now()));

        try {
            String notificationAsJson = objectMapper.writeValueAsString(notification);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8443/notificationsheavent/create")) // Remplacez par l'URL appropriée
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(notificationAsJson))
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(response -> {
                        System.out.println("Response from server: " + response);
                    });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void crEvent(MouseEvent event) {
        String location = typeEventComboBox.getValue();
        String name = nomProjetField.getText();
        ZonedDateTime zonedDateTime = dateEventField.getValue().atStartOfDay(ZoneId.systemDefault());
        String formattedDate = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(zonedDateTime);

        String description = descriptionEventField.getText();
        int places = Integer.parseInt(siegeDispoField.getText());
        double prix = 15.50;

        // Créer une instance de HttpClient
        HttpClient client = HttpClient.newHttpClient();

        String json = "{\"name\":\"" + name + "\",\"description\":\"" + description + "\",\"date\":\"" + formattedDate + "\",\"location\":\"" + location + "\",\"places\":\"" + places + "\",\"User\":\"" + mailUser + "\",\"prix\":\"" + prix + "\"}";

        System.out.println("JSON to be sent: " + json);
        // Créer une instance de HttpRequest
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8443/eventsheavent/create"))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();

        // Envoyer la requête et récupérer la réponse
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // réponse de l'API
            System.out.println("Response from API: " + response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return;
        }

        // Vérifier le statut de la réponse
        if (response.statusCode() == 200) {

            // L'événement a été créé avec succès

            try {
                ObjectMapper mapper = new ObjectMapper();
                Event createdEvent = mapper.readValue(response.body(), Event.class);
                createNotificationForEvent(createdEvent);
                // L'événement a été créé avec succès
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Création d'événement");
            alert.setHeaderText(null);
            alert.setContentText("L'événement a été créé avec succès.");

            alert.showAndWait();
        } else {
            // Quelque chose s'est mal passé
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de création d'événement");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur s'est produite lors de la création de l'événement.");

            alert.showAndWait();
        }
    }

    public void setUsername(String username) {
        usernamelabel.setText(username);
    }


    @FXML
    public void initialize() {

        User user = UserSession.getInstance().getUser();
        if (user != null) {
            setUsername(user.getUsername());
        }

        searchResults.setVisible(false);

        barreRecherche.setOnMouseClicked(event -> {
            searchResults.setVisible(true);
            searchResults.toFront();
            event.consume(); // consomme l'événement pour qu'il ne soit pas propagé à la scène
        });

        searchResults.setOnMouseClicked(event -> {
            searchResults.setVisible(false);
            event.consume(); // consomme l'événement pour qu'il ne soit pas propagé à la scène
        });

        Platform.runLater(() -> {
            barreRecherche.getScene().addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                searchResults.setVisible(false);
            });
        });

        barreRecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            search();
        });

        searchResults.setOnMouseClicked(event -> {
            String selectedEventName = searchResults.getSelectionModel().getSelectedItem();
            if (selectedEventName != null) {
                Event selectedEvent = events.stream()
                        .filter(e -> e.getName().equals(selectedEventName))
                        .findFirst()
                        .orElse(null);
                if (selectedEvent != null) {
                    searchResults.setVisible(false);
                    openEventPage(selectedEvent);
                }
            }
        });

        typeEventComboBox.getItems().addAll("Concert", "Conference", "Exposition", "Mariage", "Autre");
        String mailUser = UserSession.getInstance().getMailUser();
        setUtilisateurActuel(mailUser);


        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.5));
        slide.setNode(slider);

        slider.setTranslateX(200);
        menu.setVisible(true);
        menuClose.setVisible(false);
        menu.setOnMouseClicked(event -> {
            slide.setToX(0);
            slide.play();
            slide.setOnFinished((ActionEvent e) -> {
                menu.setVisible(false);
                menuClose.setVisible(true);
            });
        });

        menuClose.setOnMouseClicked(event -> {
            slide.setToX(200);
            slide.play();
            slide.setOnFinished((ActionEvent e) -> {
                menu.setVisible(true);
                menuClose.setVisible(false);
            });
        });

    }
}


