package com.University.TempPaper.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.University.TempPaper.Commands.InsertAssemblageNameCommand;
import com.University.TempPaper.Exceptions.StatementDontReturnValueException;
import com.University.TempPaper.Exceptions.VariableIsNull;
import com.University.TempPaper.Exceptions.ZeroRowChangedException;
import com.University.TempPaper.dao.RecordingStudio;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddAssemblageNameController extends Editor{
    private static final Logger LOG = LogManager.getLogger(AddAssemblageNameController.class);
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
                String assemblageName = textField.getText();
                if (assemblageName == "") {
                    String message = "Поле назви збірки є пустим!";
                    LOG.warn(message);

                    ExceptionMessageController.exceptionMessage = message;
                    ExceptionMessageController.start();
                } else {
                    executeCommand(new InsertAssemblageNameCommand(editor, assemblageName));
                    button.getScene().getWindow().hide();
                }
            } catch (StatementDontReturnValueException | ZeroRowChangedException | VariableIsNull e) {
                LOG.warn(e);
                ExceptionMessageController.exceptionMessage = e.getMessage();
                ExceptionMessageController.start();
            }
        });
    }
}
