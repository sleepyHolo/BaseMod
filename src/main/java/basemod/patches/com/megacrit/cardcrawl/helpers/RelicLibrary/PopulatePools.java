package basemod.patches.com.megacrit.cardcrawl.helpers.RelicLibrary;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;
import java.util.Map;

@SpirePatch(cls="com.megacrit.cardcrawl.helpers.RelicLibrary", method="populateRelicPool")
public class PopulatePools {

	public static void Postfix(ArrayList<String> pool, AbstractRelic.RelicTier tier, AbstractPlayer.PlayerClass chosenClass) {
		if (!BaseMod.isBaseGameCharacter(chosenClass)) {
			for (Map.Entry<String, AbstractRelic> r : BaseMod.getRelicsInCustomPool(
					BaseMod.getColor(chosenClass)).entrySet()) {
				if (((AbstractRelic) r.getValue()).tier  == tier && (
						!UnlockTracker.isRelicLocked((String) r.getKey()) ||
						Settings.isDailyRun)) {
					pool.add(r.getKey());
				}
			}
		}
	}
	
}
