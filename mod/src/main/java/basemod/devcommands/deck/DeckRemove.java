package basemod.devcommands.deck;

import basemod.devcommands.ConsoleCommand;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class DeckRemove extends DeckManipulator {

    public DeckRemove() {
        maxExtraTokens = 1;
        simpleCheck = true;
    }

    public void execute(String[] tokens, int depth) {
        // remove all cards
        if (tokens[2].equals("all")) {
            for (String str : AbstractDungeon.player.masterDeck.getCardNames()) {
                AbstractDungeon.player.masterDeck.removeCard(str);
            }
            // remove single card
        } else {
            AbstractDungeon.player.masterDeck.removeCard(getCardID(tokens));
        }
    }

    @Override
    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> result = ConsoleCommand.getCardOptionsFromCardGroup(AbstractDungeon.player.masterDeck);

        result.add("all");

        return result;
    }
}
