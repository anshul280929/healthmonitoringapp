package com.healthcare;

import java.net.URL;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Health Monitoring App");

        // Labels to display sensor data
        Label heartRateLabel = new Label("Heart Rate: ");
        heartRateLabel.getStyleClass().add("label");

        Label temperatureLabel = new Label("Temperature: ");
        temperatureLabel.getStyleClass().add("label");

        Label locationLabel = new Label("Location: ");
        locationLabel.getStyleClass().add("label");

        // Buttons to fetch data with hover effects and tooltips
        Button fetchHeartRateButton = new Button("Fetch Heart Rate");
        fetchHeartRateButton.getStyleClass().add("button");
        fetchHeartRateButton.setOnAction(event -> heartRateLabel.setText("Heart Rate: 72 bpm"));
        fetchHeartRateButton.setTooltip(new javafx.scene.control.Tooltip("Click to fetch heart rate"));

        Button fetchTemperatureButton = new Button("Fetch Temperature");
        fetchTemperatureButton.getStyleClass().add("button");
        fetchTemperatureButton.setOnAction(event -> temperatureLabel.setText("Temperature: 36.5°C"));
        fetchTemperatureButton.setTooltip(new javafx.scene.control.Tooltip("Click to fetch temperature"));

        Button fetchLocationButton = new Button("Fetch Location");
        fetchLocationButton.getStyleClass().add("button");
        fetchLocationButton.setOnAction(event -> locationLabel.setText("Location: Latitude 12.9716, Longitude 77.5946"));
        fetchLocationButton.setTooltip(new javafx.scene.control.Tooltip("Click to fetch location"));

        // Layout with better spacing and alignment
        VBox layout = new VBox(15, heartRateLabel, fetchHeartRateButton, temperatureLabel, fetchTemperatureButton, locationLabel, fetchLocationButton);
        layout.getStyleClass().add("vbox");

        // Create the Scene first, then apply the CSS
        Scene scene = new Scene(layout, 400, 300, Color.WHITE);

        // Correct way to load the CSS file
        String cssPath = "/style.css"; // Updated path
        URL cssFile = getClass().getResource(cssPath);
        if (cssFile != null) {
            scene.getStylesheets().add(cssFile.toExternalForm());
        } else {
            System.err.println("CSS file not found: " + cssPath);
        }

        // Apply the scene to the stage
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
