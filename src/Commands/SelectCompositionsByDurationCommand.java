package Commands;

import Menu.Menu;
import Model.Compositions.Composition;
import dao.Recording.RecordingStudio;
import Menu.Editor;

import java.util.List;

public class SelectCompositionsByDurationCommand extends Command{
    private RecordingStudio recordingStudio = new RecordingStudio();
    private List<Composition> compositions = null;
    private double d1, d2;

    public SelectCompositionsByDurationCommand(Editor editor, double d1, double d2){
        super(editor);
        this.d1 = d1;
        this.d2 = d2;
    }
    @Override
    public boolean execute() {
        compositions = recordingStudio.getCompositionsByDuration(d1, d2);
        if(compositions == null)
            return false;
        editor.setCompositions(compositions);
        return true;
    }
}
