package com.example.pokedex;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.*;

public class HelloController {

    @FXML
    ImageView img;

    public void imgTester()
    {
        try {
            // Connect to the SQLite database
            Connection conn = DriverManager.getConnection("jdbc:sqlite:/C:\\CSE\\2-2\\CSE 4402\\Lab 04\\PokeDex\\src\\main\\resources\\com\\example\\pokedex\\database.db");

            // Execute a SELECT query to retrieve the blob data
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Image FROM Pokemons WHERE P_ID = 13");

            // Check if result set is not empty
            if (rs.next()) {
                byte[] imageData = rs.getBytes("image");
                try {
                    Image image = new Image(new ByteArrayInputStream(imageData));
                    img.setImage(image);
                    System.out.println("Image created successfully");  // Debugging line
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Failed to create Image");  // Debugging line
                }

                // Set the JavaFX Image to the ImageView

            } else {
                System.out.println("No image found.");
            }

            // Close connections
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}