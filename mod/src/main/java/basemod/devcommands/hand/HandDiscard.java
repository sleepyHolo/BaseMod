package basemod.devcommands.hand;

import basemod.devcommands.ConsoleCommand;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class HandDiscard extends ConsoleCommand {
    public HandDiscard() {
        requiresPlayer = true;
        minExtraTokens = 1;
        maxExtraTokens = 1;
        simpleCheck = true;
    }

    @Override
    public void execute(String[] tokens, int depth) {
        if (tokens[2].equals("all")) {
            // discard all cards
            for (AbstractCard c : new ArrayList<>(AbstractDungeon.player.hand.group)) {
                AbstractDungeon.player.hand.moveToDiscardPile(c);
                c.triggerOnManualDiscard();
                GameActionManager.incrementDiscard(false);
            }
        } else {
            String cardName = Hand.cardName(tokens);
            // discard single card
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.cardID.equals(cardName)) {
                    AbstractDungeon.player.hand.moveToDiscardPile(c);
                    c.triggerOnManualDiscard();
                    GameActionManager.incrementDiscard(false);
                    return;
                }
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
