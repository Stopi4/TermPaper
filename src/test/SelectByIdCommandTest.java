package test;

import Commands.Command;
import Commands.SelectByIdCommand;
//import com.sun.tools.javac.util.Assert;
import Exceptions.StatementDontReturnValueException;
import Exceptions.VariableIsNull;
import Exceptions.ZeroRowChangedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class SelectByIdCommandTest {
    private boolean execute(Command command) throws StatementDontReturnValueException, VariableIsNull, ZeroRowChangedException {
        return command.execute();
    }
    @Test
    public void selectByIdCommandTest() {
//        Assertions.assertTrue(!execute(new SelectByIdCommand(1)), "");
    }
}
