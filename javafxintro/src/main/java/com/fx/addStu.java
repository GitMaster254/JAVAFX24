package com.fx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class addStu extends Application {
    private Connection conn;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Insert Student Data");

        Image logo = new Image("file:logo.png");
        primaryStage.getIcons().add(logo);
        ImageView logoImageView = new ImageView(logo);

        Label titleLabel = new Label("Komrades");
        titleLabel.setStyle("-fx-font-size: 24px;");

        // Create a stack pane to center the logo and title
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(logoImageView, titleLabel);

        // Center the logo and title within the stack pane
        StackPane.setAlignment(logoImageView, Pos.CENTER);
        StackPane.setAlignment(titleLabel, Pos.CENTER);
        

        // Create UI elements
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.alignmentProperty();

        Label nameLabel = new Label("Name:");
        GridPane.setConstraints(nameLabel, 0, 0);

        TextField nameField = new TextField();
        GridPane.setConstraints(nameField, 1, 0);

        Label RegNoLabel = new Label("RegNo:");
        GridPane.setConstraints(RegNoLabel, 0, 1);

        TextField RegNoField = new TextField();
        GridPane.setConstraints(RegNoField, 1, 1);

        Label YearOfStdLabel = new Label("YearOfStd:");
        GridPane.setConstraints(YearOfStdLabel, 0, 2);

        TextField YearOfStdField = new TextField();
        GridPane.setConstraints(YearOfStdField, 1, 2);


        

        Button addButton = new Button("Add");
        
        GridPane.setConstraints(addButton, 0, 3);
        addButton.setOnAction(e -> {
            // Get the entered values
            String name = nameField.getText();
            String RegNo = (RegNoField).getText();
            int YearOfStd = Integer.parseInt((YearOfStdField).getText());
            // Insert the data into the database
            insertStudentData(name, RegNo, YearOfStd);

            nameField.clear();
            RegNoField.clear();
            YearOfStdField.clear();
        });

        // Add UI elements to the grid
        grid.getChildren().addAll(nameLabel, nameField, RegNoLabel, RegNoField, YearOfStdLabel, YearOfStdField, addButton);

        // Set up the scene and show the stage
        Scene scene = new Scene(grid, 300, 200);
        scene.getStylesheets().add(getClass().getResource("resources/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(true);

        // Connect to the database
        connectToDatabase();
    }

    private void connectToDatabase() {
        try {
            // Initialize JDBC driver and establish connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demodb", "root", "");
            System.out.println("Connected to database");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertStudentData(String name, String RegNo, int YearOfStd) {
        try {
            // Prepare the SQL statement
            String sql = "INSERT INTO students (name, RegNo, YearOfStd) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, RegNo);
            pstmt.setInt(3, YearOfStd);

            // Execute the statement
            pstmt.executeUpdate();
            System.out.println("Student data inserted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        // Close the database connection when the application stops
        if (conn != null) {
            conn.close();
            System.out.println("Disconnected from database");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

