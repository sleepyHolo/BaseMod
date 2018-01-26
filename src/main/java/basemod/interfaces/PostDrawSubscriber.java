package basemod.interfaces;

import com.megacrit.cardcrawl.cards.AbstractCard;

public interface PostDrawSubscriber {
    void receivePostDraw(AbstractCard c);
}