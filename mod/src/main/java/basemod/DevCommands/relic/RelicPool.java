package basemod.DevCommands.relic;

import basemod.BaseMod;
import basemod.DevCommands.ConsoleCommand;
import basemod.DevCommands.deck.DeckAdd;
import basemod.DevCommands.deck.DeckRemove;
import basemod.DevConsole;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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




