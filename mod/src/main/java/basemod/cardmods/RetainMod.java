package basemod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.localization.LocalizedStrings;

public class RetainMod extends AbstractCardModifier {
    public static String ID = "RetainCardModifier";

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return GameDictionary.RETAIN.NAMES[0] + LocalizedStrings.PERIOD + " NL " + rawDescription;
    }

    public boolean shouldApply(AbstractCard card) {
        return !card.selfRetain;
    }

    public void onInitialApplication(AbstractCard card) {
        card.selfRetain = true;
    }

    public AbstractCardModifier makeCopy() {
        return new RetainMod();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}
