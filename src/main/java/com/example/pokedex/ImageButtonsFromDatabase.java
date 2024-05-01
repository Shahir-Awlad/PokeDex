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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ImageButtonsFromDatabase extends Application {

    Stage secondaryStage = new Stage();

    @Override
    public void start(Stage primaryStage) {
        // Connect to the SQLite database
        List<Image> images = new ArrayList<>();
        List<String> types = new ArrayList<>();
        List<String> types2 = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<String> id = new ArrayList<>();
        List<String> desc = new ArrayList<>();
        List<String> heights = new ArrayList<>();
        List<String> weights = new ArrayList<>();
        List<Integer> favourites = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:/D:\\CSE\\2-2\\CSE 4402\\PokeDex\\src\\main\\resources\\com\\example\\pokedex\\database.db")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Image FROM Pokemons");
            while (rs.next()) {
                byte[] imageData = rs.getBytes("Image");
                Image image = new Image(new ByteArrayInputStream(imageData));
                images.add(image);

            }

            rs = stmt.executeQuery("Select Type_1 FROM Pokemons");
            while (rs.next()) {
                String temp = rs.getString("Type_1");
                types.add(temp);
            }

            rs = stmt.executeQuery("Select Type_2 FROM Pokemons");
            while (rs.next()) {
                String temp = rs.getString("Type_2");
                types2.add(temp);
            }

            rs = stmt.executeQuery("Select Name FROM Pokemons");
            while (rs.next()) {
                String temp = rs.getString("Name");
                names.add(temp);
            }

            rs = stmt.executeQuery("Select P_ID FROM Pokemons");
            while (rs.next()) {
                String temp = rs.getString("P_ID");
                id.add(temp);
            }

            rs = stmt.executeQuery("Select Description FROM Pokemons");
            while (rs.next()) {
                String temp = rs.getString("Description");
                desc.add(temp);
            }

            rs = stmt.executeQuery("SELECT Height FROM Pokemons");
            while (rs.next()) {
                String temp = rs.getString("Height");
                heights.add(temp);
            }

            rs = stmt.executeQuery("SELECT Weight FROM Pokemons");
            while (rs.next()) {
                String temp = rs.getString("Weight");
                weights.add(temp);
            }

            rs = stmt.executeQuery("SELECT isFavourite FROM Pokemons");
            while (rs.next()) {
                String temp = rs.getString("isFavourite");
                favourites.add(Integer.valueOf(temp));
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

            Label labelT1 = new Label(types.get(count));
            labelT1.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-padding: 5px; -fx-background-radius: 30; -fx-text-fill: white; -fx-font-weight: bold;");
            labelT1.setMaxWidth(60);
            labelT1.setAlignment(Pos.CENTER);

            Label labelT2 = new Label(types2.get(count));
            if(types2.get(count) != null)
                labelT2.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-padding: 5px; -fx-background-radius: 30; -fx-text-fill: white; -fx-font-weight: bold;");
            labelT2.setMaxWidth(60);
            labelT2.setAlignment(Pos.CENTER);

            Label nameLabel = new Label(names.get(count));
            nameLabel.setStyle("-fx-background-color: rgba(255, 255, 255, 0.0); -fx-padding: 5px; -fx-background-radius: 30; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13pt;");
            nameLabel.setAlignment(Pos.CENTER);

            Label idLabel = new Label("#" + id.get(count));
            idLabel.setStyle("-fx-background-color: rgba(255, 255, 255, 0.0); -fx-padding: 5px; -fx-background-radius: 30; -fx-text-fill: #303236; -fx-font-weight: bold; -fx-font-size: 11pt;");
            idLabel.setAlignment(Pos.CENTER);

            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(imageView, labelT1, labelT2, nameLabel, idLabel);
            StackPane.setAlignment(imageView, Pos.BOTTOM_RIGHT);
            StackPane.setAlignment(labelT1, Pos.CENTER_LEFT);
            StackPane.setAlignment(labelT2, Pos.BOTTOM_LEFT);
            StackPane.setAlignment(nameLabel, Pos.TOP_LEFT);
            StackPane.setAlignment(idLabel, Pos.TOP_RIGHT);

            Button button = new Button();
            button.setPrefHeight(130);
            button.setPrefWidth(200);
            button.setGraphic(stackPane);

            int finalCount = count;
            button.setOnAction(event -> {
                String pokemonType = types.get(finalCount);
                String pokemonName = names.get(finalCount);
                String pokemonId = id.get(finalCount);
                String pokemonDesc = desc.get(finalCount);
                Image pokemonImage = images.get(finalCount);
                String pokemonHeight = heights.get(finalCount);
                String pokemonWeight = weights.get(finalCount);
                Integer pokemonFavs = favourites.get(finalCount);

                // Generate the details scene for the selected Pokémon
                Scene detailsScene = createDetailsScene(pokemonName, pokemonType, pokemonId, pokemonDesc, pokemonImage, pokemonHeight, pokemonWeight, pokemonFavs);

                // Set the newly generated scene as the scene for the primary stage
                secondaryStage.setScene(detailsScene);
                secondaryStage.setTitle("Details");
                secondaryStage.show();
            });

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

            currentRow.getChildren().add(button);

            if (count % 5 == 0 || images.indexOf(image) == images.size() - 1) {
                root.getChildren().add(currentRow);
                currentRow = new HBox(10);
            }
        }

        // Set up the JavaFX Scene
        primaryStage.setTitle("Image Buttons from Database");
        primaryStage.setScene(new Scene(root)); // Set initial scene size
        primaryStage.show();
    }

    private Scene createDetailsScene(String name, String type1, String id, String desc, Image image, String height, String weight, Integer fav) {
        VBox container = new VBox(30);
        HBox overall = new HBox(20);
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER_LEFT);
        container.setAlignment(Pos.CENTER);
        if(Objects.equals(type1, "Grass"))
            container.setStyle("-fx-background-color: #50d4b4; -fx-padding: 0 0 0 20;");
        if(Objects.equals(type1, "Fire"))
            container.setStyle("-fx-background-color: #ff6c6c; -fx-padding: 0 0 0 20;");
        if(Objects.equals(type1, "Water"))
            container.setStyle("-fx-background-color: #78bcfc; -fx-padding: 0 0 0 20;");
        if(Objects.equals(type1, "Ground"))
            container.setStyle("-fx-background-color: #e0bc74; -fx-padding: 0 0 0 20;");
        if(Objects.equals(type1, "Normal"))
            container.setStyle("-fx-background-color: #b0a47c; -fx-padding: 0 0 0 20;");
        if(Objects.equals(type1, "Fighting"))
            container.setStyle("-fx-background-color: #b83c34; -fx-padding: 0 0 0 20;");
        if(Objects.equals(type1, "Flying"))
            container.setStyle("-fx-background-color: #a894cc; -fx-padding: 0 0 0 20;");
        if(Objects.equals(type1, "Poison"))
            container.setStyle("-fx-background-color: #984c94; -fx-padding: 0 0 0 20;");
        if(Objects.equals(type1, "Electric"))
            container.setStyle("-fx-background-color: #f8d454; -fx-padding: 0 0 0 20;");
        if(Objects.equals(type1, "Psychic"))
            container.setStyle("-fx-background-color: #e8648c; -fx-padding: 0 0 0 20;");
        if(Objects.equals(type1, "Rock"))
            container.setStyle("-fx-background-color: #b8a44c; -fx-padding: 0 0 0 20;");
        if(Objects.equals(type1, "Ice"))
            container.setStyle("-fx-background-color: #a8d4dc; -fx-padding: 0 0 0 20;");
        if(Objects.equals(type1, "Bug"))
            container.setStyle("-fx-background-color: #b0b444; -fx-padding: 0 0 0 20;");
        if(Objects.equals(type1, "Dragon"))
            container.setStyle("-fx-background-color: #544ca0; -fx-padding: 0 0 0 20;");
        if(Objects.equals(type1, "Ghost"))
            container.setStyle("-fx-background-color: #705c94; -fx-padding: 0 0 0 20;");
        if(Objects.equals(type1, "Dark"))
            container.setStyle("-fx-background-color: #705c4c; -fx-padding: 0 0 0 20;");
        if(Objects.equals(type1, "Steel"))
            container.setStyle("-fx-background-color: #c0bccc; -fx-padding: 0 0 0 20;");

        Label nameLabel = new Label("Name: " + name);
        Label typeLabel = new Label("Type: " + type1);
        Label idLabel = new Label("ID: " + id);
        Label heightLabel = new Label("Height: " + height);
        Label weightLabel = new Label("Weight: " + weight);
        Text descText = new Text("Description: " + desc);
        descText.setWrappingWidth(380);

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);

        Button favButton = new Button("Favourite");

        favButton.setOnAction(event ->{
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:/D:\\CSE\\2-2\\CSE 4402\\PokeDex\\src\\main\\resources\\com\\example\\pokedex\\database.db")) {
                PreparedStatement pstmt = conn.prepareStatement("UPDATE Pokemons SET isFavourite = 1 WHERE Name = ?");
                pstmt.setString(1, name);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        layout.getChildren().addAll(nameLabel, typeLabel, idLabel, heightLabel, weightLabel, descText);
        overall.getChildren().addAll(layout, imageView);
        container.getChildren().addAll(overall, favButton);
        return new Scene(container);
    }



    public static void main(String[] args) {
        launch(args);
    }
}
