package com.example.pokedex;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class ImageButtonsFromDatabase extends Application {

    Stage secondaryStage = new Stage();
    Stage nameSearchStage = new Stage();

    @Override
    public void start(Stage primaryStage) {
        List<Image> images = new ArrayList<>();
        List<String> types = new ArrayList<>();
        List<String> types2 = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<String> id = new ArrayList<>();
        List<String> desc = new ArrayList<>();
        List<String> heights = new ArrayList<>();
        List<String> weights = new ArrayList<>();
        List<Integer> favourites = new ArrayList<>();
        List<Image> gifs = new ArrayList<>();
        List<Integer> evolutions = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:/C:\\CSE\\2-2\\CSE 4402\\PokeDex\\src\\main\\java\\com\\example\\pokedex\\database.db")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Image FROM Pokemons");
            while (rs.next()) {
                byte[] imageData = rs.getBytes("Image");
                Image image = new Image(new ByteArrayInputStream(imageData));
                images.add(image);

            }

            rs = stmt.executeQuery("SELECT Gif FROM Pokemons");
            while (rs.next()) {
                byte[] imageData = rs.getBytes("Gif");
                Image gif = new Image(new ByteArrayInputStream(imageData));
                gifs.add(gif);

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

            rs = stmt.executeQuery("SELECT Evolution_Id FROM Pokemons");
            while (rs.next()) {
                String temp = rs.getString("Evolution_Id");
                if(rs.wasNull())
                    evolutions.add(-1);
                else
                    evolutions.add(Integer.valueOf(temp));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        VBox root = new VBox(5);
        root.setStyle("-fx-padding: 0 10 0 10");

        HBox currentRow = new HBox(10);

        HBox buttonsContainer = new HBox(30);
        //buttonsContainer.setAlignment(Pos.CENTER);

        Button searchName = new Button("Search by Name");
        Button searchType = new Button("Search by Type");
        Button favButton = new Button("Show Favourites");

        ImageView ballImage = new ImageView("file:///C:/CSE/2-2/CSE%204402/PokeDex/src/main/resources/com/example/pokedex/Images/Pokeball.png");
        ballImage.setFitHeight(100);
        ballImage.setFitWidth(100);
        ballImage.setStyle("-fx-padding: 0 0 0 30");

        ImageView ballImage2 = new ImageView("file:///C:/CSE/2-2/CSE%204402/PokeDex/src/main/resources/com/example/pokedex/Images/Pokeball.png");
        ballImage2.setFitHeight(100);
        ballImage2.setFitWidth(100);
        ballImage2.setStyle("-fx-padding: 0 30 0 0");

        buttonsContainer.getChildren().addAll(searchName, searchType, favButton);
        buttonsContainer.setStyle("-fx-padding: 40 0 20 0; ");
        buttonsContainer.setAlignment(Pos.CENTER);

        StackPane heading = new StackPane();
        heading.setStyle("-fx-background-color: #ff6c6c; -fx-background-radius: 10");
        heading.getChildren().addAll(ballImage, buttonsContainer, ballImage2);
        StackPane.setAlignment(ballImage, Pos.CENTER_LEFT);
        StackPane.setAlignment(buttonsContainer, Pos.CENTER);
        StackPane.setAlignment(ballImage2, Pos.CENTER_RIGHT);

        //AnchorPane headingContainer = new AnchorPane();
        //headingContainer.setMaxHeight(40);
        //headingContainer.setMaxWidth(40);
        //headingContainer.getChildren().add(headingContainer);

        root.getChildren().add(heading);

        searchName.setOnAction(event -> {
            HBox textContainer = new HBox(1);
            textContainer.setAlignment(Pos.CENTER);
            TextField searchField = new TextField();
            searchField.setPromptText("Search Pokemon");

            Button searchButton = new Button("Search");

            textContainer.getChildren().addAll(searchField, searchButton);
            VBox nameSearchContainer = new VBox(20);
            //nameSearchContainer.getChildren().addAll(searchField, searchButton);
            nameSearchContainer.setStyle("-fx-padding: 50");
            nameSearchContainer.setAlignment(Pos.CENTER);

            ImageView detPic = new ImageView("file:///C:/CSE/2-2/CSE%204402/PokeDex/src/main/resources/com/example/pokedex/Images/Detective.png");
            detPic.setFitHeight(400);
            detPic.setFitWidth(350);

            StackPane picStacker = new StackPane(detPic, textContainer);

            Scene nameSearch = new Scene(picStacker, 400, 350);
            nameSearchStage.setScene(nameSearch);
            nameSearchStage.setTitle("Search By Name");
            nameSearchStage.show();

            searchButton.setOnAction(event2 -> {
                String searchTerm = searchField.getText();

                for(int i=0; i<names.size(); i++) {
                    if(Objects.equals(names.get(i), searchTerm))
                    {
                        ImageView imageView = new ImageView(images.get(i));
                        imageView.setFitWidth(80);
                        imageView.setFitHeight(80);

                        Label labelT1 = new Label(types.get(i));
                        labelT1.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-padding: 5px; -fx-background-radius: 30; -fx-text-fill: white; -fx-font-weight: bold;");
                        labelT1.setMaxWidth(60);
                        labelT1.setAlignment(Pos.CENTER);

                        Label labelT2 = new Label(types2.get(i));
                        if(types2.get(i) != null)
                            labelT2.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-padding: 5px; -fx-background-radius: 30; -fx-text-fill: white; -fx-font-weight: bold;");
                        labelT2.setMaxWidth(60);
                        labelT2.setAlignment(Pos.CENTER);

                        Label nameLabel = new Label(names.get(i));
                        nameLabel.setStyle("-fx-background-color: rgba(255, 255, 255, 0.0); -fx-padding: 5px; -fx-background-radius: 30; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13pt;");
                        nameLabel.setAlignment(Pos.CENTER);

                        Label idLabel = new Label("#" + id.get(i));
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
                        button.setStyle("-fx-padding: 50");
                        button.setOnMouseEntered(e -> {
                            button.setScaleX(1.1); // Increase scale on X-axis
                            button.setScaleY(1.1); // Increase scale on Y-axis
                        });

                        button.setOnMouseExited(e -> {
                            button.setScaleX(1.0); // Reset scale on X-axis
                            button.setScaleY(1.0); // Reset scale on Y-axis
                        });



                        int finalI = i;
                        button.setOnAction(event3 -> {
                            String pokemonType = types.get(finalI);
                            String pokemonName = names.get(finalI);
                            String pokemonId = id.get(finalI);
                            String pokemonDesc = desc.get(finalI);
                            String pokemonHeight = heights.get(finalI);
                            String pokemonWeight = weights.get(finalI);
                            Image pokemonGifs = gifs.get(finalI);
                            Integer pokemonEvo = evolutions.get(finalI);
                            Integer pokemonFav = favourites.get(finalI);

                            Scene detailsScene = createDetailsScene(pokemonName, pokemonType, pokemonId, pokemonDesc, pokemonHeight, pokemonWeight, pokemonGifs, pokemonEvo, pokemonFav);

                            secondaryStage.setScene(detailsScene);
                            secondaryStage.setTitle("Details");
                            secondaryStage.show();

                        });

                        if(Objects.equals(types.get(i), "Grass"))
                            button.setStyle("-fx-background-color: #50d4b4; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Fire"))
                            button.setStyle("-fx-background-color: #ff6c6c; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Water"))
                            button.setStyle("-fx-background-color: #78bcfc; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Ground"))
                            button.setStyle("-fx-background-color: #e0bc74; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Normal"))
                            button.setStyle("-fx-background-color: #b0a47c; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Fighting"))
                            button.setStyle("-fx-background-color: #b83c34; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Flying"))
                            button.setStyle("-fx-background-color: #a894cc; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Poison"))
                            button.setStyle("-fx-background-color: #984c94; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Electric"))
                            button.setStyle("-fx-background-color: #f8d454; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Psychic"))
                            button.setStyle("-fx-background-color: #e8648c; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Rock"))
                            button.setStyle("-fx-background-color: #b8a44c; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Ice"))
                            button.setStyle("-fx-background-color: #a8d4dc; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Bug"))
                            button.setStyle("-fx-background-color: #b0b444; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Dragon"))
                            button.setStyle("-fx-background-color: #544ca0; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Ghost"))
                            button.setStyle("-fx-background-color: #705c94; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Dark"))
                            button.setStyle("-fx-background-color: #705c4c; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Steel"))
                            button.setStyle("-fx-background-color: #c0bccc; -fx-background-radius: 15;");

                        nameSearchContainer.getChildren().addAll(textContainer, button);
                        picStacker.getChildren().add(nameSearchContainer);
                    }
                }
            });
        });

        searchType.setOnAction(event -> {
            HBox textContainer = new HBox(1);
            textContainer.setAlignment(Pos.CENTER);

            TextField searchField = new TextField();
            searchField.setPromptText("Search Pokemon");

            Button searchButton = new Button("Search");

            textContainer.getChildren().addAll(searchField, searchButton);
            VBox nameSearchContainer = new VBox(20);
            nameSearchContainer.setStyle("-fx-padding: 50");
            nameSearchContainer.setAlignment(Pos.CENTER);

            ImageView detPic = new ImageView("file:///C:/CSE/2-2/CSE%204402/PokeDex/src/main/resources/com/example/pokedex/Images/Detective.png");
            detPic.setFitHeight(400);
            detPic.setFitWidth(350);

            StackPane picStacker = new StackPane(detPic, textContainer);

            Scene nameSearch = new Scene(picStacker, 400, 350);
            nameSearchStage.setScene(nameSearch);
            nameSearchStage.setTitle("Search");
            nameSearchStage.show();

            searchButton.setOnAction(event2 -> {
                String searchTerm = searchField.getText();
                VBox buttonRow = new VBox(10);
               // Scene nameSearched = new Scene(buttonRow, 400, 350);

                for(int i=0; i<types.size(); i++) {
                    if(Objects.equals(types.get(i), searchTerm))
                    {
                        ImageView imageView = new ImageView(images.get(i));
                        imageView.setFitWidth(80);
                        imageView.setFitHeight(80);

                        Label labelT1 = new Label(types.get(i));
                        labelT1.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-padding: 5px; -fx-background-radius: 30; -fx-text-fill: white; -fx-font-weight: bold;");
                        labelT1.setMaxWidth(60);
                        labelT1.setAlignment(Pos.CENTER);

                        Label labelT2 = new Label(types2.get(i));
                        if(types2.get(i) != null)
                            labelT2.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-padding: 5px; -fx-background-radius: 30; -fx-text-fill: white; -fx-font-weight: bold;");
                        labelT2.setMaxWidth(60);
                        labelT2.setAlignment(Pos.CENTER);

                        Label nameLabel = new Label(names.get(i));
                        nameLabel.setStyle("-fx-background-color: rgba(255, 255, 255, 0.0); -fx-padding: 5px; -fx-background-radius: 30; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13pt;");
                        nameLabel.setAlignment(Pos.CENTER);

                        Label idLabel = new Label("#" + id.get(i));
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
                        button.setStyle("-fx-padding: 50");
                        button.setOnMouseEntered(e -> {
                            button.setScaleX(1.1);
                            button.setScaleY(1.1);
                        });

                        button.setOnMouseExited(e -> {
                            button.setScaleX(1.0);
                            button.setScaleY(1.0);
                        });

                        int finalI = i;
                        button.setOnAction(event3 -> {
                            String pokemonType = types.get(finalI);
                            String pokemonName = names.get(finalI);
                            String pokemonId = id.get(finalI);
                            String pokemonDesc = desc.get(finalI);
                            String pokemonHeight = heights.get(finalI);
                            String pokemonWeight = weights.get(finalI);
                            Image pokemonGifs = gifs.get(finalI);
                            Integer pokemonEvo = evolutions.get(finalI);
                            Integer pokemonFav = favourites.get(finalI);

                            Scene detailsScene = createDetailsScene(pokemonName, pokemonType, pokemonId, pokemonDesc, pokemonHeight, pokemonWeight, pokemonGifs, pokemonEvo, pokemonFav);

                            secondaryStage.setScene(detailsScene);
                            secondaryStage.setTitle("Details");
                            secondaryStage.show();

                        });

                        if(Objects.equals(types.get(i), "Grass"))
                            button.setStyle("-fx-background-color: #50d4b4; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Fire"))
                            button.setStyle("-fx-background-color: #ff6c6c; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Water"))
                            button.setStyle("-fx-background-color: #78bcfc; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Ground"))
                            button.setStyle("-fx-background-color: #e0bc74; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Normal"))
                            button.setStyle("-fx-background-color: #b0a47c; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Fighting"))
                            button.setStyle("-fx-background-color: #b83c34; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Flying"))
                            button.setStyle("-fx-background-color: #a894cc; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Poison"))
                            button.setStyle("-fx-background-color: #984c94; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Electric"))
                            button.setStyle("-fx-background-color: #f8d454; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Psychic"))
                            button.setStyle("-fx-background-color: #e8648c; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Rock"))
                            button.setStyle("-fx-background-color: #b8a44c; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Ice"))
                            button.setStyle("-fx-background-color: #a8d4dc; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Bug"))
                            button.setStyle("-fx-background-color: #b0b444; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Dragon"))
                            button.setStyle("-fx-background-color: #544ca0; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Ghost"))
                            button.setStyle("-fx-background-color: #705c94; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Dark"))
                            button.setStyle("-fx-background-color: #705c4c; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Steel"))
                            button.setStyle("-fx-background-color: #c0bccc; -fx-background-radius: 15;");

                        buttonRow.getChildren().add(button);

                    }

                    ScrollPane scrollPane = new ScrollPane(buttonRow);
                    scrollPane.setFitToWidth(true);

                    VBox container = new VBox(20);
                    container.getChildren().addAll(textContainer, scrollPane);

                    picStacker.getChildren().addAll(container);

                }
            });
        });

        favButton.setOnAction(event -> {
            VBox nameSearchContainer = new VBox(20);
            //nameSearchContainer.getChildren().addAll(searchField, searchButton);
            nameSearchContainer.setStyle("-fx-padding: 50");
            nameSearchContainer.setAlignment(Pos.CENTER);
                VBox buttonRow = new VBox(10);
                // Scene nameSearched = new Scene(buttonRow, 400, 350);

                for(int i=0; i<types.size(); i++) {
                    if(Objects.equals(favourites.get(i), 1))
                    {
                        System.out.println(names.get(i));
                        ImageView imageView = new ImageView(images.get(i));
                        imageView.setFitWidth(80); // Set the width of the ImageView
                        imageView.setFitHeight(80); // Set the height of the ImageView

                        Label labelT1 = new Label(types.get(i));
                        labelT1.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-padding: 5px; -fx-background-radius: 30; -fx-text-fill: white; -fx-font-weight: bold;");
                        labelT1.setMaxWidth(60);
                        labelT1.setAlignment(Pos.CENTER);

                        Label labelT2 = new Label(types2.get(i));
                        if(types2.get(i) != null)
                            labelT2.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-padding: 5px; -fx-background-radius: 30; -fx-text-fill: white; -fx-font-weight: bold;");
                        labelT2.setMaxWidth(60);
                        labelT2.setAlignment(Pos.CENTER);

                        Label nameLabel = new Label(names.get(i));
                        nameLabel.setStyle("-fx-background-color: rgba(255, 255, 255, 0.0); -fx-padding: 5px; -fx-background-radius: 30; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13pt;");
                        nameLabel.setAlignment(Pos.CENTER);

                        Label idLabel = new Label("#" + id.get(i));
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
                        button.setStyle("-fx-padding: 50");
                        button.setOnMouseEntered(e -> {
                            button.setScaleX(1.1);
                            button.setScaleY(1.1);
                        });

                        button.setOnMouseExited(e -> {
                            button.setScaleX(1.0);
                            button.setScaleY(1.0);
                        });

                        int finalI = i;
                        button.setOnAction(event3 -> {
                            String pokemonType = types.get(finalI);
                            String pokemonName = names.get(finalI);
                            String pokemonId = id.get(finalI);
                            String pokemonDesc = desc.get(finalI);
                            String pokemonHeight = heights.get(finalI);
                            String pokemonWeight = weights.get(finalI);
                            Image pokemonGifs = gifs.get(finalI);
                            Integer pokemonEvo = evolutions.get(finalI);
                            Integer pokemonFav = favourites.get(finalI);

                            Scene detailsScene = createDetailsScene(pokemonName, pokemonType, pokemonId, pokemonDesc, pokemonHeight, pokemonWeight, pokemonGifs, pokemonEvo, pokemonFav);

                            secondaryStage.setScene(detailsScene);
                            secondaryStage.setTitle("Details");
                            secondaryStage.show();

                        });

                        if(Objects.equals(types.get(i), "Grass"))
                            button.setStyle("-fx-background-color: #50d4b4; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Fire"))
                            button.setStyle("-fx-background-color: #ff6c6c; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Water"))
                            button.setStyle("-fx-background-color: #78bcfc; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Ground"))
                            button.setStyle("-fx-background-color: #e0bc74; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Normal"))
                            button.setStyle("-fx-background-color: #b0a47c; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Fighting"))
                            button.setStyle("-fx-background-color: #b83c34; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Flying"))
                            button.setStyle("-fx-background-color: #a894cc; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Poison"))
                            button.setStyle("-fx-background-color: #984c94; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Electric"))
                            button.setStyle("-fx-background-color: #f8d454; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Psychic"))
                            button.setStyle("-fx-background-color: #e8648c; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Rock"))
                            button.setStyle("-fx-background-color: #b8a44c; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Ice"))
                            button.setStyle("-fx-background-color: #a8d4dc; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Bug"))
                            button.setStyle("-fx-background-color: #b0b444; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Dragon"))
                            button.setStyle("-fx-background-color: #544ca0; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Ghost"))
                            button.setStyle("-fx-background-color: #705c94; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Dark"))
                            button.setStyle("-fx-background-color: #705c4c; -fx-background-radius: 15;");
                        if(Objects.equals(types.get(i), "Steel"))
                            button.setStyle("-fx-background-color: #c0bccc; -fx-background-radius: 15;");

                        buttonRow.getChildren().add(button);

                    }

                    ScrollPane scrollPane = new ScrollPane(buttonRow);
                    scrollPane.setFitToWidth(true);

                    Scene nameSearched = new Scene(scrollPane,400, 350);
                    nameSearchStage.setScene(nameSearched);
                    nameSearchStage.setTitle("Search");
                    nameSearchStage.show();

                }
        });

        int count = 0;
        for (Image image : images) {
            //System.out.println(types.get(count));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(80);
            imageView.setFitHeight(80);

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
            button.setOnMouseEntered(e -> {
                button.setScaleX(1.1);
                button.setScaleY(1.1);
            });

            button.setOnMouseExited(e -> {
                button.setScaleX(1.0);
                button.setScaleY(1.0);
            });

            int finalCount = count;
            button.setOnAction(event -> {
                String pokemonType = types.get(finalCount);
                String pokemonName = names.get(finalCount);
                String pokemonId = id.get(finalCount);
                String pokemonDesc = desc.get(finalCount);
                String pokemonHeight = heights.get(finalCount);
                String pokemonWeight = weights.get(finalCount);
                Image pokemonGif = gifs.get(finalCount);
                Integer pokemonEvo = evolutions.get(finalCount);
                Integer pokemonFav = favourites.get(finalCount);

                Scene detailsScene = createDetailsScene(pokemonName, pokemonType, pokemonId, pokemonDesc, pokemonHeight, pokemonWeight, pokemonGif, pokemonEvo, pokemonFav);

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

        primaryStage.setTitle("Image Buttons from Database");
        primaryStage.setScene(new Scene(root)); // Set initial scene size
        primaryStage.show();
    }

    private Scene createDetailsScene(String name, String type1, String id, String desc, String height, String weight, Image gif, Integer evo, Integer fav) {
        VBox container = new VBox(30);
        HBox overall = new HBox(20);
        VBox layout = new VBox(10);
        VBox layout2 = new VBox(10);
        HBox overall2 = new HBox(20);
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

        Text nameLabel = new Text("Name: " + name);
        Text typeLabel = new Text("Type: " + type1);
        Text idLabel = new Text("ID: " + id);
        Text heightLabel = new Text("Height: " + height);
        Text weightLabel = new Text("Weight: " + weight);
        Text descText = new Text("Description: " + desc);
        descText.setWrappingWidth(380);

        if(evo != -1)
        {
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:/C:\\CSE\\2-2\\CSE 4402\\PokeDex\\src\\main\\java\\com\\example\\pokedex\\database.db")){
                PreparedStatement statement = conn.prepareStatement("SELECT Name FROM Pokemons WHERE P_ID = ?");
                statement.setString(1, String.valueOf(evo));
                ResultSet rs = statement.executeQuery();
                String evoName = rs.getString("Name");

                PreparedStatement statement2 = conn.prepareStatement("SELECT Gif FROM Pokemons WHERE P_ID = ?");
                statement2.setString(1, String.valueOf(evo));
                ResultSet rs2 = statement2.executeQuery();
                byte[] gifData = rs2.getBytes("Gif");
                Image evoGif = new Image(new ByteArrayInputStream(gifData));

                PreparedStatement statement3 = conn.prepareStatement("SELECT Description FROM Pokemons WHERE P_ID = ?");
                statement3.setString(1, String.valueOf(evo));
                ResultSet rs3 = statement3.executeQuery();
                String evoDesc = rs3.getString("Description");

                Text evoStatus = new Text("Evolved Form:");
                Text evoNameLabel = new Text("Name: " + evoName);
                Text evodescText = new Text("Description: " + desc);
                evodescText.setWrappingWidth(380);
                layout2.getChildren().addAll(evoStatus, evoNameLabel,evodescText);

                ImageView evoImage = new ImageView(evoGif);
                evoImage.setFitWidth(200);
                evoImage.setFitHeight(200);
                overall2.getChildren().addAll(layout2, evoImage);

            }catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Text evoStatus = new Text("There is no Evolved Form");
            overall2.getChildren().add(evoStatus);
        }

        ImageView imageView = new ImageView(gif);
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);

        Button favButton = new Button();
        favButton.setStyle("-fx-padding: 0 0 0 10; -fx-background-color: red; -fx-shape: \"M23.6,0c-3.4,0-6.3,2.7-7.6,5.6C14.7,2.7,11.8,0,8.4,0C3.8,0,0,3.8,0,8.4c0,9.4,9.5,11.9,16,21.2c6.1-9.3,16-12.1,16-21.2C32,3.8,28.2,0,23.6,0z\";");
        favButton.setMaxSize(31.25, 31.25);
        favButton.setOnMouseEntered(e -> {
            favButton.setScaleX(1.1);
            favButton.setScaleY(1.1);
        });

        favButton.setOnMouseExited(e -> {
            favButton.setScaleX(1.0);
            favButton.setScaleY(1.0);
        });

        StackPane favContainer = new StackPane();
        favContainer.getChildren().addAll(favButton, imageView);
        StackPane.setAlignment(imageView, Pos.CENTER);
        StackPane.setAlignment(favButton, Pos.TOP_LEFT);

        favButton.setOnAction(event ->{
            if (fav == 0) {
                try (Connection conn = DriverManager.getConnection("jdbc:sqlite:/C:\\CSE\\2-2\\CSE 4402\\PokeDex\\src\\main\\java\\com\\example\\pokedex\\database.db")) {
                    PreparedStatement pstmt = conn.prepareStatement("UPDATE Pokemons SET isFavourite = 1 WHERE Name = ?");
                    pstmt.setString(1, name);
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                try (Connection conn = DriverManager.getConnection("jdbc:sqlite:/C:\\CSE\\2-2\\CSE 4402\\PokeDex\\src\\main\\java\\com\\example\\pokedex\\database.db")) {
                    PreparedStatement pstmt = conn.prepareStatement("UPDATE Pokemons SET isFavourite = 0 WHERE Name = ?");
                    pstmt.setString(1, name);
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        layout.getChildren().addAll(nameLabel, typeLabel, idLabel, heightLabel, weightLabel, descText);
        overall.getChildren().addAll(layout, favContainer);
        container.getChildren().addAll(overall, overall2);
        return new Scene(container);
    }



    public static void main(String[] args) {
        launch(args);
    }
}
