package com.University.TempPaper.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.University.TempPaper.Commands.InsertGenreCommand;
import com.University.TempPaper.Exceptions.StatementDontReturnValueException;
import com.University.TempPaper.Exceptions.VariableIsNull;
import com.University.TempPaper.Exceptions.ZeroRowChangedException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddGenreController extends Editor {
    Editor editor = this;
    private static final Logger LOG = LogManager.getLogger(AddGenreController.class);

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
                String genreName = textField.getText();
                if(genreName == "") {
                    String message = "Поле назви збірки є пустим!";
                    LOG.warn(message);

                    ExceptionMessageController.exceptionMessage = message;
                    ExceptionMessageController.start();
                } else {
                    executeCommand(new InsertGenreCommand(editor, genreName));
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