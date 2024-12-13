package com.healthcare;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
        mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #40afff,rgb(255, 255, 255));"); // Yellow to dark blue gradient

        Scene scene = new Scene(mainLayout, 400, 800); // Adjust size for mobile view
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createTopSection() {
        // Image
        ImageView imageView = new ImageView(new Image("C:\\Users\\C M Yuktha\\jss hackathon\\HealthMonitoringApp\\src\\main\\resources\\images\\top_image.jpg")); // Replace with your image path
        imageView.setFitWidth(300);
        imageView.setFitHeight(350);
        imageView.setPreserveRatio(true);

        // "TRY NOW" Button
        Button tryNowButton = new Button("TRY NOW");
        tryNowButton.setStyle("-fx-background-color: rgba(255, 255, 255, 0.3); -fx-border-radius: 10px; -fx-font-size: 16px; -fx-text-fill: white; -fx-font-weight: bold;");
        tryNowButton.setPadding(new Insets(10, 20, 10, 20));

        // Icons Section
        HBox iconSection = new HBox();
        iconSection.setAlignment(Pos.CENTER);
        iconSection.setSpacing(20);

        // Add icons
        String[] iconPaths = {
            "file:icon1.png", // Replace with actual file paths
            "file:icon2.png",
            "file:icon3.png"
        };

        for (int i = 0; i < iconPaths.length; i++) {
            VBox iconItem = new VBox();
            iconItem.setAlignment(Pos.CENTER);
            iconItem.setSpacing(5);

            ImageView iconView = new ImageView(new Image(iconPaths[i]));
            iconView.setFitWidth(40);
            iconView.setFitHeight(40);

            iconItem.getChildren().add(iconView);
            iconSection.getChildren().add(iconItem);
        }

        // Combine elements in top section
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

        // Icons and labels
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
        ImageView icon = new ImageView(new Image(iconPath)); // Replace with your icon path
        icon.setFitWidth(40);
        icon.setFitHeight(40);

        Button button = new Button(text, icon);
        button.setFont(Font.font("Arial", 12));
        button.setStyle("-fx-text-fill: white; -fx-background-color: #00509E; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        button.setContentDisplay(javafx.scene.control.ContentDisplay.TOP);
        button.setAlignment(Pos.CENTER);
        button.setPadding(new Insets(10));

        // Add action event
        button.setOnAction(e -> navigateToPage(text, primaryStage));

        return button;
    }

    private void navigateToPage(String page, Stage primaryStage) {
        // Create the new page layout
        VBox pageLayout = new VBox();
        pageLayout.setAlignment(Pos.CENTER);
        pageLayout.setSpacing(20);
        pageLayout.setPadding(new Insets(20));
    
        // Set the background color to white
        pageLayout.setStyle("-fx-background-color: white;");
    
        // Title Label
        Label titleLabel = new Label(page);
        titleLabel.setFont(Font.font("Arial", 24));
    
        // Animation (GIF) image
        ImageView gifView = new ImageView();
        switch (page) {
            case "HeartBeat":
                gifView.setImage(new Image("C:\\Users\\C M Yuktha\\jss hackathon\\HealthMonitoringApp\\src\\main\\resources\\animations\\solidarity.gif"));
                break;
            case "Temperature":
                gifView.setImage(new Image("C:\\Users\\C M Yuktha\\jss hackathon\\HealthMonitoringApp\\src\\main\\resources\\animations\\celsius.gif"));
                break;
            case "Location":
                gifView.setImage(new Image("C:\\Users\\C M Yuktha\\jss hackathon\\HealthMonitoringApp\\src\\main\\resources\\animations\\maps.gif"));
                break;
            case "Personalized":
                gifView.setImage(new Image("file:personalized.gif"));
                break;
        }
    
        gifView.setFitWidth(200);
        gifView.setFitHeight(200);
    
        // Back Button
        Button backButton = new Button("Back");
        backButton.setFont(Font.font("Arial", 14));
        backButton.setOnAction(e -> start(primaryStage));
    
        // Add all components to the layout
        pageLayout.getChildren().addAll(titleLabel, gifView, backButton);
    
        // Set the new scene with white background
        Scene scene = new Scene(pageLayout, 400, 800);
        primaryStage.setScene(scene);
    }
    

    private HBox createBottomNavBar() {
        HBox navBar = new HBox();
        navBar.setAlignment(Pos.CENTER);
        navBar.setSpacing(20);
        navBar.setPadding(new Insets(10));
        navBar.setStyle("-fx-background-color: #003366;");

        String[] labels = {"Home", "Fitness", "Sports", "Store", "Transform"};
        String[] icons = {"file:home.png", "file:fitness.png", "file:sports.png", "file:store.png", "file:transform.png"}; // Replace with image paths

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
