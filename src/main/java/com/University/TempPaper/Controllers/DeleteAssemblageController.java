package com.University.TempPaper.Controllers;

import com.University.TempPaper.Commands.DeleteAssemblageCommand;
import com.University.TempPaper.Commands.DeleteCompositionByIdCommand;
import com.University.TempPaper.Commands.SelectAssemblageCommand;
import com.University.TempPaper.Commands.SelectAssemblageNamesCommand;
import com.University.TempPaper.Exceptions.StatementDontReturnValueException;
import com.University.TempPaper.Exceptions.VariableIsNull;
import com.University.TempPaper.Exceptions.ZeroRowChangedException;
import com.University.TempPaper.Model.Composition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.Tab;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteAssemblageController extends Editor{
    Editor editor = this;
    private static final Logger LOG = LogManager.getLogger(DeleteAssemblageController.class);

    @FXML
    private Button button;

    @FXML
    private Button buttonCBA;

    @FXML
    private Button deleteButton;

    @FXML
    private ListView<String> listViewAssemblageNameForAssemblage;

    @FXML
    private ListView<String> listViewOfAssemblageNameForComposition;

    @FXML
    private ListView<Composition> listViewOfCompositions;

    @FXML
    private Tab tabCBA;

    @FXML
    private Tab tabCBD;

    @FXML
    void initialize() {
        try {
            executeCommand(new SelectAssemblageNamesCommand(editor));
            ObservableList<String> items = FXCollections.observableArrayList ();
            for(String assemblageName : editor.assemblageNames)
                items.add(assemblageName);
            listViewOfAssemblageNameForComposition.setItems(items);
            listViewAssemblageNameForAssemblage.setItems(items);
        } catch (StatementDontReturnValueException | VariableIsNull | ZeroRowChangedException e) {
            ExceptionMessageController.exceptionMessage = e.getMessage();
            ExceptionMessageController.start();
            return;
        }

        button.setOnAction(event -> {
            SelectionModel selectionAssemblageNameModel = listViewAssemblageNameForAssemblage.getSelectionModel();
            if(selectionAssemblageNameModel.getSelectedItem() == null) {
                String message = "Не вибрана збірка для композиції";
                LOG.warn(message);
                ExceptionMessageController.exceptionMessage = message;
                ExceptionMessageController.start();
            } else {
                try {
                    editor.executeCommand(new DeleteAssemblageCommand(editor, (String) selectionAssemblageNameModel.getSelectedItem()));
                    ExceptionMessageController.exceptionMessage = "Видалення збірки пройшло успішно!";
                    ExceptionMessageController.start();
                } catch (StatementDontReturnValueException ignored) {
                } catch (VariableIsNull | ZeroRowChangedException e) {
                    ExceptionMessageController.exceptionMessage = e.getMessage();
                    ExceptionMessageController.start();
                }
            }
        });

        buttonCBA.setOnAction(actionEvent -> {
            SelectionModel selectionAssemblageModel = listViewOfAssemblageNameForComposition.getSelectionModel();
            if (selectionAssemblageModel.getSelectedItem() == null) {
                String message = "Не вибрана назва збірки.";
                LOG.warn(message);
                ExceptionMessageController.exceptionMessage = message;
                ExceptionMessageController.start();
            } else {
                try {
                    try {
                        executeCommand(new SelectAssemblageCommand(editor, (String) selectionAssemblageModel.getSelectedItem()));
                    } catch (StatementDontReturnValueException ignored) {                  }

                    ObservableList<Composition> compositionItems = FXCollections.observableArrayList();
                    for (Composition composition : editor.compositions)
                        compositionItems.add(composition);
                    listViewOfCompositions.setItems(compositionItems);
                } catch (VariableIsNull e) {
                    ExceptionMessageController.exceptionMessage = e.getMessage();
                    ExceptionMessageController.start();
                    return;
                } catch (ZeroRowChangedException ignored) {
                }
            }
        });

        deleteButton.setOnAction(actionEvent ->  {
            SelectionModel selectionCompositionModel = listViewOfCompositions.getSelectionModel();
            if(selectionCompositionModel.getSelectedItem() == null) {
                String message = "Не вибрана збірка для композиції";
                LOG.warn(message);
                ExceptionMessageController.exceptionMessage = message;
                ExceptionMessageController.start();
            } else {
                try {
                    Composition composition = (Composition) selectionCompositionModel.getSelectedItem();
                    editor.executeCommand(new DeleteCompositionByIdCommand(editor, composition.getId()));
                    ExceptionMessageController.exceptionMessage = "Видалення композиції пройшло успішно!";
                    ExceptionMessageController.start();
                } catch (StatementDontReturnValueException ignored) {
                } catch (VariableIsNull | ZeroRowChangedException e) {
                    ExceptionMessageController.exceptionMessage = e.getMessage();
                    ExceptionMessageController.start();
                }
            }
        });
    }
}
