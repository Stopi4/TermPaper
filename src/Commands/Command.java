package Commands;

import Exceptions.VariableIsNull;
import Exceptions.StatementDontReturnValueException;
import Exceptions.ZeroRowChangedException;
import Menu.Editor;

public abstract class Command {
     protected Editor editor;
     Command(Editor editor) {
          this.editor = editor;
     }
     public abstract boolean execute() throws StatementDontReturnValueException, VariableIsNull, ZeroRowChangedException;
}
