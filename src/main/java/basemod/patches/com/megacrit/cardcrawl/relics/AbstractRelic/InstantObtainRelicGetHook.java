package basemod.patches.com.megacrit.cardcrawl.relics.AbstractRelic;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.relics.AbstractRelic", method="instantObtain",paramtypes= {"com.megacrit.cardcrawl.relics.AbstractRelic","int","boolean"})
public class InstantObtainRelicGetHook {
	@SpireInsertPatch(rloc=2)
	public static void Insert(AbstractRelic relic, AbstractPlayer p, int slot, boolean callOnEquip) {
		BaseMod.publishRelicGet(relic);;
	}
}
