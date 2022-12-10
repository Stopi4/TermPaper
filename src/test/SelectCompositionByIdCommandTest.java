package test;

import Commands.Command;
//import com.sun.tools.javac.util.Assert;
import Exceptions.StatementDontReturnValueException;
import Exceptions.VariableIsNull;
import Exceptions.ZeroRowChangedException;
import org.junit.jupiter.api.Test;

public class SelectCompositionByIdCommandTest {
    private boolean execute(Command command) throws StatementDontReturnValueException, VariableIsNull, ZeroRowChangedException {
        return command.execute();
    }
    @Test
    public void selectByIdCommandTest() {
//        Assertions.assertTrue(!execute(new SelectByIdCommand(1)), "");
    }
}
