package com.University.TempPaper.Commands;

import com.University.TempPaper.Exceptions.StatementDontReturnValueException;
import com.University.TempPaper.Controllers.Editor;
import com.University.TempPaper.Model.Composition;
import com.University.TempPaper.dao.RecordingStudio;

import java.util.List;

public class SelectCompositionsByGenreNameCommand extends  Command{
    private RecordingStudio recordingStudio = new RecordingStudio();
    private List<Composition> compositions;
    private String genreName;
    public SelectCompositionsByGenreNameCommand(Editor editor, String genreName) {
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
