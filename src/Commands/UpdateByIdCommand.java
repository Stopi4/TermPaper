package Commands;

import Model.Compositions.Composition;
import dao.Recording.RecordingStudio;
import Menu.Editor;

public class UpdateByIdCommand extends Command{
    private RecordingStudio recordingStudio = new RecordingStudio();
    private int id;
    private Composition composition;

    public UpdateByIdCommand(Editor editor, int id, Composition composition) {
        super(editor);
        this.id = id;
        this.composition = composition;
    }

    @Override
    public boolean execute() {
        if(composition == null)
            return false;
        if(!recordingStudio.isIdExist(id) || !recordingStudio.isCollectionExist(composition.getAssemblageName()))
            return false;
//        recordingStudio.test3(id, composition);
        return true;
    }
}
