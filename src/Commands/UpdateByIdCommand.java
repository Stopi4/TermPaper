package Commands;

import Model.Compositions.Composition;
import dao.Recording.RecordingStudio;

public class UpdateByIdCommand implements Command{
    private RecordingStudio recordingStudio = new RecordingStudio();
    private int id;
    private Composition composition;

    public UpdateByIdCommand(int id, Composition composition) {
        this.id = id;
        this.composition = composition;
    }

    @Override
    public boolean execute() {
        if(composition == null)
            return false;
        if(!recordingStudio.isIdExist(id) || !recordingStudio.isCollectionExist(composition.getAssemblageName()))
            return false;
        recordingStudio.test3(id, composition);
        return true;
    }
}
