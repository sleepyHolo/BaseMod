package basemod.devcommands.key;

import basemod.devcommands.ConsoleCommand;
import basemod.DevConsole;

public class KeyCommand extends ConsoleCommand {

    public KeyCommand() {
        requiresPlayer = true;
        minExtraTokens = 1;
        simpleCheck = true;
        followup.put("add", KeyAdd.class);
        followup.put("lose", KeyLose.class);
    }

    @Override
    public void execute(String[] tokens, int depth) {
        errorMsg();
    }

    @Override
    public void errorMsg() {
        cmdDrawHelp();
    }

    private static void cmdDrawHelp() {
        DevConsole.couldNotParse();
        DevConsole.log("options are:");
        DevConsole.log("* draw [amt]");
    }
}
