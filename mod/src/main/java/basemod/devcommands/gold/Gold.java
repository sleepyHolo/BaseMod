package basemod.devcommands.gold;

import basemod.devcommands.ConsoleCommand;
import basemod.DevConsole;
import basemod.helpers.ConvertHelper;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class Gold extends ConsoleCommand {
    public Gold() {
        requiresPlayer = true;
        minExtraTokens = 2;
        maxExtraTokens = 2;
    }

    @Override
    public void execute(String[] tokens, int depth) {
        int amount = ConvertHelper.tryParseInt(tokens[2], 0);
        if (tokens[1].toLowerCase().equals("add") || tokens[1].toLowerCase().equals("a")) {
            AbstractDungeon.player.displayGold += amount;
            AbstractDungeon.player.gainGold(amount);
        } else if (tokens[1].toLowerCase().equals("lose") || tokens[1].toLowerCase().equals("l")) {
            AbstractDungeon.player.displayGold = Math.max(AbstractDungeon.player.displayGold - amount, 0);
            AbstractDungeon.player.loseGold(amount);
        } else {
            cmdGoldHelp();
        }
    }

    @Override
    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> result = new ArrayList<>();
        result.add("add");
        result.add("lose");

        if (tokens.length > depth + 1 && result.contains(tokens[depth])) {
            if (tokens[depth + 1].matches("\\d+")) {
                complete = true;
            }
            result = ConsoleCommand.bigNumbers();
        }
        return result;
    }

    @Override
    public void errorMsg() {
        cmdGoldHelp();
    }

    private static void cmdGoldHelp() {
        DevConsole.couldNotParse();
        DevConsole.log("options are:");
        DevConsole.log("* add [amt]");
        DevConsole.log("* lose [amt]");
    }
}
