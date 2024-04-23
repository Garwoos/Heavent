package org.example.heaventfx;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.control.Alert;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import javafx.scene.control.ListView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HeaventEventController {

    private Stage stage;

    @FXML
    private TextField EventDate;

    @FXML
    private Label EventName;

    @FXML
    private TextField EventSiege;

    @FXML
    private ImageView EventType;

    @FXML
    private ImageView cloche;

    @FXML
    private Button GoEvent;

    @FXML
    private TextField barreRecherche;

    @FXML
    private ListView<String> searchResults;
    private List<Event> events;

    @FXML
    private Button creatEvent;

    @FXML
    private Button deconnexion;

    @FXML
    private Label usernamelabel;

    @FXML
    private Button Accueil;

    @FXML
    private Button Reserv;

    @FXML
    private Button GestionEvent;

    @FXML
    private TextField descriptionEventField;

    @FXML
    private Button ResEvent;

    private Event currentEvent;


    @FXML
    private Button menu;

    @FXML
    private Button menuClose;

    @FXML
    private Button recherche;

    @FXML
    private VBox slider;



    public void initData(Event event) {
        currentEvent = event;
        EventName.setText(event.getName());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        EventDate.setText(formatter.format(event.getDate()));
        EventDate.setEditable(false);
        EventDate.setDisable(true);
        EventSiege.setText(String.valueOf(event.getPlaces()));
        EventSiege.setEditable(false);
        EventSiege.setDisable(true);
        descriptionEventField.setText(event.getDescription());
        descriptionEventField.setEditable(false);
        descriptionEventField.setDisable(true);

        String imagePath = "/org/example/heaventfx/image/Type d'évènement/";
        switch (event.getLocation()) {
            case "Concert":
                imagePath += "concert.jpg";
                break;
            case "Conference":
                imagePath += "conference.jpg";
                break;
            case "Exposition":
                imagePath += "exposition.jpg";
                break;
            case "Mariage":
                imagePath += "mariage.jpg";
                break;
            case "Autre":
                imagePath += "roger.jpg";
                break;
        }

        Image image = new Image(getClass().getResource(imagePath).toExternalForm());
        EventType.setImage(image);
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
    void ReservEvent() {
        String email = UserSession.getInstance().getMailUser();
        int eventId = currentEvent.getId();
        long eventIdLong = (long) eventId;

        if (currentEvent.getPlaces() <= 0) {
            // Créez une nouvelle alerte
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Inscription impossible");
            alert.setHeaderText(null);
            alert.setContentText("Il n'y a plus de places disponibles pour cet événement.");

            // Affichez l'alerte et attendez que l'utilisateur la ferme
            alert.showAndWait();
            return;
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8443/inscriptionheavent/create?userEmail=" + email + "&eventId=" + eventIdLong))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(response -> {
                    System.out.println("Response: " + response);
                    Platform.runLater(() -> {
                        // Update the number of available seats
                        int currentSeats = currentEvent.getPlaces();
                        if (currentSeats > 0) {
                            currentEvent.setPlaces(currentSeats - 1);
                        } else {
                            System.out.println("No seats available for this event.");
                        }
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Inscription réussie");
                        alert.setHeaderText(null);
                        alert.setContentText("Votre inscription a été prise en compte.");
                        alert.showAndWait();

                        initData(currentEvent);
                    });
                })
                .exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });
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
    void creaEvent(MouseEvent event) {
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

        String username = UserSession.getInstance().getUsername();
        usernamelabel.setText(username);

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

        EventName.setAlignment(Pos.CENTER);
        EventName.widthProperty().addListener((obs, oldVal, newVal) -> {
            EventName.setAlignment(Pos.CENTER);
        });
    }
}

