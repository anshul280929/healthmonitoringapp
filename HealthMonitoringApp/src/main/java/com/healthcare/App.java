package com.healthcare;

import java.net.URL;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Health Monitoring App");

        // Create the search bar
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search...");
        searchBar.getStyleClass().add("search-bar");

        // Create the navigation menu
        HBox navBar = new HBox();
        navBar.setSpacing(15);
        navBar.setPadding(new Insets(10));
        navBar.getStyleClass().add("navbar");

        Button homeButton = new Button("Home");
        homeButton.getStyleClass().add("nav-item");

        Button aboutUsButton = new Button("About Us");
        aboutUsButton.getStyleClass().add("nav-item");

        Button missionButton = new Button("Our Mission");
        missionButton.getStyleClass().add("nav-item");

        Button servicesButton = new Button("Our Services");
        servicesButton.getStyleClass().add("nav-item");

        Button contactUsButton = new Button("Contact Us");
        contactUsButton.getStyleClass().add("nav-item");

        Button reviewsButton = new Button("Reviews");
        reviewsButton.getStyleClass().add("nav-item");

        navBar.getChildren().addAll(homeButton, aboutUsButton, missionButton, servicesButton, contactUsButton, reviewsButton);

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

        // Main content layout
        VBox contentLayout = new VBox(15, heartRateLabel, fetchHeartRateButton, temperatureLabel, fetchTemperatureButton, locationLabel, fetchLocationButton);
        contentLayout.setPadding(new Insets(20));
        contentLayout.getStyleClass().add("vbox");

        // Create a layout to align the search bar to the right
        HBox topLayout = new HBox(20); // Spacing between the search bar and navbar
        topLayout.setPadding(new Insets(10));
        topLayout.setAlignment(Pos.CENTER_LEFT); // Align the items to the left by default
        topLayout.getChildren().addAll(new VBox(), searchBar); // Add search bar to the layout

        // Combine everything into a BorderPane layout
        BorderPane rootLayout = new BorderPane();
        rootLayout.setTop(new VBox(topLayout, navBar)); // Add top layout (with search bar) and navbar
        rootLayout.setCenter(contentLayout); // Main content in the center

        // Create the Scene first, then apply the CSS
        Scene scene = new Scene(rootLayout, 800, 600, Color.WHITE);

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
