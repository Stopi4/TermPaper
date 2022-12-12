package com.University.TempPaper.Commands;

import com.University.TempPaper.Exceptions.StatementDontReturnValueException;
import com.University.TempPaper.Controllers.Editor;
import com.University.TempPaper.dao.RecordingStudio;

import java.util.List;

public class SelectAssemblageNamesCommand extends Command{
    private RecordingStudio recordingStudio = new RecordingStudio();
    private List<String> assemblageNames;

    public SelectAssemblageNamesCommand(Editor editor){
        super(editor);
    }
    @Override
    public boolean execute() throws StatementDontReturnValueException {
        assemblageNames = recordingStudio.selectAssemblageNames();
        if(assemblageNames == null)
             return false;
        editor.setAssemblageNames(assemblageNames);
        return false;
    }
}
