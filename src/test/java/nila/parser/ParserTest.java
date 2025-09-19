package nila.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import nila.Nila;

public class ParserTest {
    @Test
    public void parseCommand_listCommand_returnsList() {
        Nila.Command result = Parser.parseCommand("list");
        assertEquals(Nila.Command.LIST, result);
    }

    @Test
    public void parseCommand_uppercaseListCommand_returnsList() {
        Nila.Command result = Parser.parseCommand("LIST");
        assertEquals(Nila.Command.LIST, result);
    }

    @Test
    public void parseCommand_mixedCaseListCommand_returnsList() {
        Nila.Command result = Parser.parseCommand("LisT");
        assertEquals(Nila.Command.LIST, result);
    }

    @Test
    public void parseCommand_markCommand_returnsMark() {
        Nila.Command result = Parser.parseCommand("mark");
        assertEquals(Nila.Command.MARK, result);
    }

    @Test
    public void parseCommand_unmarkCommand_returnsUnmark() {
        Nila.Command result = Parser.parseCommand("unmark");
        assertEquals(Nila.Command.UNMARK, result);
    }

    @Test
    public void parseCommand_deleteCommand_returnsDelete() {
        Nila.Command result = Parser.parseCommand("delete");
        assertEquals(Nila.Command.DELETE, result);
    }

    @Test
    public void parseCommand_todoCommand_returnsTodo() {
        Nila.Command result = Parser.parseCommand("todo");
        assertEquals(Nila.Command.TODO, result);
    }

    @Test
    public void parseCommand_deadlineCommand_returnsDeadline() {
        Nila.Command result = Parser.parseCommand("deadline");
        assertEquals(Nila.Command.DEADLINE, result);
    }

    @Test
    public void parseCommand_eventCommand_returnsEvent() {
        Nila.Command result = Parser.parseCommand("event");
        assertEquals(Nila.Command.EVENT, result);
    }

    @Test
    public void parseCommand_findCommand_returnsFind() {
        Nila.Command result = Parser.parseCommand("find");
        assertEquals(Nila.Command.FIND, result);
    }

    @Test
    public void parseCommand_byeCommand_returnsBye() {
        Nila.Command result = Parser.parseCommand("bye");
        assertEquals(Nila.Command.BYE, result);
    }

    @Test
    public void parseCommand_helpCommand_returnsHelp() {
        Nila.Command result = Parser.parseCommand("help");
        assertEquals(Nila.Command.HELP, result);
    }

    @Test
    public void parseCommand_unknownCommand_returnsUnknown() {
        Nila.Command result = Parser.parseCommand("invalid");
        assertEquals(Nila.Command.UNKNOWN, result);
    }
}
