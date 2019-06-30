package basemod.devcommands.hand;

import basemod.devcommands.ConsoleCommand;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class HandRemove extends ConsoleCommand {
    public HandRemove() {
        requiresPlayer = true;
        minExtraTokens = 1;
        maxExtraTokens = 1;
        simpleCheck = true;
    }

    @Override
    public void execute(String[] tokens, int depth) {
        int countIndex = Hand.countIndex(tokens);
        String cardName = Hand.cardName(tokens, countIndex);

        // remove all cards
        if (tokens[2].equals("all")) {
            for (AbstractCard c : new ArrayList<>(AbstractDungeon.player.hand.group)) {
                AbstractDungeon.player.hand.moveToExhaustPile(c);
            }
            return;
            // remove single card
        } else {
            boolean removed = false;
            AbstractCard toRemove = null;
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (removed) {
                    break;
                }
                if (c.cardID.equals(cardName)) {
                    toRemove = c;
                    removed = true;
                }
            }
            if (removed) {
                AbstractDungeon.player.hand.moveToExhaustPile(toRemove);
            }
        }
    }

    @Override
    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> result = ConsoleCommand.getCardOptionsFromCardGroup(AbstractDungeon.player.hand);

        result.add("all");

        return result;
    }

    @Override
    public void errorMsg() {
        Hand.cmdHandHelp();
    }
}
