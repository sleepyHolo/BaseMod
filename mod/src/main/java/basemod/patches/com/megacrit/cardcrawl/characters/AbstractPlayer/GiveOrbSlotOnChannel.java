package basemod.patches.com.megacrit.cardcrawl.characters.AbstractPlayer;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

@SpirePatch(
		clz=AbstractPlayer.class,
		method="channelOrb"
)
public class GiveOrbSlotOnChannel
{
	public static void Prefix(AbstractPlayer __instance, AbstractOrb orbToSet)
	{
		if (BaseMod.fixesEnabled && __instance.masterMaxOrbs == 0 && __instance.maxOrbs == 0) {
			__instance.masterMaxOrbs = 1;
			__instance.increaseMaxOrbSlots(1, true);
		}
	}
}
