package basemod.patches.com.megacrit.cardcrawl.shop.StorePotion;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.shop.StorePotion", method="update")
public class StorePotionPotionGetHook {
	@SpireInsertPatch(rloc=38,localvars= {"potion"})
	public static void Insert(Object __obj_instance, float rugY, Object potion) {
		AbstractPotion p = (AbstractPotion)potion;
		BaseMod.publishPotionGet(p);
	}
}
