package org.example.heaventfx;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.stream.Collectors;


public class HeaventController{
    @FXML
    private Button deconnexion;

    @FXML
    private Button Reserv;

    @FXML
    private Button recherche;

    @FXML
    private TextField barreRecherche;

    @FXML
    private Label usernamelabel;

    @FXML
    private Pane textPane;

    @FXML
    private Pane carouselPane;

    @FXML
    private Button Accueil;

    @FXML
    private Button GestionEvent;

    @FXML
    private Button creatEvent;

    @FXML
    private ImageView tiktok;

    @FXML
    private ImageView cloche;

    @FXML
    private ImageView twitter;

    @FXML
    private ImageView insta;

    @FXML
    private ImageView facebook;

    @FXML
    private ImageView linkedin;

    @FXML
    private Button menu;

    @FXML
    private Button menuClose;

    @FXML
    private VBox slider;

    @FXML
    private ListView<String> searchResults;

    private List<Event> events;

    private Stage stage;

    private ImageView carouselImageView;
    private List<Image> carouselImages;
    private int carouselCurrentIndex = 0;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void createCarousel() {
        carouselImages = Arrays.asList(
                new Image("org/example/heaventfx/image/Type d'évènement/Concert.jpg"),
                new Image("org/example/heaventfx/image/Type d'évènement/mariage.jpg"),
                new Image("org/example/heaventfx/image/Type d'évènement/roger.jpg"),
                new Image("org/example/heaventfx/image/Type d'évènement/exposition.jpg"),
                new Image("org/example/heaventfx/image/Type d'évènement/conference.jpg")
                // Add as many images as you want
        );

        carouselImageView = new ImageView(carouselImages.get(carouselCurrentIndex));
        carouselImageView.setFitWidth(150);
        carouselImageView.setFitHeight(200);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            carouselCurrentIndex = (carouselCurrentIndex + 1) % carouselImages.size();
            carouselImageView.setImage(carouselImages.get(carouselCurrentIndex));
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Add carouselImageView to your user interface
        // For example, if you have a Pane named carouselPane:
        carouselPane.getChildren().add(carouselImageView);
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
    void welcome() {

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

    private void roundImageView(ImageView imageView) {
        Rectangle clip = new Rectangle(
                imageView.getFitWidth(), imageView.getFitHeight()
        );
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        imageView.setClip(clip);
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

        createCarousel();

        Label welcomeLabel = new Label("Nous sommes heureux que vous ayez choisis\nHeavent pour vous accompagner ou\nparticiper à des évènements.\nMerci de nous faire confiance et\navant tout amusez vous bien !");
        welcomeLabel.setWrapText(true);
        welcomeLabel.setOpacity(1.0);
        welcomeLabel.setFont(new Font(14));
        welcomeLabel.setTextFill(Color.WHITE);

        // Ajout du Label au textPane
        textPane.getChildren().add(welcomeLabel);



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

        roundImageView(tiktok);
        roundImageView(twitter);
        roundImageView(insta);
        roundImageView(facebook);
        roundImageView(linkedin);
        roundImageView(cloche);


    }

}


