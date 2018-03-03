package basemod.patches.com.megacrit.cardcrawl.rewards;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.rewards.RewardItem", method="claimReward")
public class RewardItemPotionGetHook {
	@SpireInsertPatch(rloc=34,localvars= {"i"})
	public static void Insert(Object __obj_instance, int i) {
		BaseMod.publishPotionGet(AbstractDungeon.player.potions[i]);;
	}
}
