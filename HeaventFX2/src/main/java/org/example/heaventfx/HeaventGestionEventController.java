package org.example.heaventfx;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    private ComboBox<String> typeEventComboBox;

    @FXML
    private Button menu;

    @FXML
    private Button menuClose;

    @FXML
    private VBox slider;

    @FXML
    private ComboBox<Event> choixEvent;

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
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    @FXML
    void modifEvent(MouseEvent event) {
        Event selectedEvent = choixEvent.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            saveChangesToEvent(selectedEvent);
        }
    }

    private void loadUserEvents() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Event> events = Arrays.asList(mapper.readValue(new File("src/main/resources/org/example/heaventfx/event.json"), Event[].class));
            List<Event> userEvents = events.stream()
                    .filter(event -> event.getNomCreateur().equals(utilisateurActuel))
                    .collect(Collectors.toList());
            choixEvent.setItems(FXCollections.observableArrayList(userEvents));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadEventIntoForm(Event event) {
        nomProjetField.setText(event.getNomProjet());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dateEventField.setValue(LocalDate.parse(event.getDateEvent(), formatter));
        descriptionEventField.setText(event.getDescriptionEvent());
        siegeDispoField.setText(String.valueOf(event.getSiegeDispo()));
        typeEventComboBox.setValue(event.getTypeEvent());
        ModifGestion.setVisible(true);
    }

    private void saveChangesToEvent(Event event) {
        event.setNomProjet(nomProjetField.getText());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateEvent = dateEventField.getValue().format(formatter);
        event.setDescriptionEvent(descriptionEventField.getText());
        event.setSiegeDispo(Integer.parseInt(siegeDispoField.getText()));
        event.setTypeEvent(typeEventComboBox.getValue());

        // Enregistrez les modifications dans le fichier JSON
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Event> events = Arrays.asList(mapper.readValue(new File("src/main/resources/org/example/heaventfx/event.json"), Event[].class));
            List<Event> updatedEvents = events.stream()
                    .map(e -> e.getId().equals(event.getId()) ? event : e)
                    .collect(Collectors.toList());
            mapper.writeValue(new File("src/main/resources/org/example/heaventfx/event.json"), updatedEvents);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {

        typeEventComboBox.getItems().addAll("Concert", "Conference", "Exposition", "Mariage", "Autre");
        String username = UserSession.getInstance().getUsername();
        setUtilisateurActuel(username);
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

        loadUserEvents();

        ModifGestion.setVisible(false);

        choixEvent.setOnAction((event) -> {
            Event selectedEvent = choixEvent.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                ModifGestion.setVisible(true);
                loadEventIntoForm(selectedEvent);
            }
        });

    }
}