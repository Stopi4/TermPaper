package Commands;

import Model.Compositions.Composition;
import dao.Recording.RecordingStudio;

public class InsertCommand implements Command{
    private RecordingStudio recordingStudio = new RecordingStudio();
    private Composition composition = null;

    public InsertCommand(Composition composition) {
        this.composition = composition;
    }


//    public void setStartValues(Composition composition) {
//        this.composition = composition;
//    }
    @Override
    public boolean execute() {
        if(composition == null)
            return false;
        recordingStudio.test1(composition);
        return true;
    }
}
