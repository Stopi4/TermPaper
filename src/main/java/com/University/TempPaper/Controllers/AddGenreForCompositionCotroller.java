package com.University.TempPaper.Controllers;
import java.net.URL;
import java.util.ResourceBundle;

import com.University.TempPaper.Commands.InsertGenreCommand;
import com.University.TempPaper.Commands.InsertGenreOfComposition;
import com.University.TempPaper.Commands.SelectGenreIdByNameCommand;
import com.University.TempPaper.Commands.SelectGenreNamesCommand;
import com.University.TempPaper.Exceptions.StatementDontReturnValueException;
import com.University.TempPaper.Exceptions.VariableIsNull;
import com.University.TempPaper.Exceptions.ZeroRowChangedException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddGenreForCompositionCotroller extends Editor{
    Editor editor = this;
    private static final Logger LOG = LogManager.getLogger(AddGenreForCompositionCotroller.class);

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
            int genreId;
            try {
                executeCommand(new SelectGenreNamesCommand(editor));
                String genreName = textField.getText();
                if (genreName == "") {
                    String message = "Поле назви жанру є пустим!";
                    LOG.warn(message);

                    ExceptionMessageController.exceptionMessage = message;
                    ExceptionMessageController.start();
                } else {
                    if (!editor.genreNames.contains(genreName)) {
                        executeCommand(new InsertGenreCommand(editor, genreName));
                        genreId = editor.getGenreId();
                    } else {
                        executeCommand(new SelectGenreIdByNameCommand(editor, genreName));
                        genreId = editor.getGenreId();
                    }
                    executeCommand(new InsertGenreOfComposition(editor, UpdateCompositionController.compositionId, genreId));
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

