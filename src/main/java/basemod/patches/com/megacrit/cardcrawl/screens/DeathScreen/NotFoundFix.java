package basemod.patches.com.megacrit.cardcrawl.screens.DeathScreen;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.DeathScreen;

@SpirePatch(cls="com.megacrit.cardcrawl.screens.DeathScreen", method="calculateUnlockProgress")
public class NotFoundFix {
	public static void Postfix(Object __obj_instance) {
		DeathScreen screen = (DeathScreen) __obj_instance;
		if (screen.unlockBundle != null && screen.unlockBundle.size() <= 0) {
			// game checks for null, not for wrong size
			screen.unlockBundle = null;
		}
	}
}
