package com.University.TempPaper.Commands;

import com.University.TempPaper.Exceptions.VariableIsNull;
import com.University.TempPaper.Exceptions.StatementDontReturnValueException;
import com.University.TempPaper.Exceptions.ZeroRowChangedException;
import com.University.TempPaper.Controllers.Editor;


public abstract class Command {
     protected Editor editor;
     Command(Editor editor) {
          this.editor = editor;
     }
     public abstract boolean execute() throws StatementDontReturnValueException, VariableIsNull, ZeroRowChangedException;
}
