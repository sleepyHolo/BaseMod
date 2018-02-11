package basemod.interfaces;

import com.megacrit.cardcrawl.shop.StoreRelic;

public interface PostCreateShopRelicSubscriber {
	StoreRelic recieveCreateShopRelic(StoreRelic relic, int relicNumber);
}
