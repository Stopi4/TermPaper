package Commands;

import Exceptions.StatementDontReturnValueException;
import Menu.Editor;
import Model.Compositions.Composition;
import dao.Recording.RecordingStudio;

import java.util.List;

public class SelectAssemblageNamesCommand extends Command{
    private RecordingStudio recordingStudio = new RecordingStudio();
    private List<String> assemblageNames;

    public SelectAssemblageNamesCommand(Editor editor){
        super(editor);
    }
    @Override
    public boolean execute() throws StatementDontReturnValueException {
        assemblageNames = recordingStudio.getAssemblageNames();
        if(assemblageNames == null)
             return false;
        editor.setAssemblageNames(assemblageNames);
        return false;
    }
//    public List<String> getAssemblageNames() {
//        return assemblageNames;
//    }
}
