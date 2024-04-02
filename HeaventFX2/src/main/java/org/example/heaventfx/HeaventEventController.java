package org.example.heaventfx;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.TranslateTransition;
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
import javafx.util.Duration;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

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
    private Button GoEvent;

    @FXML
    private TextField barreRecherche;

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
        EventName.setText(event.getNomProjet());
        EventDate.setText(event.getDateEvent().toString());
        EventDate.setEditable(false);
        EventDate.setDisable(true);
        EventSiege.setText(String.valueOf(event.getSiegeDispo()));
        EventSiege.setEditable(false);
        EventSiege.setDisable(true);
        descriptionEventField.setText(event.getDescriptionEvent());
        descriptionEventField.setEditable(false);
        descriptionEventField.setDisable(true);

        String imagePath = "/org/example/heaventfx/image/Type d'évènement/";
        switch (event.getTypeEvent()) {
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
    void ReservEvent() {
        String username = UserSession.getInstance().getUsername();
        reserveEvent(currentEvent.getId(), username);
    }

    public void reserveEvent(String eventId, String username) {
        try {
            // Load the users.json file
            String usersContent = new String(Files.readAllBytes(Paths.get("src/main/resources/org/example/heaventfx/users.json")));
            JSONArray users = new JSONArray(usersContent);

            // Find the current user and get his email
            String email = null;
            for (int i = 0; i < users.length(); i++) {
                JSONObject user = users.getJSONObject(i);
                if (user.getString("username").equals(username)) {
                    email = user.getString("email");
                    break;
                }
            }

            // Load the event.json file
            String eventsContent = new String(Files.readAllBytes(Paths.get("src/main/resources/org/example/heaventfx/event.json")));
            JSONArray events = new JSONArray(eventsContent);

            // Find the event and add the user's email
            for (int i = 0; i < events.length(); i++) {
                JSONObject event = events.getJSONObject(i);
                if (event.getString("id").equals(eventId)) {
                    JSONArray participants = event.optJSONArray("participants");
                    if (participants == null) {
                        participants = new JSONArray();
                        event.put("participants", participants);
                    }
                    JSONObject participant = new JSONObject();
                    participant.put("username", username);
                    participant.put("email", email);
                    participants.put(participant);

                    int currentSeats = event.getInt("siegeDispo");
                    if (currentSeats > 0) {
                        event.put("siegeDispo", currentSeats - 1);
                    } else {
                        System.out.println("Aucune place disponible pour cet événement.");
                        return;
                    }
                    break;
                }
            }

            // Save the changes to the event.json file
            Files.write(Paths.get("src/main/resources/org/example/heaventfx/event.json"), events.toString().getBytes());
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
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

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

        EventName.setAlignment(Pos.CENTER);
        EventName.widthProperty().addListener((obs, oldVal, newVal) -> {
            EventName.setAlignment(Pos.CENTER);
        });
    }
}

