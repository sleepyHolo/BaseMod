package basemod.patches.com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;

import basemod.BaseMod;
import basemod.abstracts.CustomUnlock;
import basemod.abstracts.CustomUnlockBundle;

@SpirePatch(cls="com.megacrit.cardcrawl.unlock.UnlockTracker", method="getUnlockBundle")
public class GetUnlockBundleSwitch {
	@SpireInsertPatch(rloc=98, localvars={"tmpBundle"})
	public static void Insert(Object cObj, int unlockLevel, ArrayList<AbstractUnlock> tmpBundle) {
		AbstractPlayer.PlayerClass chosenClass = (AbstractPlayer.PlayerClass) cObj;
		CustomUnlockBundle bundle = BaseMod.getUnlockBundleFor(chosenClass, unlockLevel);
		if (bundle != null) {
			ArrayList<CustomUnlock> unlocks = bundle.getUnlocks();
			for (CustomUnlock unlock : unlocks) {
				tmpBundle.add(unlock);
			}
		}
	}
	
	
}
