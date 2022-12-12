package com.University.TempPaper.Controllers;

import com.University.TempPaper.Commands.Command;
import com.University.TempPaper.Exceptions.StatementDontReturnValueException;
import com.University.TempPaper.Exceptions.VariableIsNull;
import com.University.TempPaper.Exceptions.ZeroRowChangedException;
import com.University.TempPaper.Model.Composition;

import java.util.List;

public abstract class Editor {
    protected Composition composition;
    protected List<Composition> compositions;
    protected List<String> assemblageNames;
    protected List<String> genreNames;
    protected double totalDuration;
    protected static boolean dataIsCurrent = false;


    public List<String> getAssemblageNames() {
        return assemblageNames;
    }
    public void setAssemblageNames(List<String> assemblageNames) {
        this.assemblageNames = assemblageNames;
    }
    public Composition getComposition() {
        return composition;
    }
    public void setComposition(Composition composition) {
        this.composition = composition;
    }
    public List<Composition> getCompositions() {
        return compositions;
    }
    public void setCompositions(List<Composition> compositions) {
        this.compositions = compositions;
    }
    public List<String> getGenreNames() {
        return genreNames;
    }
    public void setGenreNames(List<String> genreNames) {
        this.genreNames = genreNames;
    }

    public void executeCommand(Command command) throws StatementDontReturnValueException, VariableIsNull, ZeroRowChangedException {
        command.execute();
    }
    public void setTotalDuration(double totalDuration) {
        this.totalDuration = totalDuration;
    }
    public double getTotalDuration() {
        return totalDuration;
    }
}
