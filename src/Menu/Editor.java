package Menu;

import Commands.Command;
import Model.Compositions.Composition;

import java.util.LinkedList;
import java.util.List;

public abstract class Editor {
    protected Composition composition;
    protected List<Composition> compositions;
    protected List<String> assemblageNames;


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
}
