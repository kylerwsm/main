package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;

public class ClearCommand extends Command {
    public static final String COMMAND_WORD = "clear";
    private static final String MAIN_USAGE = "Did you mean:\n"
        + "Clearing the patients list: patientclear or pclear\n";
    private static final String GOTO_USAGE = "Did you mean:\n"
        + "Clearing the records list: recordclear or rclear\n";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        if (MainWindow.isGoToMode()) {
            return new CommandResult(GOTO_USAGE);
        } else {
            return new CommandResult(MAIN_USAGE);
        }
    }
}
