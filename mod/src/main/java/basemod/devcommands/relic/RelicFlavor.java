package basemod.devcommands.relic;

import basemod.BaseMod;
import basemod.devcommands.ConsoleCommand;
import basemod.DevConsole;
import com.megacrit.cardcrawl.helpers.RelicLibrary;

import java.util.ArrayList;
import java.util.Arrays;

public class RelicFlavor extends ConsoleCommand {
    public RelicFlavor() {
        minExtraTokens = 1;
        maxExtraTokens = 1;
        simpleCheck = true;
    }

    @Override
    public void execute(String[] tokens, int depth) {
        String[] relicNameArray = Arrays.copyOfRange(tokens, 2, tokens.length);
        String relicName = getRelicName(relicNameArray);
        DevConsole.log(RelicLibrary.getRelic(relicName).flavorText);
    }

    public static String getRelicName(String[] relicNameArray) {
        String relic = String.join(" ", relicNameArray);
        if (BaseMod.underScoreRelicIDs.containsKey(relic)) {
            relic = BaseMod.underScoreRelicIDs.get(relic);
        }
        return relic;
    }

    @Override
    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        return ConsoleCommand.getRelicOptions();
    }


    @Override
    public void errorMsg() {
        Relic.cmdRelicHelp();
    }
}




