package basemod.DevCommands.debug;

import basemod.DevCommands.ConsoleCommand;
import basemod.DevConsole;
import basemod.helpers.ConvertHelper;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.util.ArrayList;

public class Debug extends ConsoleCommand {
    public Debug() {
        minExtraTokens = 1;
        maxExtraTokens = 1;
        simpleCheck = true;
    }

    @Override
    public void execute(String[] tokens, int depth) {
        if(tokens[1].equals("true") || tokens[1].equals("false")) {
            try {
                Settings.isDebug = Boolean.parseBoolean(tokens[1]);
                DevConsole.log("Setting debug mode to: " + Settings.isDebug);
            } catch(Exception e) {
                errorMsg();
            }
        } else {
            errorMsg();
        }
    }

    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> result = new ArrayList<>();

        result.add("true");
        result.add("false");

        return result;
    }

    @Override
    public void errorMsg() {
        DevConsole.couldNotParse();
        DevConsole.log("options are:");
        DevConsole.log("* true");
        DevConsole.log("* false");
    }
}
