package com.University.TempPaper.Commands;

import com.University.TempPaper.Exceptions.VariableIsNull;
import com.University.TempPaper.Exceptions.StatementDontReturnValueException;
import com.University.TempPaper.Exceptions.ZeroRowChangedException;
import com.University.TempPaper.Controllers.Editor;
import com.University.TempPaper.Model.Composition;
import com.University.TempPaper.dao.RecordingStudio;

public class InsertCompositionCommand extends Command{
    private RecordingStudio recordingStudio = new RecordingStudio();
    private Composition composition = null;

    public InsertCompositionCommand(Editor editor, Composition composition) {
        super(editor);
        this.composition = composition;
    }
    @Override
    public boolean execute() throws StatementDontReturnValueException, VariableIsNull, ZeroRowChangedException {
        if(composition == null)
            return false;

        recordingStudio.insertComposition(composition);
        return true;
    }
}
