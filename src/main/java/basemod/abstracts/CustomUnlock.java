package basemod.abstracts;

import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;

public class CustomUnlock extends AbstractUnlock {

	public CustomUnlock(String cardID) {
		this.card = CardLibrary.getCard(cardID);
		this.key = this.card.cardID;
		this.title = this.card.name;
	}
	
}
