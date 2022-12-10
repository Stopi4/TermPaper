package Commands;

import Exceptions.ZeroRowChangedException;
import Menu.Editor;
import dao.Recording.RecordingStudio;

public class DeleteCompositionByIdCommand extends Command{
    private RecordingStudio recordingStudio = new RecordingStudio();
    private int compositionId;

    public DeleteCompositionByIdCommand(Editor editor, int compositionId) {
        super(editor);
        this.compositionId = compositionId;
    }

    @Override
    public boolean execute() throws ZeroRowChangedException {
//        if(!recordingStudio.isCollectionExist(assemblageName))
//            return false;
        recordingStudio.deleteCompositionById(compositionId);

        return true;
    }
}
