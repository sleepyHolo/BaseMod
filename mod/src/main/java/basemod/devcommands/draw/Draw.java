package basemod.devcommands.draw;

import basemod.devcommands.ConsoleCommand;
import basemod.DevConsole;
import basemod.helpers.ConvertHelper;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class Draw extends ConsoleCommand {

    public Draw() {
        requiresPlayer = true;
        minExtraTokens = 1;
        maxExtraTokens = 1;
    }

    @Override
    public void execute(String[] tokens, int depth) {
        if (tokens.length != 2) {
            cmdDrawHelp();
            return;
        }

        AbstractDungeon.actionManager
                .addToTop(new DrawCardAction(AbstractDungeon.player, ConvertHelper.tryParseInt(tokens[1], 0)));
    }

    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        if(tokens[depth].matches("\\d+")) {
            complete = true;
        }
        return ConsoleCommand.smallNumbers();
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
