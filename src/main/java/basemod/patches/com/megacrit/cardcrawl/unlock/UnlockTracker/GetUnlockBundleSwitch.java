package basemod.patches.com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import basemod.abstracts.CustomUnlock;
import basemod.abstracts.CustomUnlockBundle;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;

@SpirePatch(
		clz=UnlockTracker.class,
		method="getUnlockBundle"
)
public class GetUnlockBundleSwitch
{
	public static ArrayList<AbstractUnlock> Postfix(ArrayList<AbstractUnlock> __result, AbstractPlayer.PlayerClass c, int unlockLevel)
	{
		CustomUnlockBundle bundle = BaseMod.getUnlockBundleFor(c, unlockLevel);
		if (bundle != null) {
			ArrayList<CustomUnlock> unlocks = bundle.getUnlocks();
			__result.addAll(unlocks);
		}
		if (__result.isEmpty()) {
			return null;
		}
		return __result;
	}
}
