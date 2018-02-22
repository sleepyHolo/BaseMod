package basemod.patches.com.megacrit.cardcrawl.screens.DeathScreen;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.DevConsole;

@SpirePatch(cls="com.megacrit.cardcrawl.screens.DeathScreen", method="calculateUnlockProgress")
public class ForceUnlock {
	public static void Postfix(Object __obj_instance) {
		DeathScreen screen = (DeathScreen) __obj_instance;
		if (DevConsole.forceUnlocks) {
			screen.unlockBundle = UnlockTracker.getUnlockBundle(
					AbstractDungeon.player.chosenClass,
					UnlockTracker.getUnlockLevel(AbstractDungeon.player.chosenClass));
		}
	}
}
