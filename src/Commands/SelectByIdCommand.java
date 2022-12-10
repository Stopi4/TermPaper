package Commands;

import Menu.Menu;
import Model.Compositions.Composition;
import dao.Recording.RecordingStudio;
import Menu.Editor;

public class SelectByIdCommand extends Command{
    private RecordingStudio recordingStudio = new RecordingStudio();
    private Composition composition;
    private int id;
    public SelectByIdCommand(Editor editor, int id) {
        super(editor);
        this.id = id;
    }
    @Override
    public boolean execute() {
        composition = recordingStudio.getCompositionByID(id);
        if(composition == null)
            return false;
        editor.setComposition(composition);
        return true;
    }
//    public Composition getValue() {
//        return composition;
//    }
}
