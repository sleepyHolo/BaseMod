package basemod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.localization.LocalizedStrings;

public class InnateMod extends AbstractCardModifier {
    public static String ID = "InnateCardModifier";

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return GameDictionary.INNATE.NAMES[0] + LocalizedStrings.PERIOD + " NL " + rawDescription;
    }

    public boolean shouldApply(AbstractCard card) {
        return !card.isInnate;
    }

    public void onInitialApplication(AbstractCard card) {
        card.isInnate = true;
    }

    public AbstractCardModifier makeCopy() {
        return new InnateMod();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}
