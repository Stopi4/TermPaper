package com.University.TempPaper.Commands;

import com.University.TempPaper.Exceptions.StatementDontReturnValueException;
import com.University.TempPaper.Exceptions.VariableIsNull;
import com.University.TempPaper.Exceptions.ZeroRowChangedException;
import com.University.TempPaper.Controllers.Editor;
import com.University.TempPaper.dao.RecordingStudio;

public class DeleteCompositionByIdCommand extends Command{
    private RecordingStudio recordingStudio = new RecordingStudio();
    private int compositionId;

    public DeleteCompositionByIdCommand(Editor editor, int compositionId) {
        super(editor);
        this.compositionId = compositionId;
    }

    @Override
    public boolean execute() throws ZeroRowChangedException, StatementDontReturnValueException, VariableIsNull {
//        if(!recordingStudio.isCollectionExist(assemblageName))
//            return false;
        recordingStudio.deleteCompositionById(compositionId);
        return true;
    }
}
