package basemod.devcommands.deck;

import basemod.devcommands.ConsoleCommand;
import basemod.DevConsole;

public class Deck extends ConsoleCommand {
    public Deck() {
        followup.put("add", DeckAdd.class);
        followup.put("remove", DeckRemove.class);
        requiresPlayer = true;
    }

    @Override
    public void execute(String[] tokens, int depth) {
        cmdDeckHelp();
    }

    @Override
    public void errorMsg() {
        cmdDeckHelp();
    }

    public static void cmdDeckHelp() {
        DevConsole.couldNotParse();
        DevConsole.log("options are:");
        DevConsole.log("* add [id] {count} {upgrade amt}");
        DevConsole.log("* remove [id]");
        DevConsole.log("* remove all");
    }
}
