package com.University.TempPaper.Commands;

import com.University.TempPaper.Exceptions.StatementDontReturnValueException;
import com.University.TempPaper.Model.Composition;
import com.University.TempPaper.dao.RecordingStudio;
import com.University.TempPaper.Controllers.Editor;

public class SelectCompositionByIdCommand extends Command{
    private RecordingStudio recordingStudio = new RecordingStudio();
    private Composition composition;
    private int id;
    public SelectCompositionByIdCommand(Editor editor, int id) {
        super(editor);
        this.id = id;
    }
    @Override
    public boolean execute() throws StatementDontReturnValueException {
        composition = recordingStudio.getCompositionById(id);
        if(composition == null)
            return false;
        editor.setComposition(composition);
        return true;
    }
}
