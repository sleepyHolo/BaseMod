package basemod.patches.com.megacrit.cardcrawl.relics.AbstractRelic;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.relics.AbstractRelic", method="instantObtain",paramtypes= {"boolean"})
public class InstantObtainRelicGetHook2 {
	@SpireInsertPatch(rloc=2)
	public static void Insert(AbstractRelic relic,boolean callOnEquip) {
		BaseMod.publishRelicGet(relic);;
	}
}
