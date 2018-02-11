package basemod.patches.com.megacrit.cardcrawl.shop.ShopScreen;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.shop.StorePotion;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.shop.ShopScreen", method="initPotions")
public class PostCreateShopPotionHook {
	@SpireInsertPatch(loc=315, localvars={"i", "potion"})
	public static void Insert(Object __obj_instance, int i, Object potionObj) {
		potionObj = BaseMod.publishPostCreateShopPotion((StorePotion) potionObj, i);
	}
	
}
