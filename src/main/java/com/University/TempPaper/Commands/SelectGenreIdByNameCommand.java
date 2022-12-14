package com.University.TempPaper.Commands;

import com.University.TempPaper.Controllers.Editor;
import com.University.TempPaper.Exceptions.StatementDontReturnValueException;
import com.University.TempPaper.Exceptions.VariableIsNull;
import com.University.TempPaper.Exceptions.ZeroRowChangedException;
import com.University.TempPaper.dao.RecordingStudio;

public class SelectGenreIdByNameCommand extends Command{
    private RecordingStudio recordingStudio = new RecordingStudio();
    private String genreName;
    private int compositionId;

    public SelectGenreIdByNameCommand(Editor editor, String genreName) {
        super(editor);
        this.genreName = genreName;
    }
    @Override
    public boolean execute() throws StatementDontReturnValueException, VariableIsNull, ZeroRowChangedException {
        editor.setGenreId(recordingStudio.selectGenreIdByName(genreName));
        return true;
    }
}
