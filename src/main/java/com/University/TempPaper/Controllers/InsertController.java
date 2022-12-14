package com.University.TempPaper.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import com.University.TempPaper.Commands.InsertCompositionCommand;
import com.University.TempPaper.Commands.SelectAssemblageNamesCommand;
import com.University.TempPaper.Commands.SelectGenreNamesCommand;
import com.University.TempPaper.Exceptions.StatementDontReturnValueException;
import com.University.TempPaper.Exceptions.VariableIsNull;
import com.University.TempPaper.Exceptions.ZeroRowChangedException;
import com.University.TempPaper.Model.Composition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.skin.ListViewSkin;
import javafx.stage.Stage;

public class InsertController extends Editor {
    public Editor editor = this;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label Assemblage;

    @FXML
    private Label Duration;

    @FXML
    private Label Genre;

    @FXML
    private Label Name;

    @FXML
    private Label lableException;

    @FXML
    private Button updateButton;

    @FXML
    private Button addAssemblageButton;

    @FXML
    private Button addCompositionToAssemblageButton;

    @FXML
    private Button addGenreButton;

    @FXML
    private ListView<String> listViewAssemblage;

    @FXML
    private ListView<String> listViewGenre;

    @FXML
    private TextField textFieldDuration;

    @FXML
    private TextField textFieldName;

    @FXML
    private TextField textFieldPerformer;

    @FXML
    void initialize() {
        listViewGenre.setSkin(new CustomListViewSkin<>(listViewGenre));
        listViewGenre.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ObservableList<String> items;
        try {
            executeCommand(new SelectAssemblageNamesCommand(editor));
            items = FXCollections.observableArrayList ();
            for(String assemblageName : editor.assemblageNames)
                items.add(assemblageName);
            listViewAssemblage.setItems(items);
        } catch (StatementDontReturnValueException | VariableIsNull | ZeroRowChangedException e) {
//            ExceptionMessageController.exceptionMessage = e.getMessage();
//            ExceptionMessageController.start();
        }

        try {
            executeCommand(new SelectGenreNamesCommand(editor));
            items = FXCollections.observableArrayList ();
            for(String genreName : editor.genreNames)
                items.add(genreName);
            listViewGenre.setItems(items);
        } catch (StatementDontReturnValueException | VariableIsNull | ZeroRowChangedException e) {
//            ExceptionMessageController.exceptionMessage = e.getMessage();
//            ExceptionMessageController.start();
        }

        addGenreButton.setOnAction(event -> {
//            SELECT.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/com.University.TempPaper/HomeWindow.fxml"));
            loader.setLocation(getClass().getResource("/com.University.TempPaper/AddGenre.fxml"));

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
        });
        addAssemblageButton.setOnAction(event -> {
//            addAssemblageButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/com.University.TempPaper/HomeWindow.fxml"));
            loader.setLocation(getClass().getResource("/com.University.TempPaper/AddAssemblageName.fxml"));

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
        });

        addCompositionToAssemblageButton.setOnAction(event -> {
            SelectionModel selectionAssemblageModel = listViewAssemblage.getSelectionModel();
            ListViewSkin<String> listViewSkin = new CustomListViewSkin<>(listViewGenre);

            SelectionModel selectionGenreModel = listViewGenre.getSelectionModel();
            if(selectionAssemblageModel.getSelectedItem() == null) {
                ExceptionMessageController.exceptionMessage = "Не вибрана збірка для композиції";
                ExceptionMessageController.start();
            }
            else if(selectionGenreModel.getSelectedItem() == null) {
                ExceptionMessageController.exceptionMessage = "Не вибрані жанри для композиції";
                ExceptionMessageController.start();
            } else {

                Composition composition = new Composition();

                try {
                    if(textFieldName.getText() == "" && textFieldPerformer.getText() == "")
                        throw new VariableIsNull("Поля є пустими, заповніть їх!");
                } catch (VariableIsNull e) {
                    ExceptionMessageController.exceptionMessage = e.getMessage();
                    ExceptionMessageController.start();
                }

                composition.setName(textFieldName.getText());
                composition.setDuration(Double.parseDouble(textFieldDuration.getText()));
            composition.setPerformer(textFieldPerformer.getText());
                composition.setAssemblageName((String) selectionAssemblageModel.getSelectedItem());

                LinkedList linkedList = new LinkedList<String>();
                linkedList.add((String) selectionGenreModel.getSelectedItem());
                composition.setGenres(linkedList);


//                AnimationTimer animationTimer = new AnimationTimer() {
//                    @Override
//                    public void handle(long l) {
//
//                    }
//                }

//            String assemblageName = (String) selectionAssemblageModel.getSelectedItem();
//            String genreName = (String) selectionGenreModel.getSelectedItem();

                try {
                    executeCommand(new InsertCompositionCommand(editor, composition));
                    ExceptionMessageController.exceptionMessage = "Композиція додана в збірку успішно!";
                    ExceptionMessageController.start();
                } catch (StatementDontReturnValueException | ZeroRowChangedException | VariableIsNull e) {
                    ExceptionMessageController.exceptionMessage = e.getMessage();
                    ExceptionMessageController.start();
                }

            }
        });

    }

}

