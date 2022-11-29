package Commands;

import dao.Recording.RecordingStudio;

public class DeleteAssemblageCommand implements Command{
    private RecordingStudio recordingStudio = new RecordingStudio();
    private String assemblageName;

    public DeleteAssemblageCommand(String assemblageName) {
        this.assemblageName = assemblageName;
    }

    @Override
    public boolean execute() {
        if(assemblageName == null)
            return false;
        if(!recordingStudio.isCollectionExist(assemblageName))
            return false;
        recordingStudio.test4(assemblageName);
        return true;
    }
}
