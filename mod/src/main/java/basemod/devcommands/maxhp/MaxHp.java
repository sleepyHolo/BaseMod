package basemod.devcommands.maxhp;

import basemod.devcommands.ConsoleCommand;
import basemod.DevConsole;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class MaxHp extends ConsoleCommand {
    public MaxHp() {
        requiresPlayer = true;
        minExtraTokens = 2;
        maxExtraTokens = 2;
    }

    @Override
    public void execute(String[] tokens, int depth) {
        if ((tokens[1].toLowerCase().equals("add") || tokens[1].toLowerCase().equals("a"))) {
            int i;
            try {
                i = Integer.parseInt(tokens[2]);
                AbstractDungeon.player.increaseMaxHp(i, true);
            } catch (Exception e) {
                cmdMaxHPHelp();
            }
        } else if ((tokens[1].toLowerCase().equals("lose") || tokens[1].toLowerCase().equals("l"))) {
            int i;
            try {
                i = Integer.parseInt(tokens[2]);
                AbstractDungeon.player.decreaseMaxHealth(i);
            } catch (Exception e) {
                cmdMaxHPHelp();
            }
        } else {
            cmdMaxHPHelp();
        }
    }

    @Override
    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> result = new ArrayList<>();
        result.add("add");
        result.add("lose");

        if(tokens.length == depth + 1) {
            return result;
        } else if(result.contains(tokens[depth])) {
            if(tokens[depth + 1].matches("\\d+")) {
                complete = true;
            }
            result = smallNumbers();
        }
        return result;
    }

    @Override
    public void errorMsg() {
        cmdMaxHPHelp();
    }

    private static void cmdMaxHPHelp() {
        DevConsole.couldNotParse();
        DevConsole.log("options are:");
        DevConsole.log("* add [amt]");
        DevConsole.log("* lose [amt]");
    }
}
