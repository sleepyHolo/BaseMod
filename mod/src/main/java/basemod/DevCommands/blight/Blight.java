package basemod.DevCommands.blight;

import basemod.DevCommands.ConsoleCommand;
import basemod.DevCommands.deck.DeckAdd;
import basemod.DevCommands.deck.DeckRemove;
import basemod.DevConsole;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.BlightHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.Arrays;

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
