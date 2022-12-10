package Commands;

import Exceptions.VariableIsNull;
import Exceptions.StatementDontReturnValueException;
import Menu.Editor;
import Model.Compositions.Composition;
import dao.Recording.RecordingStudio;

import java.util.List;

public class SelectAssemblageCommand extends Command{
    private RecordingStudio recordingStudio = new RecordingStudio();
    private String assemblageName = null;
    private List<Composition> assemblage;

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
        editor.setCompositions(assemblage);
        return true;
    }
    public List<Composition> getAssemblage() {
        return assemblage;
    }
}
