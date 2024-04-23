package org.example.heaventfx;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.time.Instant;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import java.util.stream.Collectors;

public class HeaventGestionEventController {

    private String utilisateurActuel;

    private String selectedEventId;

    @FXML
    private Button deconnexion;


    @FXML
    private Button ModifierEvent;

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
    private ComboBox<String> choixEvent;

    @FXML
    private Pane ModifGestion;

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
    private Button creatEvent;

    @FXML
    private TextArea descriptionEventField;

    @FXML
    private TextField siegeDispoField;

    private Stage stage;

    private List<Event> userEvents;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUtilisateurActuel(String utilisateurActuel) {
        this.utilisateurActuel = utilisateurActuel;
    }



    @FXML
    public void creaEvent(MouseEvent event) {
        try {
            Stage stage = (Stage) creatEvent.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HeaventApplication.class.getResource("HeaventCreation.fxml"));
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
                .thenAccept(newEvents -> {
                    Platform.runLater(() -> {
                        events = newEvents; // Mettre à jour la liste 'events'
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
        List<Event> events = new ArrayList<>();
        try {
            // Si la réponse commence par "[", c'est une liste d'événements
            if (responseBody.startsWith("[")) {
                events = Arrays.asList(mapper.readValue(responseBody, Event[].class));
            }
            // Si la réponse contient "status", c'est une erreur
            else if (responseBody.contains("\"status\"")) {
                System.out.println("Erreur lors de la récupération des événements : " + responseBody);
            }
            // Sinon, c'est une réponse inattendue
            else {
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
    void myEvent () {
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

    @FXML
    void modifEvent(MouseEvent event) {
        String selectedEventName = choixEvent.getSelectionModel().getSelectedItem();
        if (selectedEventName != null) {
            Event selectedEvent = userEvents.stream()
                    .filter(e -> e.getName().equals(selectedEventName))
                    .findFirst()
                    .orElse(null);
            if (selectedEvent != null) {
                // Mettre à jour l'objet selectedEvent avec les nouvelles valeurs des champs de formulaire
                selectedEvent.setName(nomProjetField.getText());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = dateEventField.getValue();
                Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                selectedEvent.setDate(date);
                selectedEvent.setDescription(descriptionEventField.getText());
                selectedEvent.setPlaces(Integer.parseInt(siegeDispoField.getText()));
                selectedEvent.setLocation(typeEventComboBox.getValue());

                // Enregistrer les modifications
                saveChangesToEvent(selectedEvent);
            }
        }
    }

    private void loadUserEvents() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8443/eventsheavent/getalleventfromuser/" + utilisateurActuel))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::parseEvents)
                .thenAccept(events -> {
                    userEvents = events; // Stocker les événements dans une variable de classe
                    choixEvent.getItems().clear();
                    ObservableList<String> eventNames = FXCollections.observableArrayList(events.stream().map(Event::getName).collect(Collectors.toList()));
                    choixEvent.getItems().addAll(eventNames); // Ajouter uniquement les noms des événements à la ComboBox
                })
                .exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });


    }

    private void createNotificationForEvent(Event event) {
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();

        // pour crée une nouvelle notification
        Notification notification = new Notification();
        notification.setEvent(event);
        notification.setNotificationType("Modification");
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



    private void loadEventIntoForm(Event event) {
        nomProjetField.setText(event.getName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = event.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        dateEventField.setValue(localDate);
        descriptionEventField.setText(event.getDescription());
        siegeDispoField.setText(String.valueOf(event.getPlaces()));
        typeEventComboBox.setValue(event.getLocation());
        ModifGestion.setVisible(true);
    }

    private void saveChangesToEvent(Event event) {
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String eventAsJson = objectMapper.writeValueAsString(event);
            System.out.println("Event as JSON: " + eventAsJson); // Imprimer l'événement en tant que JSON
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8443/eventsheavent/update/" + event.getId())) // Utiliser l'ID de l'événement ici
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(eventAsJson))
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(response -> {
                        System.out.println("Response from server: " + response);
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Confirmation");
                            alert.setHeaderText(null);
                            alert.setContentText("L'événement a été modifié avec succès.");
                            alert.showAndWait();
                        });
                    });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        createNotificationForEvent(event);
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

        this.events = new ArrayList<>();

        searchResults.setVisible(false);

        barreRecherche.setOnMouseClicked(event -> {
            searchResults.setVisible(true);
            searchResults.toFront();
            event.consume();
        });

        searchResults.setOnMouseClicked(event -> {
            searchResults.setVisible(false);
            event.consume();
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
            System.out.println("Mouse clicked on searchResults");
            String selectedEventName = searchResults.getSelectionModel().getSelectedItem();
            System.out.println("Selected event name: " + selectedEventName); // Ajout d'une instruction d'impression
            if (selectedEventName != null) {
                System.out.println("Events: " + events); // Ajout d'une instruction d'impression pour afficher tous les événements
                Event selectedEvent = events.stream()
                        .filter(e -> {
                            System.out.println("Event name: " + e.getName()); // Ajout d'une instruction d'impression pour afficher le nom de chaque événement
                            return e.getName().equals(selectedEventName);
                        })
                        .findFirst()
                        .orElse(null);
                System.out.println("Selected event: " + selectedEvent); // Ajout d'une instruction d'impression
                if (selectedEvent != null) {
                    searchResults.setVisible(false);
                    openEventPage(selectedEvent);
                }
            }
        });

        typeEventComboBox.getItems().addAll("Concert", "Conference", "Exposition", "Mariage", "Autre");


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

        setUtilisateurActuel(UserSession.getInstance().getMailUser());

        loadUserEvents();

        ModifGestion.setVisible(false);

        choixEvent.setOnAction((actionEvent) -> {
            String selectedEventName = choixEvent.getSelectionModel().getSelectedItem();
            if (selectedEventName != null) {
                Event selectedEvent = userEvents.stream()
                        .filter(e -> e.getName().equals(selectedEventName))
                        .findFirst()
                        .orElse(null);
                if (selectedEvent != null) {
                    loadEventIntoForm(selectedEvent);
                }
            }
        });

    }
}