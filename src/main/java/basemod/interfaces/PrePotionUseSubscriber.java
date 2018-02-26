package basemod.interfaces;

import com.megacrit.cardcrawl.potions.AbstractPotion;

public interface PrePotionUseSubscriber {
	void receivePreUsePotion(AbstractPotion p);
}
