package basemod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.localization.LocalizedStrings;

public class ExhaustMod extends AbstractCardModifier {
    public static String ID = "basemod:ExhaustCardModifier";

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return GameDictionary.EXHAUST.NAMES[0] + LocalizedStrings.PERIOD + " NL " + rawDescription;
    }

    public boolean shouldApply(AbstractCard card) {
        return !card.exhaust;
    }

    public void onInitialApplication(AbstractCard card) {
        card.exhaust = true;
    }

    public AbstractCardModifier makeCopy() {
        return new ExhaustMod();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}
