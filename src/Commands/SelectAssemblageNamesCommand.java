package Commands;

import Model.Compositions.Composition;
import dao.Recording.RecordingStudio;

import java.util.List;

public class SelectAssemblageNamesCommand implements Command{
    private RecordingStudio recordingStudio = new RecordingStudio();
    private List<String> assemblageNames;

    public SelectAssemblageNamesCommand(){
    }
    @Override
    public boolean execute() {
        assemblageNames = recordingStudio.test5();
        return false;
    }
    public List<String> getAssemblageNames() {
        return assemblageNames;
    }
}
