package org.example.heaventfx;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
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
    private TextField barreRecherche;

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
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
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
    void search(MouseEvent event) {
        String query = barreRecherche.getText();
        Event selectedEvent = searchEventInJson(query);
        if (selectedEvent != null) {
            openEventPage(selectedEvent);
        }
    }

    private Event searchEventInJson(String eventName) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Event> events = Arrays.asList(mapper.readValue(new File("src/main/resources/org/example/heaventfx/event.json"), Event[].class));
            return events.stream()
                    .filter(event -> event.getNomProjet().equals(eventName))
                    .findFirst()
                    .orElse(null);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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

    private List<Event> getEventsForUser(String email, String name) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Event> events = Arrays.asList(mapper.readValue(new File("src/main/resources/org/example/heaventfx/event.json"), Event[].class));
            return events.stream()
                    .filter(event -> event.getParticipants() != null && event.getParticipants().stream().anyMatch(participant -> participant.getEmail().equals(email) || participant.getUsername().equals(name)))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private void updateEventJsonFile(Event event) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Event> events = Arrays.asList(mapper.readValue(new File("src/main/resources/org/example/heaventfx/event.json"), Event[].class));
            events.removeIf(e -> e.getNomProjet().equals(event.getNomProjet()));
            events.add(event);
            mapper.writeValue(new File("src/main/resources/org/example/heaventfx/event.json"), events);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void initialize() {

        String username = UserSession.getInstance().getUsername();
        usernamelabel.setText(username);


        slider.setTranslateX(200);
        menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.5));
            slide.setNode(slider);
            slide.setToX(0);
            slide.play();
            slide.setOnFinished((ActionEvent e) -> {
                menu.setVisible(false);
                menuClose.setVisible(true);
            });
        });

        menuClose.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.5));
            slide.setNode(slider);
            slide.setToX(200);
            slide.play();
            slide.setOnFinished((ActionEvent e) -> {
                menu.setVisible(true);
                menuClose.setVisible(false);
            });
        });

        String name = UserSession.getInstance().getUsername();
        String email = UserSession.getInstance().getEmail();
        usernamelabel.setText(username);

        List<Event> userEvents = getEventsForUser(email, name);
        for (int i = 0; i < userEvents.size(); i++) {
            Event event = userEvents.get(i);
            HBox eventBox = new HBox();

            TextField nameField = new TextField(event.getNomProjet());
            nameField.setEditable(false);
            nameField.setStyle("-fx-background-color: #282B30; -fx-text-fill: white;");

            TextField dateField = new TextField(event.getDateEvent());
            dateField.setEditable(false);
            dateField.setStyle("-fx-background-color: #282B30; -fx-text-fill: white;");

            Button cancelButton = new Button("Annuler");
            cancelButton.getStyleClass().add("form-button");
            cancelButton.setStyle("-fx-background-color: #282B30; -fx-text-fill: white;");
            cancelButton.setCursor(Cursor.HAND);
            cancelButton.setOnAction(e -> {
                event.getParticipants().removeIf(participant -> participant.getEmail().equals(email) || participant.getUsername().equals(name));
                updateEventJsonFile(event);
                eventContainer.getChildren().remove(eventBox);
            });

            eventBox.getChildren().addAll(dateField, nameField, cancelButton);
            eventContainer.getChildren().add(eventBox);
        }
    }
}