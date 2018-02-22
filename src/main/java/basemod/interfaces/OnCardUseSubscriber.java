package basemod.interfaces;

import com.megacrit.cardcrawl.cards.AbstractCard;

public interface OnCardUseSubscriber {
	void receiveCardUsed(AbstractCard c);
}
