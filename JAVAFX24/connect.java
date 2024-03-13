package JAVAFX24;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class connect extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("SQLite Example");

        // Create a label to display database content
        Label label = new Label();

        // Connect to SQLite database
        try (Connection conn = DriverManager.getConnection(":A:DataFromSQLite:Data.sqlite3");
             Statement stmt = conn.createStatement()) {

            // Create a table (if not exists)
            stmt.execute("CREATE TABLE IF NOT EXISTS example_table (id INTEGER PRIMARY KEY, name TEXT)");

            // Insert data into the table
            stmt.execute("INSERT INTO example_table (name) VALUES ('John')");
            stmt.execute("INSERT INTO example_table (name) VALUES ('Jane')");

            // Query data from the table
             ResultSet resultSet = stmt.executeQuery("SELECT * FROM example_table");
             StringBuilder sb = new StringBuilder();
             while (resultSet.next()) {
                 sb.append("ID: ").append(resultSet.getInt("id"))
                         .append(", Name: ").append(resultSet.getString("name"))
                         .append("\n");
             }
             label.setText(sb.toString());

        } catch (Exception e) {
            e.printStackTrace();
            label.setText("Error: " + e.getMessage());
        }

        // Create a scene
        StackPane root = new StackPane();
        root.getChildren().add(label);
        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
