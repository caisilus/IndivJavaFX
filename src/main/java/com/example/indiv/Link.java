package com.example.indiv;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Link extends VBox {
    @FXML
    private HBox mainContainer;
    @FXML
    private Label nameLabel;
    @FXML
    private Label linkLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label categoryLabel;

    String name;
    String link;
    String description;
    String category;

    public Link() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("link.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(Link.this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        initElements();
    }

    void initElements() {
        mainContainer.onMouseClickedProperty().set(event -> toggleExpansion());
    }

    void updateCategoryColor() {
        categoryLabel.setStyle("-fx-background-color: #" + RandomColor.getColorFromString(this.category).toString().substring(2));
    }

    void toggleExpansion() {
        boolean expanded = this.descriptionLabel.visibleProperty().get();
        this.descriptionLabel.setVisible(!expanded);
        this.descriptionLabel.setManaged(!expanded);
    }

    public String getName() {
       return name;
    }

    public void setName(String text) {
        name = text;
        nameLabel.setText(text);
    }

    public String getLink() {
        return link;
    }

    public void setLink(String text) {
        link = text;
        linkLabel.setText(text);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.descriptionLabel.setText(description);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
        this.categoryLabel.setText(category);
        this.updateCategoryColor();
    }

    public String serialize() {
        return "!^" + this.name + ";" + this.link + ";" + this.category + ";" + "\n" + this.description;
    }

    public void deserialize(List<String> lines) {
        String[] tokens = lines.get(0).substring(2).split(";");
        this.setName(tokens[0]);
        this.setLink(tokens[1]);
        this.setCategory(tokens[2]);
        String description = lines.stream().skip(1).collect(Collectors.joining("\n"));
        this.setDescription(description);
    }
}
