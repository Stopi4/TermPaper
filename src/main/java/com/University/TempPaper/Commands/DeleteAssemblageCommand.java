package com.University.TempPaper.Commands;

import com.University.TempPaper.Exceptions.StatementDontReturnValueException;
import com.University.TempPaper.Exceptions.VariableIsNull;
import com.University.TempPaper.Exceptions.ZeroRowChangedException;
import com.University.TempPaper.Controllers.Editor;
import com.University.TempPaper.dao.RecordingStudio;

public class DeleteAssemblageCommand extends Command{
    private RecordingStudio recordingStudio = new RecordingStudio();
    private String assemblageName;

    public DeleteAssemblageCommand(Editor editor, String assemblageName) {
        super(editor);
        this.assemblageName = assemblageName;
    }

    @Override
    public boolean execute() throws StatementDontReturnValueException, VariableIsNull, ZeroRowChangedException {
        if(assemblageName == null)
            return false;
        recordingStudio.deleteAssemblage(assemblageName);
        return true;
    }
}
