package com.healthcare;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Health Monitoring App");

        // Create the search bar
        VBox topSection = createTopSection();

        // Middle Section
        GridPane middleSection = createMiddleSection(primaryStage);

        // Bottom Navigation Bar
        HBox bottomNavBar = createBottomNavBar();

        // Main Layout
        VBox mainLayout = new VBox(topSection, middleSection, bottomNavBar);
        mainLayout.setSpacing(20);
        mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #40afff, rgb(255, 255, 255));"); // Gradient background

        Scene scene = new Scene(mainLayout, 400, 800); // Adjust size for mobile view
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createTopSection() {
        ImageView imageView = new ImageView(new Image("C:\\Users\\C M Yuktha\\jss hackathon\\HealthMonitoringApp\\src\\main\\resources\\images\\top_image.jpg"));
        imageView.setFitWidth(300);
        imageView.setFitHeight(350);
        imageView.setPreserveRatio(true);

        Button tryNowButton = new Button("TRY NOW");
        tryNowButton.setStyle("-fx-background-color: rgba(255, 255, 255, 0.3); -fx-border-radius: 10px; -fx-font-size: 16px; -fx-text-fill: white; -fx-font-weight: bold;");
        tryNowButton.setPadding(new Insets(10, 20, 10, 20));

        HBox iconSection = new HBox();
        iconSection.setAlignment(Pos.CENTER);
        iconSection.setSpacing(20);

        String[] iconPaths = {"file:icon1.png", "file:icon2.png", "file:icon3.png"};
        for (String path : iconPaths) {
            VBox iconItem = new VBox();
            iconItem.setAlignment(Pos.CENTER);
            iconItem.setSpacing(5);

            ImageView iconView = new ImageView(new Image(path));
            iconView.setFitWidth(40);
            iconView.setFitHeight(40);

            iconItem.getChildren().add(iconView);
            iconSection.getChildren().add(iconItem);
        }

        VBox topSection = new VBox(imageView, tryNowButton, iconSection);
        topSection.setAlignment(Pos.CENTER);
        topSection.setSpacing(10);
        topSection.setPadding(new Insets(10));
        return topSection;
    }

    private GridPane createMiddleSection(Stage primaryStage) {
        GridPane menuGrid = new GridPane();
        menuGrid.setAlignment(Pos.CENTER);
        menuGrid.setPadding(new Insets(10));
        menuGrid.setHgap(20);
        menuGrid.setVgap(20);

        String[] labels = {"HeartBeat", "Temperature", "Location", "Personalized"};
        String[] icons = {
            "C:\\Users\\C M Yuktha\\jss hackathon\\HealthMonitoringApp\\src\\main\\resources\\images\\heart-rate.png",
            "C:\\Users\\C M Yuktha\\jss hackathon\\HealthMonitoringApp\\src\\main\\resources\\images\\thermometer.png",
            "C:\\Users\\C M Yuktha\\jss hackathon\\HealthMonitoringApp\\src\\main\\resources\\images\\location.png",
            "C:\\Users\\C M Yuktha\\jss hackathon\\HealthMonitoringApp\\src\\main\\resources\\images\\personalized.png"
        };

        for (int i = 0; i < labels.length; i++) {
            Button menuItem = createMenuButton(labels[i], icons[i], primaryStage);
            menuGrid.add(menuItem, i % 2, i / 2); // Arrange in rows and columns
        }
        return menuGrid;
    }

    private Button createMenuButton(String text, String iconPath, Stage primaryStage) {
        ImageView icon = new ImageView(new Image(iconPath));
        icon.setFitWidth(40);
        icon.setFitHeight(40);

        Button button = new Button(text, icon);
        button.setFont(Font.font("Arial", 12));
        button.setStyle("-fx-text-fill: white; -fx-background-color: #00509E; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        button.setContentDisplay(javafx.scene.control.ContentDisplay.TOP);
        button.setAlignment(Pos.CENTER);
        button.setPadding(new Insets(10));

        button.setOnAction(e -> navigateToPage(text, primaryStage));
        return button;
    }

    private void navigateToPage(String page, Stage primaryStage) {
        VBox pageLayout = new VBox();
        pageLayout.setAlignment(Pos.CENTER);
        pageLayout.setSpacing(20);
        pageLayout.setPadding(new Insets(20));
        pageLayout.setStyle("-fx-background-color: white;");

        Label titleLabel = new Label(page);
        titleLabel.setFont(Font.font("Arial", 24));

        ImageView gifView = new ImageView();
        String apiUrl = "";

        switch (page) {
            case "HeartBeat":
                gifView.setImage(new Image(getClass().getResource("/animations/solidarity.gif").toExternalForm()));
                apiUrl = "http://localhost:5000/heart_rate";
                break;
            case "Temperature":
                gifView.setImage(new Image(getClass().getResource("/animations/celsius.gif").toExternalForm()));
                apiUrl = "http://localhost:5000/temperature";
                break;
            case "Location":
                gifView.setImage(new Image(getClass().getResource("/animations/maps.gif").toExternalForm()));
                apiUrl = "http://192.168.30.222:5000/gps";
                break;
            case "Personalized":
    // Set GIF animation
                gifView.setImage(new Image(getClass().getResource("/animations/personalized.gif").toExternalForm()));

    // Define the API URL for the personalized option
                apiUrl = "http://192.168.30.222:5000/analyze";

    // Create a TextArea to display the multiline response
    TextArea responseArea = new TextArea();
    responseArea.setWrapText(true); // Allow text wrapping
    responseArea.setEditable(false); // Make it read-only
    responseArea.setPrefHeight(300); // Set preferred height for better visibility

    // Simulate making an API call and receiving a response
    String llmResponse = callAPI(apiUrl); // You need to define the callAPI method to fetch the response

    // Set the received response text in the TextArea
    responseArea.setText(llmResponse);

    // Add the TextArea to your layout (e.g., VBox, HBox, or Scene)
    yourLayout.getChildren().add(responseArea); // Replace 'yourLayout' with the appropriate container in your scene
    break;

            default:
                gifView.setImage(new Image(getClass().getResource("/animations/default.gif").toExternalForm()));
                break;
        }
        gifView.setFitWidth(200);
        gifView.setFitHeight(200);

        Label dataLabel = new Label("Loading...");
        dataLabel.setFont(Font.font("Arial", 18));
        dataLabel.setStyle("-fx-text-fill: #00509E;");

        final String finalApiUrl = apiUrl;
        new Thread(() -> {
            try {
                String data = fetchDataFromApi(finalApiUrl);
                javafx.application.Platform.runLater(() -> dataLabel.setText(data));
            } catch (Exception e) {
                javafx.application.Platform.runLater(() -> dataLabel.setText("Failed to load data"));
            }
        }).start();

        Button backButton = new Button("Back");
        backButton.setFont(Font.font("Arial", 14));
        backButton.setOnAction(e -> start(primaryStage));

        pageLayout.getChildren().addAll(titleLabel, gifView, dataLabel, backButton);

        Scene scene = new Scene(pageLayout, 400, 800);
        primaryStage.setScene(scene);
    }

    private String fetchDataFromApi(String apiUrl) throws Exception {
        Thread.sleep(30000); 
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            return "Error: " + responseCode;
        }
    }

    private HBox createBottomNavBar() {
        HBox navBar = new HBox();
        navBar.setAlignment(Pos.CENTER);
        navBar.setSpacing(20);
        navBar.setPadding(new Insets(10));
        navBar.setStyle("-fx-background-color: #003366;");

        String[] labels = {"Home", "Fitness", "Sports", "Store", "Transform"};
        String[] icons = {"file:home.png", "file:fitness.png", "file:sports.png", "file:store.png", "file:transform.png"};

        for (int i = 0; i < labels.length; i++) {
            VBox navItem = createMenuItem(labels[i], icons[i]);
            navBar.getChildren().add(navItem);
        }

        return navBar;
    }

    private VBox createMenuItem(String text, String iconPath) {
        ImageView icon = new ImageView(new Image(iconPath));
        icon.setFitWidth(40);
        icon.setFitHeight(40);

        Label label = new Label(text);
        label.setFont(Font.font("Arial", 12));
        label.setStyle("-fx-text-fill: white;");

        VBox menuItem = new VBox(icon, label);
        menuItem.setAlignment(Pos.CENTER);
        menuItem.setSpacing(5);

        return menuItem;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
