package com.University.TempPaper.Controllers;

import com.University.TempPaper.Commands.*;
import com.University.TempPaper.Exceptions.StatementDontReturnValueException;
import com.University.TempPaper.Exceptions.VariableIsNull;
import com.University.TempPaper.Exceptions.ZeroRowChangedException;
import com.University.TempPaper.Model.Composition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SelectAssemblageController extends Editor {
    private static final Logger LOG = LogManager.getLogger(SelectAssemblageController.class);
    Editor editor = this;
    @FXML
    private Button buttonCBA;

    @FXML
    private Button buttonCBD;

    @FXML
    private Button buttonCBG;

    @FXML
    private ListView<Composition> listViewOfAssemblageCBA;

    @FXML
    private ListView<Composition> listViewOfAssemblageCBD;

    @FXML
    private ListView<Composition> listViewOfAssemblageCBG;

    @FXML
    private ListView<String> listViewOfAssemblageName;

    @FXML
    private ListView<String> listViewOfGenreName;

    @FXML
    private Tab tabCBA;

    @FXML
    private Tab tabCBD;

    @FXML
    private TextField textFieldLb;

    @FXML
    private TextField textFieldUb;

    @FXML
    private Label totalDurationId;

    @FXML
    void initialize() {
        try {
            executeCommand(new SelectAssemblageNamesCommand(editor));
            ObservableList<String> assemblageNameitems = FXCollections.observableArrayList();
            for (String assemblageName : editor.assemblageNames)
                assemblageNameitems.add(assemblageName);
            listViewOfAssemblageName.setItems(assemblageNameitems);

            executeCommand(new SelectGenreNamesCommand(editor));
            ObservableList<String> genreItems = FXCollections.observableArrayList();
            for (String assemblageName : editor.getGenreNames())
                genreItems.add(assemblageName);
            listViewOfGenreName.setItems(genreItems);
        } catch (StatementDontReturnValueException | VariableIsNull e) {
            ExceptionMessageController.exceptionMessage = e.getMessage();
            ExceptionMessageController.start();
            return;
        } catch (ZeroRowChangedException ignored) {
        }


        buttonCBA.setOnAction(event -> {
            SelectionModel selectionAssemblageModel = listViewOfAssemblageName.getSelectionModel();
            if (selectionAssemblageModel.getSelectedItem() == null) {
                String message = "Не вибрана назва збірки.";
                LOG.warn(message);
                ExceptionMessageController.exceptionMessage = message;
                ExceptionMessageController.start();
            } else {
                try {
                    executeCommand(new SelectAssemblageCommand(editor, (String) selectionAssemblageModel.getSelectedItem()));

                    ObservableList<Composition> compositionsItems = FXCollections.observableArrayList();
                    for (Composition composition : editor.compositions)
                        compositionsItems.add(composition);
                    listViewOfAssemblageCBA.setItems(compositionsItems);
                    totalDurationId.setText(String.valueOf(editor.getTotalDuration()));

                } catch (StatementDontReturnValueException | VariableIsNull e) {
                    ExceptionMessageController.exceptionMessage = e.getMessage();
                    ExceptionMessageController.start();
                    return;
                } catch (ZeroRowChangedException ignored) {
                }
            }
        });

        buttonCBD.setOnAction(event -> {
            try {
                String message;
                if(textFieldLb.getText() == "") {
                    message = "Поле мінімальної тривалості є пустим!";
                }
                else if(textFieldUb.getText() == "") {
                    message = "Поле максимальної тривалості є пустим!";
                } else {
                    executeCommand(new SelectCompositionsByDurationCommand(editor,
                            Double.parseDouble(textFieldLb.getText()), Double.parseDouble(textFieldUb.getText())));

                    ObservableList<Composition> compositionsItems = FXCollections.observableArrayList();
                    for (Composition composition : editor.compositions)
                        compositionsItems.add(composition);
                    listViewOfAssemblageCBD.setItems(compositionsItems);
                    return;
                }
                LOG.warn(message);
                ExceptionMessageController.exceptionMessage = message;
                ExceptionMessageController.start();
            } catch (StatementDontReturnValueException | VariableIsNull e) {
                ExceptionMessageController.exceptionMessage = e.getMessage();
                ExceptionMessageController.start();
            } catch (ZeroRowChangedException ignored) {
            }
        });

        buttonCBG.setOnAction(event -> {
            SelectionModel selectionGenreModel = listViewOfGenreName.getSelectionModel();
            if (selectionGenreModel.getSelectedItem() == null) {
                String message = "Не вибраний жанр.";
                LOG.warn(message);
                ExceptionMessageController.exceptionMessage = message;
                ExceptionMessageController.start();
            } else {
                try {
                    executeCommand(new SelectCompositionsByGenreNameCommand(editor, (String) selectionGenreModel.getSelectedItem()));

                    ObservableList<Composition> compositionsItems = FXCollections.observableArrayList();
                    for (Composition composition : editor.compositions)
                        compositionsItems.add(composition);
                    listViewOfAssemblageCBG.setItems(compositionsItems);

                } catch (StatementDontReturnValueException | VariableIsNull e) {
                    ExceptionMessageController.exceptionMessage = e.getMessage();
                    ExceptionMessageController.start();
                } catch (ZeroRowChangedException ignored) {
                }
            }
        });
    }
}

