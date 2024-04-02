package org.example.heaventfx;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.LocalDate;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

public class HeaventCreationController {

    private String utilisateurActuel;

    @FXML
    private Button deconnexion;


    @FXML
    private Button creeEvent;

    @FXML
    private Label usernamelabel;

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
        this.utilisateurActuel = utilisateurActuel;
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
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    private List<Event> readEventsFromJson() {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("src/main/resources/org/example/heaventfx/event.json");
        try {
            if (file.exists()) {
                return new ArrayList<>(Arrays.asList(mapper.readValue(file, Event[].class)));
            } else {
                return new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void writeEventsToJson(List<Event> events) {
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        File file = new File("src/main/resources/org/example/heaventfx/event.json");
        try {
            mapper.writeValue(file, events);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void crEvent(MouseEvent event) {
        String type = typeEventComboBox.getValue();
        String nomProjet = nomProjetField.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateEvent = dateEventField.getValue().format(formatter);
        String descriptionEvent = descriptionEventField.getText();
        int siegeDispo = Integer.parseInt(siegeDispoField.getText());

        Event newEvent = new Event(utilisateurActuel, nomProjet, dateEvent, type, descriptionEvent, siegeDispo);

        List<Event> events = readEventsFromJson();
        events.add(newEvent);
        writeEventsToJson(events);
    }

    // ... autres méthodes ...




    @FXML
    public void initialize() {

        typeEventComboBox.getItems().addAll("Concert", "Conference", "Exposition", "Mariage", "Autre");
        String username = UserSession.getInstance().getUsername();
        setUtilisateurActuel(username);
        usernamelabel.setText(username);

        DateTimeFormatter dateFormatter = DateUtils.getDateFormatter();
        StringConverter<LocalDate> converter = new LocalDateStringConverter(dateFormatter, dateFormatter);
        dateEventField.setConverter(converter);

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
    }
}


