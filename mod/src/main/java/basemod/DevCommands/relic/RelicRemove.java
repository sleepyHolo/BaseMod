package basemod.DevCommands.relic;

import basemod.BaseMod;
import basemod.DevCommands.ConsoleCommand;
import basemod.DevCommands.deck.DeckAdd;
import basemod.DevCommands.deck.DeckRemove;
import basemod.DevConsole;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class RelicRemove extends ConsoleCommand {
    public RelicRemove() {
        requiresPlayer = true;
        minExtraTokens = 1;
        maxExtraTokens = 1;
        simpleCheck = true;
    }

    @Override
    public void execute(String[] tokens, int depth) {
        String[] relicNameArray = Arrays.copyOfRange(tokens, 2, tokens.length);
        String relicName = Relic.getRelicName(relicNameArray);
        AbstractDungeon.player.loseRelic(relicName);
    }

    @Override
    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> result = new ArrayList<>();
        for (AbstractRelic relic : AbstractDungeon.player.relics) {
            result.add(relic.relicId.replace(' ', '_'));
        }
        return result;
    }

    @Override
    public void errorMsg() {
        Relic.cmdRelicHelp();
    }

}




