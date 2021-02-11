package basemod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.localization.LocalizedStrings;

public class EtherealMod extends AbstractCardModifier {
    public static String ID = "EtherealCardModifier";

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return GameDictionary.ETHEREAL.NAMES[0] + LocalizedStrings.PERIOD + " NL " + rawDescription;
    }

    public boolean shouldApply(AbstractCard card) {
        return !card.isEthereal;
    }

    public void onInitialApplication(AbstractCard card) {
        card.isEthereal = true;
    }

    public AbstractCardModifier makeCopy() {
        return new EtherealMod();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}
