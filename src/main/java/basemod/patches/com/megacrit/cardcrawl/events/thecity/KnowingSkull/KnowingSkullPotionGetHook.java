package basemod.patches.com.megacrit.cardcrawl.events.thecity.KnowingSkull;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.events.thecity.KnowingSkull", method="obtainReward")
public class KnowingSkullPotionGetHook {
	@SpireInsertPatch(rloc=8,localvars= {"i"})
	public static void Insert(Object __obj_instance, int slot, int i) {
		BaseMod.publishPotionGet(AbstractDungeon.player.potions[i]);;
	}
}
