package basemod.devcommands.hand;

import basemod.devcommands.ConsoleCommand;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class HandSet extends ConsoleCommand {
    public HandSet() {
        requiresPlayer = true;
        minExtraTokens = 3;
        maxExtraTokens = 3;
    }

    @Override
    public void execute(String[] tokens, int depth) {
        if ((tokens[2].equalsIgnoreCase("damage") || tokens[2].equalsIgnoreCase("block") || tokens[2].equalsIgnoreCase("magic") || tokens[2].equalsIgnoreCase("cost") || tokens[2].equalsIgnoreCase("d") || tokens[2].equalsIgnoreCase("b") || tokens[2].equalsIgnoreCase("m") || tokens[2].equalsIgnoreCase("c"))) {
            try{
                String cardName = tokens[3];
                boolean all = tokens[3].equals("all");
                int v = Integer.parseInt(tokens[4]);
                for (AbstractCard c : new ArrayList<>(AbstractDungeon.player.hand.group)) {
                    if (all || c.cardID.equals(cardName)) {
                        if (tokens[2].equalsIgnoreCase("damage") || tokens[2].equalsIgnoreCase("d")) {
                            if (c.baseDamage != v) c.upgradedDamage = true;
                            c.baseDamage = v;
                        }
                        if (tokens[2].equalsIgnoreCase("block") || tokens[2].equalsIgnoreCase("b")) {
                            if (c.baseBlock != v) c.upgradedBlock = true;
                            c.baseBlock = v;
                        }
                        if (tokens[2].equalsIgnoreCase("magic") || tokens[2].equalsIgnoreCase("m")) {
                            if (c.baseMagicNumber != v) c.upgradedMagicNumber = true;
                            c.magicNumber = c.baseMagicNumber = v;
                        }
                        if (tokens[2].equalsIgnoreCase("cost") || tokens[2].equalsIgnoreCase("c")) {
                            if (c.cost != v) c.upgradedCost = true;
                            c.cost = v;
                            c.baseBlock = v;
                            c.costForTurn = v;
                        }
                        c.displayUpgrades();
                        c.applyPowers();
                        if (!all) break;
                    }
                }
            } catch (NumberFormatException e) {
                Hand.cmdHandHelp();
            }
        } else {
            Hand.cmdHandHelp();
        }
    }

    @Override
    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> options = new ArrayList<>();
        options.add("damage");
        options.add("block");
        options.add("magic");
        options.add("cost");

        if(options.contains(tokens[depth])) { //Input cardID is correct
            options.clear();
            options = ConsoleCommand.getCardOptionsFromCardGroup(AbstractDungeon.player.hand);
            options.add("all");

            if(tokens.length == depth + 2) {
                return options;
            } else if(tokens.length > depth + 2 && options.contains(tokens[depth + 1])) {
                options = ConsoleCommand.smallNumbers();

                if(tokens[depth + 2].matches("\\d+")) {
                    complete = true;
                }

                return options;
            } else {
                tooManyTokensError();
            }
        } else if(tokens.length > depth + 1) {//CardID is not correct, but you're typing more parameters???
            tooManyTokensError();
        }
        return options;
    }

    @Override
    public void errorMsg() {
        Hand.cmdHandHelp();
    }
}
