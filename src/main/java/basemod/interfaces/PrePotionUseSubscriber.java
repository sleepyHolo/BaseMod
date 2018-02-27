package basemod.interfaces;

import com.megacrit.cardcrawl.potions.AbstractPotion;

public interface PrePotionUseSubscriber {
	void receivePrePotionUse(AbstractPotion p);
}
