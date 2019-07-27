package basemod.devcommands.key;

import basemod.devcommands.ConsoleCommand;
import basemod.DevConsole;
import com.megacrit.cardcrawl.core.Settings;

import java.util.ArrayList;

public class KeyLose extends ConsoleCommand {

    public KeyLose() {
        requiresPlayer = true;
        minExtraTokens = 1;
        simpleCheck = true;
    }

    @Override
    public void execute(String[] tokens, int depth) {
        switch(tokens[depth]) {
            case "emerald":
                Settings.hasEmeraldKey = false;
                break;
            case "sapphire":
                Settings.hasSapphireKey = false;
                break;
            case "ruby":
                Settings.hasRubyKey = false;
                break;
            case "all":
                Settings.hasEmeraldKey = false;
                Settings.hasSapphireKey = false;
                Settings.hasRubyKey = false;
                break;

            default:
                DevConsole.log("Key does not exist.");
                break;
        }
    }

    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> tmp = new ArrayList<>();
        tmp.add("emerald");
        tmp.add("sapphire");
        tmp.add("ruby");
        tmp.add("all");
        return tmp;
    }
}
