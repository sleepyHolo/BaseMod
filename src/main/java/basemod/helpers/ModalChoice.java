package basemod.helpers;

import basemod.BaseMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class ModalChoice
{
    private static final Logger logger = LogManager.getLogger(ModalChoice.class.getName());

    public interface ModalChoiceCallback
    {
        void optionSelected(int index);
    }
    public static class Builder
    {
        private String title = "Choose an Option";
        private ArrayList<AbstractCard> options = new ArrayList<>();
        private ModalChoiceCallback callback = null;
        private AbstractCard.CardColor color;

        private Builder()
        {
        }

        public void open()
        {
            ModalChoice ret = new ModalChoice(title, options, callback);
            ret.open();
        }

        public Builder setTitle(String title)
        {
            this.title = title;
            return this;
        }

        public Builder addOption(String description, AbstractCard.CardTarget target)
        {
            return addOption(null, description, target);
        }

        public Builder addOption(String title, String description, AbstractCard.CardTarget target)
        {
            if (title == null) {
                title = "Option " + (options.size() + 1);
            }
            options.add(new ModalChoiceCard(title, description, color, target, options.size(), callback));
            return this;
        }

        public Builder addOption(AbstractCard card)
        {
            options.add(card);
            return this;
        }
    }

    public static Builder build(ModalChoiceCallback callback, AbstractCard.CardColor color)
    {
        Builder ret = new Builder();
        ret.callback = callback;
        ret.color = color;
        return ret;
    }

    private ModalChoiceCallback callback;
    private String title;
    private ArrayList<AbstractCard> cards;

    private ModalChoice(String title, ArrayList<AbstractCard> options, ModalChoiceCallback callback)
    {
        this.title = title;
        this.cards = options;
        this.callback = callback;

        for (AbstractCard c : cards) {
            c.dontTriggerOnUseCard = true;
            c.purgeOnUse = true;
        }
    }

    public void complete(AbstractCard card)
    {
        callback.optionSelected(cards.indexOf(card));
    }

    private void open()
    {
        BaseMod.modalChoiceScreen.open(cards, title, callback);
    }
}
