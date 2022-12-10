package Commands;

import Menu.Editor;
import dao.Recording.RecordingStudio;

public class DeleteCompositionFromAssemblageCommand extends Command{
    private RecordingStudio recordingStudio = new RecordingStudio();
    private int compositionId;

    public DeleteCompositionFromAssemblageCommand(Editor editor, int compositionId) {
        super(editor);
        this.compositionId = compositionId;
    }

    @Override
    public boolean execute() {
//        if(!recordingStudio.isCollectionExist(assemblageName))
//            return false;
//        recordingStudio.deleteAssemblage(assemblageName);
        return true;
    }
}
