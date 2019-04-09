package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_ONLY_GO_TO_MODE_COMMANDS;
import static seedu.address.commons.core.Messages.MESSAGE_ONLY_PATIENT_MODE_COMMANDS;
import static seedu.address.commons.core.Messages.MESSAGE_ONLY_TASK_OR_DATE_COMMANDS;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.*;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.CalendarWindow;
import seedu.address.ui.MainWindow;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {
        //Commands that should not run in both GoTo mode and Calendar Window
        case PatientAddCommand.COMMAND_WORD:
        case PatientAddCommand.COMMAND_WORD2:
            checkCalendarCondition();
            notGoTo();
            return new PatientAddCommandParser().parse(arguments);

        case PatientClearCommand.COMMAND_WORD:
        case PatientClearCommand.COMMAND_WORD2:
            checkCalendarCondition();
            notGoTo();
            return new PatientClearCommand();

        case PatientCopyCommand.COMMAND_WORD:
        case PatientCopyCommand.COMMAND_WORD2:
            checkCalendarCondition();
            notGoTo();
            return new PatientCopyCommandParser().parse(arguments);

        case PatientDeleteCommand.COMMAND_WORD:
        case PatientDeleteCommand.COMMAND_WORD2:
            checkCalendarCondition();
            notGoTo();
            return new PatientDeleteCommandParser().parse(arguments);

        case PatientEditCommand.COMMAND_WORD:
        case PatientEditCommand.COMMAND_WORD2:
            checkCalendarCondition();
            notGoTo();
            return new PatientEditCommandParser().parse(arguments);

        case PatientFindCommand.COMMAND_WORD:
        case PatientFindCommand.COMMAND_WORD2:
            checkCalendarCondition();
            notGoTo();
            return new PatientFindCommandParser().parse(arguments);

        case PatientListCommand.COMMAND_WORD:
        case PatientListCommand.COMMAND_WORD2:
            checkCalendarCondition();
            notGoTo();
            return new PatientListCommand();

        case PatientSelectCommand.COMMAND_WORD:
        case PatientSelectCommand.COMMAND_WORD2:
            checkCalendarCondition();
            notGoTo();
            return new PatientSelectCommandParser().parse(arguments);

        case HistoryCommand.COMMAND_WORD:
            checkCalendarCondition();
            notGoTo();
            return new HistoryCommand();

        case UndoCommand.COMMAND_WORD:
            checkCalendarCondition();
            notGoTo();
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            checkCalendarCondition();
            notGoTo();
            return new RedoCommand();

        case StatsCommand.COMMAND_WORD:
            checkCalendarCondition();
            notGoTo();
            return new StatsCommandParser().parse(arguments);

        case OpenCommand.COMMAND_WORD:
            checkCalendarCondition();
            notGoTo();
            return new OpenCommandParser().parse(arguments);

        case SaveCommand.COMMAND_WORD:
            checkCalendarCondition();
            notGoTo();
            return new SaveCommandParser().parse(arguments);

        case ImportCommand.COMMAND_WORD:
            checkCalendarCondition();
            notGoTo();
            return new ImportCommandParser().parse(arguments);

        case ExportCommand.COMMAND_WORD:
            checkCalendarCondition();
            notGoTo();
            return new ExportCommandParser().parse(arguments);

        //Commands that run in both GoTo mode and Patient mode but not in Calendar Window
        case SortCommand.COMMAND_WORD:
            if (CalendarWindow.isRunningCommand()) {
                throw new ParseException(MESSAGE_ONLY_TASK_OR_DATE_COMMANDS);
            }
            return new SortCommandParser().parse(arguments);

        case AddCommand.COMMAND_WORD:
            return new AddCommand();

        case EditCommand.COMMAND_WORD:
            return new EditCommand();

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommand();

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case CopyCommand.COMMAND_WORD:
            return new CopyCommand();

        //Commands that runs ONLY in both Patient Mode OR Calendar Window
        case TaskDoneCommand.COMMAND_WORD:
            return new TaskDoneCommandParser().parse(arguments);
        //Commands that should ONLY run in GoTo mode but not in Calendar Window
        case GoToCommand.COMMAND_WORD:
            checkCalendarCondition();
            return new GoToCommandParser().parse(arguments);

        case BackCommand.COMMAND_WORD:
            checkCalendarCondition();
            isGoTo();
            return new BackCommand();

        case RecordAddCommand.COMMAND_WORD:
        case RecordAddCommand.COMMAND_WORD2:
            checkCalendarCondition();
            isGoTo();
            return new RecordAddCommandParser().parse(arguments);

        case RecordClearCommand.COMMAND_WORD:
        case RecordClearCommand.COMMAND_WORD2:
            checkCalendarCondition();
            isGoTo();
            return new RecordClearCommand();

        case RecordEditCommand.COMMAND_WORD:
        case RecordEditCommand.COMMAND_WORD2:
            checkCalendarCondition();
            isGoTo();
            return new RecordEditCommandParser().parse(arguments);

        case RecordDeleteCommand.COMMAND_WORD:
        case RecordDeleteCommand.COMMAND_WORD2:
            checkCalendarCondition();
            isGoTo();
            return new RecordDeleteCommandParser().parse(arguments);

        case TeethEditCommand.COMMAND_WORD:
            checkCalendarCondition();
            isGoTo();
            return new TeethEditCommandParser().parse(arguments);

        //Commands that should run in ALL modes and popups
        case TaskAddCommand.COMMAND_WORD:
        case TaskAddCommand.COMMAND_WORD2:
            return new TaskAddCommandParser().parse(arguments);

        case TaskCalendarCommand.COMMAND_WORD:
        case TaskCalendarCommand.COMMAND_WORD2:
            return new TaskCalendarCommandParser().parse(arguments);

        case TaskEditCommand.COMMAND_WORD:
        case TaskEditCommand.COMMAND_WORD2:
            return new TaskEditCommandParser().parse(arguments);

        case TaskSortCommand.COMMAND_WORD:
        case TaskSortCommand.COMMAND_WORD2:
            return new TaskSortCommandParser().parse(arguments);

        case TaskDeleteCommand.COMMAND_WORD:
        case TaskDeleteCommand.COMMAND_WORD2:
            return new TaskDeleteCommandParser().parse(arguments);

        case TaskCopyCommand.COMMAND_WORD:
        case TaskCopyCommand.COMMAND_WORD2:
            return new TaskCopyCommandParser().parse(arguments);

        case TaskListCommand.COMMAND_WORD:
        case TaskListCommand.COMMAND_WORD2:
            return new TaskListCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case ExitAnywayCommand.COMMAND_WORD:
            return new ExitAnywayCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Checks if a command is being ran from Calendar Window.
     * Feedback given to user if commands are being ran in the wrong modes or areas of the GUI.
     */
    private void checkCalendarCondition() throws ParseException {
        if (CalendarWindow.isRunningCommand()) {
            throw new ParseException(MESSAGE_ONLY_TASK_OR_DATE_COMMANDS);
        }
    }


    /**
     * For commands which can only run in Patient mode.
     * Feedback given to user if commands are being ran in the wrong modes or areas of the GUI.
     */
    private void isGoTo() throws ParseException {
        if (!MainWindow.isGoToMode()) {
            throw new ParseException(MESSAGE_ONLY_PATIENT_MODE_COMMANDS);
        }
    }

    /**
     * For commands which can only run in GoTo mode.
     * Feedback given to user if commands are being ran in the wrong modes or areas of the GUI.
     */
    private void notGoTo() throws ParseException {
        if (MainWindow.isGoToMode()) {
            throw new ParseException(MESSAGE_ONLY_GO_TO_MODE_COMMANDS);
        }
    }

}
