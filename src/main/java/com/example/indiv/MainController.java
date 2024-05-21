package com.example.indiv;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class MainController {
    @FXML
    VBox linksContainer;
    @FXML
    ScrollPane scrollPane;
    @FXML
    ComboBox filterCombobox;

    private HashMap<String, List<Link>> categorialFilters = new HashMap<String, List<Link>>();
    private ObservableList<String> categories = FXCollections.observableArrayList("Все");

    public void initialize() {
        scrollPane.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            linksContainer.setMinWidth(newValue.getWidth());
            linksContainer.setMaxWidth(newValue.getWidth());
        });
        loadLinks();
        filterCombobox.setItems(categories);
    }

    private void loadLinks() {
        try {
            String pathString = getClass().getResource("links.txt").getFile();
            Path path = Paths.get(pathString);
            List<String> lines = Files.readAllLines(path);
            for (List<String> group: groupLines(lines)) {
                Link link = new Link();
                link.deserialize(group);
                linksContainer.getChildren().add(link);
                updateFilters(link);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void updateFilters(Link link) {
        if (!categorialFilters.containsKey(link.category)) {
            categorialFilters.put(link.category, new ArrayList<Link>());
        }

        categorialFilters.get(link.category).add(link);
        if (!categories.contains(link.category)) {
            categories.add(link.category);
        }
    }

    private List<List<String>> groupLines(List<String> inputList) {
        List<List<String>> groupedLists = new ArrayList<>();

        List<String> currentGroup = null;
        for (String s : inputList) {
            if (s.startsWith("!^")) {
                // If the current group is not null, add it to the grouped lists
                if (currentGroup != null) {
                    groupedLists.add(currentGroup);
                }
                // Start a new group with the current string
                currentGroup = new ArrayList<>();
                currentGroup.add(s);
            } else if (currentGroup != null) {
                // Add the current string to the current group
                currentGroup.add(s);
            }
        }
        // Add the last group to the grouped lists
        if (currentGroup != null) {
            groupedLists.add(currentGroup);
        }

        return groupedLists;
    }

    @FXML
    protected void onNewLinkButton() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("new-link.fxml"));
        try {
            VBox modalRoot = loader.load();

            NewLinkController newLinkController = loader.getController();
            newLinkController.setMainController(this);

            Stage modalStage = new Stage();
            modalStage.setTitle("New Link");
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setScene(new Scene(modalRoot));
            modalStage.showAndWait();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void addLink(Link link) {
        linksContainer.getChildren().add(link);
        updateFilters(link);

        String file = getClass().getResource("links.txt").getFile();
        System.out.println(file);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            System.out.println(link.serialize());
            writer.write(link.serialize());
            writer.newLine();
            System.out.println("Serialized data is saved in myObjects.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    @FXML
    public void applyFilter() {
        linksContainer.getChildren().clear();
        String category = filterCombobox.getSelectionModel().getSelectedItem().toString();
        List<Link> links;
        if (Objects.equals(category, "Все")) {
            links = categorialFilters.values().stream().flatMap(List::stream).collect(Collectors.toList());
        } else {
            links = categorialFilters.get(category);
        }

        linksContainer.getChildren().addAll(links);
    }
}