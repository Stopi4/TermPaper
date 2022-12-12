package com.University.TempPaper.Commands;

import com.University.TempPaper.Controllers.Editor;
import com.University.TempPaper.Exceptions.StatementDontReturnValueException;
import com.University.TempPaper.Exceptions.ZeroRowChangedException;
import com.University.TempPaper.dao.RecordingStudio;

public class DeleteCompositionGenreCommand extends Command{
    private RecordingStudio recordingStudio = new RecordingStudio();
    private String genreName;
    private int genreId;
    private int compositionId;

    public DeleteCompositionGenreCommand(Editor editor, String genreName, int compositionId) {
        super(editor);
        this.genreName = genreName;
        this.compositionId = compositionId;
    }

    @Override
    public boolean execute() throws ZeroRowChangedException, StatementDontReturnValueException {
//        if(!recordingStudio.isCollectionExist(assemblageName))
//            return false;
        genreId = recordingStudio.selectGenreIdByName(genreName);
        recordingStudio.deleteGenreOfCompositionByGenre(compositionId, genreId);

        return true;
    }
}