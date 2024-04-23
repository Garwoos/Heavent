package org.example.heaventfx;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.time.ZoneId;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HeaventNotificationController {

    @FXML
    private Button Quitter;

    @FXML
    private VBox notificationsContainer;

    private Event getEventById(int eventId) {
        String url = "http://localhost:8443/eventsheavent/readbyid/" + eventId;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.body(), Event.class);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    public void initialize() {
        // Récupérez les notifications de l'utilisateur
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                .withLocale(Locale.FRENCH);
        String frenchDate = LocalDateTime.now().format(formatter);

        List<Notification> notifications = getAllNotifications();

        Map<Integer, Notification> mostRecentNotifications = new HashMap<>();

        for (Notification notification : notifications) {
            int eventId = notification.getEvent().getId();
            // If the map already contains a notification for the event and it's older, update it with the current notification
            if (!mostRecentNotifications.containsKey(eventId) || mostRecentNotifications.get(eventId).getDate().before(notification.getDate())) {
                mostRecentNotifications.put(eventId, notification);
            }
        }

        notificationsContainer.setSpacing(10);

        List<Notification> sortedNotifications = new ArrayList<>(mostRecentNotifications.values());
        sortedNotifications.sort(Comparator.comparing(Notification::getDate).reversed());
        // Affichez chaque notification dans la VBox
        for (Notification notification : sortedNotifications) {
            // Récupérez l'événement correspondant à la notification
            Event event = getEventById(notification.getEvent().getId());

            // Récupérez le nom de l'événement
            String eventName = event.getName();

            TextFlow textFlow = new TextFlow();

            Text text1 = new Text("L'évènement ");
            text1.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
            text1.setFill(Color.WHITE); // Ajoutez cette ligne

            Text text2 = new Text(eventName);
            text2.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
            text2.setFill(Color.WHITE);

            Text text3;
            if (notification.getNotificationType().equals("Modification")) {
                text3 = new Text(" a été modifié le ");
            } else if (notification.getNotificationType().equals("Suppression")) {
                text3 = new Text(" a été supprimé le ");
            } else if (notification.getNotificationType().equals("Creation")) {
                text3 = new Text(" a été crée le ");
            } else {
                text3 = new Text(" a subi une action non spécifiée le ");
            }
            text3.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
            text3.setFill(Color.WHITE);

            String notificationDateInFrench = formatter.format(notification.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            Text text4 = new Text(notificationDateInFrench);
            text4.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
            text4.setFill(Color.WHITE);

            textFlow.getChildren().addAll(text1, text2, text3, text4);
            notificationsContainer.getChildren().add(textFlow);
        }
        // Définissez une action pour le bouton Quitter
        Quitter.setOnAction(event -> {
            Stage stage = (Stage) Quitter.getScene().getWindow();
            stage.close();
        });
    }

    private List<Notification> getAllNotifications() {
        String url = "http://localhost:8443/notificationsheavent/read";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.body(), new TypeReference<List<Notification>>(){});
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return new ArrayList<>(); // Retournez une liste vide en cas d'erreur
        }
    }
}