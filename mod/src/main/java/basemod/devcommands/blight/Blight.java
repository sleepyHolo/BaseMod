package basemod.devcommands.blight;

import basemod.devcommands.ConsoleCommand;
import basemod.DevConsole;

public class Blight extends ConsoleCommand {
    public Blight() {
        followup.put("add", BlightAdd.class);
        followup.put("remove", BlightRemove.class);
        requiresPlayer = true;
    }

    @Override
    public void execute(String[] tokens, int depth) {
        cmdBlightHelp();
    }

    public void errorMsg() {
        cmdBlightHelp();
    }

    public static void cmdBlightHelp() {
        DevConsole.couldNotParse();
        DevConsole.log("options are:");
        DevConsole.log("* add [id]");
        DevConsole.log("* remove [id]");
    }
}
