package com.University.TempPaper.Commands;

import com.University.TempPaper.Exceptions.StatementDontReturnValueException;
import com.University.TempPaper.Controllers.Editor;
import com.University.TempPaper.dao.RecordingStudio;

import java.util.List;

public class SelectGenreNamesCommand extends Command {
    private RecordingStudio recordingStudio = new RecordingStudio();
    private List<String> genreNames;

    public SelectGenreNamesCommand(Editor editor){
        super(editor);
    }
    @Override
    public boolean execute() throws StatementDontReturnValueException {
        genreNames = recordingStudio.selectGenreName();
        if(genreNames == null)
            return false;
        editor.setGenreNames(genreNames);
        return false;
    }
}
