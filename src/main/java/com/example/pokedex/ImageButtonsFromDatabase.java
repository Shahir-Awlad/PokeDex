package com.example.pokedex;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ImageButtonsFromDatabase extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Connect to the SQLite database
        List<Image> images = new ArrayList<>();
        List<String> types = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:/D:\\CSE\\2-2\\CSE 4402\\PokeDex\\src\\main\\resources\\com\\example\\pokedex\\database.db")) {
            Statement stmt = conn.createStatement();
            //Statement stmt2 = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT Image FROM Pokemons");
            //ResultSet rs2 = stmt2.executeQuery("Select Type_1 FROM Pokemons");
            while (rs.next()) {
                // Retrieve blob data as bytes
                byte[] imageData = rs.getBytes("Image");
                // Convert byte array to Image
                Image image = new Image(new ByteArrayInputStream(imageData));
                images.add(image);

                //String temp = rs2.getString("Type_1");
                //types.add(temp);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:/D:\\CSE\\2-2\\CSE 4402\\PokeDex\\src\\main\\resources\\com\\example\\pokedex\\database.db")) {
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("Select Type_1 FROM Pokemons");
            while (rs.next()) {
                String temp = rs.getString("Type_1");
                types.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Create a root VBox
        VBox root = new VBox(10); // 10 is the vertical spacing between children

        // Create buttons with images
        HBox currentRow = new HBox(10); // 10 is the horizontal spacing between children
        int count = 0; // Counter for buttons in current row
        for (Image image : images) {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(80); // Set the width of the ImageView
            imageView.setFitHeight(80); // Set the height of the ImageView

            Label label = new Label();
            label.setStyle("-fx-background-color: white; -fx-padding: 5px;");

            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(imageView, label);
            StackPane.setAlignment(imageView, Pos.BOTTOM_RIGHT); // Align image to the bottom left


            StackPane.setAlignment(label, Pos.BOTTOM_LEFT);

            Button button = new Button();
            button.setPrefHeight(130);
            button.setPrefWidth(200);
            button.setGraphic(stackPane);

            if(Objects.equals(types.get(count), "Grass"))
                button.setStyle("-fx-background-color: #50d4b4; -fx-background-radius: 15;");
            if(Objects.equals(types.get(count), "Fire"))
                button.setStyle("-fx-background-color: #ff6c6c; -fx-background-radius: 15;");
            if(Objects.equals(types.get(count), "Water"))
                button.setStyle("-fx-background-color: #78bcfc; -fx-background-radius: 15;");
            if(Objects.equals(types.get(count), "Ground"))
                button.setStyle("-fx-background-color: #e0bc74; -fx-background-radius: 15;");
            if(Objects.equals(types.get(count), "Normal"))
                button.setStyle("-fx-background-color: #b0a47c; -fx-background-radius: 15;");
            if(Objects.equals(types.get(count), "Fighting"))
                button.setStyle("-fx-background-color: #b83c34; -fx-background-radius: 15;");
            if(Objects.equals(types.get(count), "Flying"))
                button.setStyle("-fx-background-color: #a894cc; -fx-background-radius: 15;");
            if(Objects.equals(types.get(count), "Poison"))
                button.setStyle("-fx-background-color: #984c94; -fx-background-radius: 15;");
            if(Objects.equals(types.get(count), "Electric"))
                button.setStyle("-fx-background-color: #f8d454; -fx-background-radius: 15;");
            if(Objects.equals(types.get(count), "Psychic"))
                button.setStyle("-fx-background-color: #e8648c; -fx-background-radius: 15;");
            if(Objects.equals(types.get(count), "Rock"))
                button.setStyle("-fx-background-color: #b8a44c; -fx-background-radius: 15;");
            if(Objects.equals(types.get(count), "Ice"))
                button.setStyle("-fx-background-color: #a8d4dc; -fx-background-radius: 15;");
            if(Objects.equals(types.get(count), "Bug"))
                button.setStyle("-fx-background-color: #b0b444; -fx-background-radius: 15;");
            if(Objects.equals(types.get(count), "Dragon"))
                button.setStyle("-fx-background-color: #544ca0; -fx-background-radius: 15;");
            if(Objects.equals(types.get(count), "Ghost"))
                button.setStyle("-fx-background-color: #705c94; -fx-background-radius: 15;");
            if(Objects.equals(types.get(count), "Dark"))
                button.setStyle("-fx-background-color: #705c4c; -fx-background-radius: 15;");
            if(Objects.equals(types.get(count), "Steel"))
                button.setStyle("-fx-background-color: #c0bccc; -fx-background-radius: 15;");
            count++;


            // Set the style of the button to make it rounded



            currentRow.getChildren().add(button); // Add the button to the current row


            // If the current row is full or we reached the end of images, add the row to the scene
            if (count % 5 == 0 || images.indexOf(image) == images.size() - 1) {
                root.getChildren().add(currentRow); // Add the row to the root
                currentRow = new HBox(10); // Start a new row
            }
        }

        // Set up the JavaFX Scene
        primaryStage.setTitle("Image Buttons from Database");
        primaryStage.setScene(new Scene(root)); // Set initial scene size
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
