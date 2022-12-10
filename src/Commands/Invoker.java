package Commands;

import Exceptions.StatementDontReturnValueException;
import Exceptions.VariableIsNull;
import Exceptions.ZeroRowChangedException;

public class Invoker {
    private Command command;
    public Invoker(Command command) {
        this.command = command;
    }
    public void setCommand(Command command) {
        this.command = command;
    }
    public void executeCommand() throws StatementDontReturnValueException, VariableIsNull, ZeroRowChangedException {
        command.execute();
    }
}
