package org.example.heaventfx;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import java.io.File;
import java.util.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;

import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.control.Label;
import java.util.stream.Collectors;

public class HeaventUserController{
    @FXML
    private Button deconnexion;

    @FXML
    private Button recherche;

    @FXML
    private ImageView cloche;

    @FXML
    private TextField barreRecherche;

    @FXML
    private ListView<String> searchResults;

    private List<Event> events;

    @FXML
    private Label usernamelabel;

    @FXML
    private Button Accueil;

    @FXML
    private ListView<Event> listViewEvent;

    @FXML
    private VBox eventContainer;

    @FXML
    private Button GestionEvent;

    @FXML
    private Button creatEvent;

    @FXML
    private Button menu;

    @FXML
    private Button menuClose;

    @FXML
    private VBox slider;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
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

    private boolean deleteInscription(long inscriptionId, Event event) throws IOException, InterruptedException {
        String deleteUrl = "http://localhost:8443/inscriptionheavent/delete/" + inscriptionId;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(URI.create(deleteUrl))
                .DELETE()
                .build();

        HttpResponse<String> deleteResponse = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());

        if (deleteResponse.statusCode() == 200) {
            event.setPlaces(event.getPlaces() + 1);
            String updateUrl = "http://localhost:8443/eventsheavent/update/" + event.getId();
            String json = new ObjectMapper().writeValueAsString(event);

            HttpRequest updateRequest = HttpRequest.newBuilder()
                    .uri(URI.create(updateUrl))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> updateResponse = client.send(updateRequest, HttpResponse.BodyHandlers.ofString());

            return updateResponse.statusCode() == 200;
        } else {
            return false;
        }
    }


    private List<InscriptionHeavent> getEventsForUser(String userEmail) {
        String url = "http://localhost:8443/inscriptionheavent/geteventsforuser/" + userEmail;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            return Arrays.asList(mapper.readValue(response.body(), InscriptionHeavent[].class));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private List<InscriptionHeavent> getEventsDetailsForUser(String userEmail) {
        return getEventsForUser(userEmail);
    }


    private void populateEventContainer(String userEmail) {
        eventContainer.getChildren().clear();
        Pane spacer = new Pane();
        spacer.setMinHeight(20);
        eventContainer.getChildren().add(spacer);
        List<InscriptionHeavent> inscriptionHeavents = getEventsDetailsForUser(userEmail);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        for (InscriptionHeavent inscriptionHeavent : inscriptionHeavents) {
            Event event = inscriptionHeavent.getEvent();
            long inscriptionId = inscriptionHeavent.getInscriptionId();
            HBox eventBox = new HBox();

            TextField nameField = new TextField(event.getName());
            nameField.setEditable(false);
            nameField.setStyle("-fx-background-color: #282B30; -fx-text-fill: white;");

            String formattedDate = formatter.format(event.getDate());
            TextField dateField = new TextField(formattedDate);
            dateField.setEditable(false);
            dateField.setStyle("-fx-background-color: #282B30; -fx-text-fill: white;");

            Button cancelButton = new Button("Annuler");
            cancelButton.getStyleClass().add("form-button");
            cancelButton.setStyle("-fx-background-color: #282B30; -fx-text-fill: white;");
            cancelButton.setCursor(Cursor.HAND);
            cancelButton.setOnAction(evt -> {
                try {
                    boolean deleteSuccess = deleteInscription(inscriptionId, event);
                    if (deleteSuccess) {
                        populateEventContainer(userEmail);
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            });

            eventBox.getChildren().addAll(nameField, dateField, cancelButton);
            eventContainer.getChildren().add(eventBox);
        }
        eventContainer.setSpacing(10);
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


        String email = UserSession.getInstance().getMailUser();
        populateEventContainer(email);
    }
}