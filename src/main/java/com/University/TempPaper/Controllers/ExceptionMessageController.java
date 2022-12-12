package com.University.TempPaper.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class ExceptionMessageController extends Editor {
    public static String exceptionMessage;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exitButton;

    @FXML
    private ListView<String> listViewExceptionMessage;

    @FXML
    void initialize() {
        ObservableList<String> items = FXCollections.observableArrayList ();
        items.add(exceptionMessage);
        listViewExceptionMessage.setItems(items);

        exitButton.setOnAction(event -> {
            exitButton.getScene().getWindow().hide();
        });
    }

    public static void start() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ExceptionMessageController.class.getResource("/com.University.TempPaper/ExceptionMessage.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            ExceptionMessageController.exceptionMessage = e.getMessage();
            ExceptionMessageController.start();
            throw new RuntimeException(e);
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

}
