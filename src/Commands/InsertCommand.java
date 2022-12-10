package Commands;

import Exceptions.VariableIsNull;
import Exceptions.StatementDontReturnValueException;
import Exceptions.ZeroRowChangedException;
import Menu.Editor;
import Model.Compositions.Composition;
import dao.Recording.RecordingStudio;

public class InsertCommand extends Command{
    private RecordingStudio recordingStudio = new RecordingStudio();
    private Composition composition = null;

    public InsertCommand(Editor editor, Composition composition) {
        super(editor);
        this.composition = composition;
    }


//    public void setStartValues(Composition composition) {
//        this.composition = composition;
//    }
    @Override
    public boolean execute() throws StatementDontReturnValueException, VariableIsNull, ZeroRowChangedException {
        if(composition == null)
            return false;

        recordingStudio.insertComposition(composition);
        return true;
    }
}
