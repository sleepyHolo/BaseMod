package basemod.helpers;

import basemod.BaseMod;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;
import java.util.List;

public class ModalChoice
{

    public interface Callback
    {
        void optionSelected(int index);
    }

    private String title;
    private ArrayList<AbstractCard> cards;

    ModalChoice(String title, ArrayList<AbstractCard> options)
    {
        this.title = title;
        this.cards = options;
    }

    public void open()
    {
        List<AbstractCard> cards_copy = new ArrayList<>(cards.size());
        for (AbstractCard c : cards) {
            AbstractCard copy = c.makeCopy();
            copy.dontTriggerOnUseCard = true;
            copy.purgeOnUse = true;
            cards_copy.add(copy);
        }

        BaseMod.modalChoiceScreen.open(cards_copy, title);
    }

    public List<TooltipInfo> generateTooltips()
    {
        List<TooltipInfo> ret = new ArrayList<>();

        for (AbstractCard card : cards) {
            ret.add(new TooltipInfo(card.name, card.rawDescription));
        }

        return ret;
    }
}
