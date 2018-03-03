package basemod.patches.com.megacrit.cardcrawl.helpers.PotionHelper;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.helpers.PotionHelper", method="obtainPotion")
public class PotionHelperPotionGetHook {
	@SpireInsertPatch(rloc=5)
	public static void Insert(Object p) {
		AbstractPotion potion = (AbstractPotion)p;
		BaseMod.publishPotionGet(potion);
	}
}
