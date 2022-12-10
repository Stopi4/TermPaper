package Commands;

import Exceptions.StatementDontReturnValueException;
import Exceptions.VariableIsNull;
import Exceptions.ZeroRowChangedException;
import Menu.Editor;
import dao.Recording.RecordingStudio;

public class DeleteAssemblageCommand extends Command{
    private RecordingStudio recordingStudio = new RecordingStudio();
    private String assemblageName;

    public DeleteAssemblageCommand(Editor editor, String assemblageName) {
        super(editor);
        this.assemblageName = assemblageName;
    }

    @Override
    public boolean execute() throws StatementDontReturnValueException, VariableIsNull, ZeroRowChangedException {
        if(assemblageName == null)
            return false;
        if(!recordingStudio.isCollectionExist(assemblageName))
            return false;
        recordingStudio.deleteAssemblage(assemblageName);
        return true;
    }
}
