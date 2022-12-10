package test;

import Commands.Command;
import Commands.InsertCommand;
import Exceptions.StatementDontReturnValueException;
import Exceptions.VariableIsNull;
import Exceptions.ZeroRowChangedException;
import Model.Compositions.Composition;
import org.junit.jupiter.api.Test;

public class InsertCommandTest {
    private boolean execute(Command command) throws StatementDontReturnValueException, VariableIsNull, ZeroRowChangedException {
        return command.execute();
    }
    @Test
    public void insertCommandTest() {
//        Composition composition = new Composition(1, "Stairway to Heaven", 483, null, "Led Zeppelin IV", "Led Zeppelin");
//        execute(new InsertCommand(composition));

    }
}
