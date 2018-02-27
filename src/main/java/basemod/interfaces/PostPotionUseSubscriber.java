package basemod.interfaces;

import com.megacrit.cardcrawl.potions.AbstractPotion;

public interface PostPotionUseSubscriber {
	void receivePostPotionUse(AbstractPotion p);
}
