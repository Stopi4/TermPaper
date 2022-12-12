package com.University.TempPaper.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.University.TempPaper.Commands.SelectAssemblageCommand;
import com.University.TempPaper.Commands.SelectAssemblageNamesCommand;
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

public class SelectCompositionForUpdateController extends Editor {
    Editor editor = this;
    public static Composition composition;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button button;

    @FXML
    private ListView<Composition> listViewOfAssemblage;

    @FXML
    private ListView<String> listViewOfAssemblageName;

    @FXML
    private TextField textField;

    @FXML
    private Button updateButton;

    @FXML
    void initialize() {
        try {
            executeCommand(new SelectAssemblageNamesCommand(editor));
        } catch (StatementDontReturnValueException | VariableIsNull e) {
            ExceptionMessageController.exceptionMessage = e.getMessage();
            ExceptionMessageController.start();
            return;
        } catch (ZeroRowChangedException ignored) {
        }
        ObservableList<String> assemblageNameItems = FXCollections.observableArrayList ();
        for(String assemblageName : editor.assemblageNames)
            assemblageNameItems.add(assemblageName);
        listViewOfAssemblageName.setItems(assemblageNameItems);

        button.setOnAction(event -> {
            SelectionModel selectionAssemblageModel = listViewOfAssemblageName.getSelectionModel();
            if(selectionAssemblageModel.getSelectedItem() == null) {
                ExceptionMessageController.exceptionMessage = "Не вибрана назва збірки.";
                ExceptionMessageController.start();
            } else {
                try {
                    executeCommand(new SelectAssemblageCommand(editor, (String) selectionAssemblageModel.getSelectedItem()));

                    ObservableList<Composition> assemblageItems = FXCollections.observableArrayList();
                    for (Composition composition : editor.compositions)
                        assemblageItems.add(composition);
                    listViewOfAssemblage.setItems(assemblageItems);
                } catch (StatementDontReturnValueException | VariableIsNull e) {
                    ExceptionMessageController.exceptionMessage = e.getMessage();
                    ExceptionMessageController.start();
                } catch (ZeroRowChangedException ignored) {
                }
            }
        });

        updateButton.setOnAction(event -> {
            SelectionModel selectionCompositionModel = listViewOfAssemblage.getSelectionModel();
            if(selectionCompositionModel.getSelectedItem() == null) {
                ExceptionMessageController.exceptionMessage = "Не вибрана назва композиції.";
                ExceptionMessageController.start();
            } else {
                composition = (Composition) selectionCompositionModel.getSelectedItem();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com.University.TempPaper/UpdateComposition.fxml"));

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
        });
    }
}

