package com.example.indiv;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewLinkController {
    @FXML
    TextField nameField;
    @FXML
    TextField linkField;
    @FXML
    TextField categoryField;
    @FXML
    TextArea descriptionTextArea;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void handleSubmit() {
        mainController.addLink(createLink());
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    private Link createLink() {
        Link link = new Link();

        String name = nameField.getText();
        link.setName(name);

        String linkStr = linkField.getText();
        link.setLink(linkStr);

        String category = categoryField.getText();
        link.setCategory(category);

        String description = descriptionTextArea.getText();
        link.setDescription(description);

        return link;
    }
}
