package Commands;

import Exceptions.StatementDontReturnValueException;
import Menu.Editor;
import Model.Compositions.Composition;
import dao.Recording.RecordingStudio;

import java.util.List;

public class SelectCompositionByGenreNameCommand extends  Command{
    private RecordingStudio recordingStudio = new RecordingStudio();
    private List<Composition> compositions;
    private String genreName;
    public SelectCompositionByGenreNameCommand(Editor editor, String genreName) {
        super(editor);
        this.genreName = genreName;
    }
    @Override
    public boolean execute() throws StatementDontReturnValueException {
        compositions = recordingStudio.getCompositionsByGenreName(genreName);
        if(compositions == null)
            return false;
        editor.setCompositions(compositions);
        return true;
    }
}
