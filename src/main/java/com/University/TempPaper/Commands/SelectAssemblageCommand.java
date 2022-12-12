package com.University.TempPaper.Commands;

import com.University.TempPaper.Exceptions.VariableIsNull;
import com.University.TempPaper.Exceptions.StatementDontReturnValueException;
import com.University.TempPaper.Controllers.Editor;
import com.University.TempPaper.Model.Composition;
import com.University.TempPaper.dao.RecordingStudio;

import java.util.List;

public class SelectAssemblageCommand extends Command{
    private RecordingStudio recordingStudio = new RecordingStudio();
    private String assemblageName = null;
    private List<Composition> assemblage;
    private double totalDuration;

    public SelectAssemblageCommand(Editor editor, String assemblageName){
        super(editor);
        this.assemblageName = assemblageName;
    }

//    public void setStartValues(String assemblageName) {
//        this.assemblageName = assemblageName;
//    }
    @Override
    public boolean execute() throws StatementDontReturnValueException, VariableIsNull {
        if(assemblageName == null)
            return false;
        assemblage = recordingStudio.getAssemblage(assemblageName);
        if(assemblage == null)
            return false;
        totalDuration = recordingStudio.selectAssemblageTotalDuration(assemblageName);
        editor.setCompositions(assemblage);
        editor.setTotalDuration(totalDuration);
        return true;
    }
    public List<Composition> getAssemblage() {
        return assemblage;
    }
}
