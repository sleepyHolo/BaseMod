package basemod.interfaces;

import java.util.ArrayList;

import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.shop.StorePotion;

public interface PostCreateShopPotionSubscriber extends ISubscriber {
	void receiveCreateShopPotions(ArrayList<StorePotion> potions, ShopScreen screenInstance);
}
