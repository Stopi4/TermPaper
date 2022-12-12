package com.University.TempPaper.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import com.University.TempPaper.Commands.DeleteCompositionGenreCommand;
import com.University.TempPaper.Commands.UpdateCompositionByIdCommand;
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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateCompositionController extends Editor{
    Editor editor = this;
    private Composition composition;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> listViewOfGenreName;

    @FXML
    private TextField textFieldAssemblageName;

    @FXML
    private TextField textFieldCompositionDuration;

    @FXML
    private TextField textFieldCompositionName;

    @FXML
    private TextField textFieldCompositionPerformer;

    @FXML
    private Button updateButton;

    @FXML
    private Button addGenreButton;

    @FXML
    private Button deleteGenreButton;


    @FXML
    void initialize() {
        composition = SelectCompositionForUpdateController.composition;
        textFieldAssemblageName.setText(composition.getAssemblageName());
        textFieldCompositionName.setText(composition.getName());
        textFieldCompositionDuration.setText(String.valueOf(composition.getDuration()));
        textFieldCompositionPerformer.setText(composition.getPerformer());

        ObservableList<String> genreItems = FXCollections.observableArrayList();
        List<String> genres = composition.getGenres();
        for (String genre : genres)
            genreItems.add(genre);
        listViewOfGenreName.setItems(genreItems);

        updateButton.setOnAction(event -> {
            try {
                if (textFieldAssemblageName.getText().equals(""))
                    throw new VariableIsNull("Поле ім'я збірки є пустим, заповніть його!");
                if (textFieldCompositionName.getText().equals(""))
                    throw new VariableIsNull("Поле ім'я є пустим, заповніть його!");
                if (textFieldCompositionDuration.getText().equals(""))
                    throw new VariableIsNull("Поле ім'я тривалості є пустим, заповніть його!");
                if (equals(textFieldCompositionPerformer.getText().equals("")))
                    throw new VariableIsNull("Поле виконавця є пустим, заповніть його!");


                composition.setName(textFieldCompositionName.getText());
                composition.setAssemblageName(textFieldAssemblageName.getText());
                composition.setDuration(Double.parseDouble(textFieldCompositionDuration.getText()));
                composition.setPerformer(textFieldCompositionPerformer.getText());


                executeCommand(new UpdateCompositionByIdCommand(editor, composition));
                ExceptionMessageController.exceptionMessage = "Композиція оновлена успішно!";
                ExceptionMessageController.start();
            } catch (StatementDontReturnValueException ignored) {
            } catch (VariableIsNull | ZeroRowChangedException e) {
                ExceptionMessageController.exceptionMessage = e.getMessage();
                ExceptionMessageController.start();
            }
        });

        addGenreButton.setOnAction(event2 -> {
            FXMLLoader loader = new FXMLLoader();
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
        deleteGenreButton.setOnAction(event3 -> {
            SelectionModel selectionGenreModel = listViewOfGenreName.getSelectionModel();
            if (selectionGenreModel.getSelectedItem() == null) {
                ExceptionMessageController.exceptionMessage = "Не вибрана назва збірки.";
                ExceptionMessageController.start();
            }

            try {
                executeCommand(new DeleteCompositionGenreCommand(editor, (String) selectionGenreModel.getSelectedItem(), composition.getId()));
                ObservableList<String> genreItemsForDelete = FXCollections.observableArrayList();
                listViewOfGenreName.setItems(null);
                List<String> genresForDelete = composition.getGenres();
                for (String genre : genresForDelete)
                    genreItemsForDelete.add(genre);
                genreItemsForDelete.set(selectionGenreModel.getSelectedIndex(), null);
                listViewOfGenreName.setItems(genreItemsForDelete);
                ExceptionMessageController.exceptionMessage = "Жанр композиції видалений успішно.";
                ExceptionMessageController.start();
            } catch (StatementDontReturnValueException | ZeroRowChangedException | VariableIsNull e) {
                ExceptionMessageController.exceptionMessage = "Не вибрана назва збірки.";
                ExceptionMessageController.start();
            }
        });
    }

}
