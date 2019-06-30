package basemod.devcommands.relic;

import basemod.devcommands.ConsoleCommand;
import basemod.DevConsole;
import com.megacrit.cardcrawl.helpers.RelicLibrary;

import java.util.ArrayList;
import java.util.Arrays;

public class RelicPool extends ConsoleCommand {
    public RelicPool() {
        minExtraTokens = 1;
        maxExtraTokens = 1;
        simpleCheck = true;
    }

    @Override
    public void execute(String[] tokens, int depth) {
        String[] relicNameArray = Arrays.copyOfRange(tokens, 2, tokens.length);
        String relicName = Relic.getRelicName(relicNameArray);
        DevConsole.log(RelicLibrary.getRelic(relicName).tier.toString());
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




