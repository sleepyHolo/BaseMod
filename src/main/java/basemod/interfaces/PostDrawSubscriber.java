package basemod.interfaces;

import com.megacrit.cardcrawl.cards.AbstractCard;

public interface PostDrawSubscriber {
    public void receivePostDraw(AbstractCard c);
}