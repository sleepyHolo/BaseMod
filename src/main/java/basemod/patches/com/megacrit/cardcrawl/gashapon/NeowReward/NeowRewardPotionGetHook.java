package basemod.patches.com.megacrit.cardcrawl.gashapon.NeowReward;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.gashapon.NeowReward", method="getPotion")
public class NeowRewardPotionGetHook {
	@SpireInsertPatch(rloc=7)
	public static void Insert(Object __obj_instance, Object p) {
		AbstractPotion potion = (AbstractPotion)p;
		BaseMod.publishPotionGet(potion);;
	}
}
