package basemod.interfaces;

import com.megacrit.cardcrawl.shop.StorePotion;

public interface PostCreateShopPotionSubscriber {
	StorePotion receieveCreateShopPotion(StorePotion pot, int potionNumber);
}
