package Commands;

import Model.Compositions.Composition;
import dao.Recording.RecordingStudio;

import java.util.List;

public class SelectAssemblageCommand implements Command{
    private RecordingStudio recordingStudio = new RecordingStudio();
    private String assemblageName = null;
    private List<Composition> assemblage;

    public SelectAssemblageCommand(String assemblageName){
        this.assemblageName = assemblageName;
    }

//    public void setStartValues(String assemblageName) {
//        this.assemblageName = assemblageName;
//    }
    @Override
    public boolean execute() {
        if(assemblageName == null)
            return false;
        assemblage = recordingStudio.test2(assemblageName);
        return true;
    }
    public List<Composition> getAssemblage() {
        return assemblage;
    }
}
