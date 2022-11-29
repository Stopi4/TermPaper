package Commands;

import Model.Compositions.Composition;
import dao.Recording.RecordingStudio;

public class SelectByIdCommand implements Command{
    private RecordingStudio recordingStudio = new RecordingStudio();
    private Composition composition;
    private int id;
    public SelectByIdCommand(int id) {
        this.id = id;
    }
    @Override
    public boolean execute() {
        composition = recordingStudio.test6(id);
        if(composition == null)
            return false;
        return true;
    }
    public Composition getValue() {
        return composition;
    }
}
