package seedu.address.logic.parser;

import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExitCommand object.
 */
public class ExitCommandParser implements Parser<ExitCommand> {

    /**
     * Validates the given ArgumentMultimap by checking that it fulfils certain criteria.
     *
     * @param map the ArgumentMultimap to be validated.
     * @return true if the ArgumentMultimap is valid, false otherwise.
     */
    public static boolean validate(ArgumentMultimap map) {
        return false;
    }

    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @param userInput String from user.
     * @return A {@code Command}.
     * @throws ParseException If {@code userInput} does not conform the expected format.
     */
    @Override
    public ExitCommand parse(String userInput) throws ParseException {
        assert false : "This method should not be invoked";
        return null;
    }
}