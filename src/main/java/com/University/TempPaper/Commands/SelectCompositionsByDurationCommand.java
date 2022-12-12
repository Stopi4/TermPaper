package com.University.TempPaper.Commands;

import com.University.TempPaper.Exceptions.StatementDontReturnValueException;
import com.University.TempPaper.Model.Composition;
import com.University.TempPaper.dao.RecordingStudio;
import com.University.TempPaper.Controllers.Editor;

import java.util.List;

public class SelectCompositionsByDurationCommand extends Command{
    private RecordingStudio recordingStudio = new RecordingStudio();
    private List<Composition> compositions = null;
    private double d1, d2;

    public SelectCompositionsByDurationCommand(Editor editor, double d1, double d2){
        super(editor);
        this.d1 = d1;
        this.d2 = d2;
    }
    @Override
    public boolean execute() throws StatementDontReturnValueException {
        compositions = recordingStudio.getCompositionsByDuration(d1, d2);
        if(compositions == null)
            return false;
        editor.setCompositions(compositions);
        return true;
    }
}
