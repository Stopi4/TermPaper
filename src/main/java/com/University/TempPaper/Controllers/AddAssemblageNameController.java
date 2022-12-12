package com.University.TempPaper.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.University.TempPaper.Commands.InsertAssemblageNameCommand;
import com.University.TempPaper.Exceptions.StatementDontReturnValueException;
import com.University.TempPaper.Exceptions.VariableIsNull;
import com.University.TempPaper.Exceptions.ZeroRowChangedException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddAssemblageNameController extends Editor{
    Editor editor = this;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button button;

    @FXML
    private TextField textField;

    @FXML
    void initialize() {

        button.setOnAction(event -> {
            try {
                executeCommand(new InsertAssemblageNameCommand(editor, textField.getText()));
                button.getScene().getWindow().hide();
            } catch (StatementDontReturnValueException | ZeroRowChangedException | VariableIsNull e) {
                ExceptionMessageController.exceptionMessage = e.getMessage();
                ExceptionMessageController.start();
            }

        });
    }

}
