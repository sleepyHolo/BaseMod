package basemod.patches.com.megacrit.cardcrawl.unlock.UnlockTracker;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.unlock.UnlockTracker", method="refresh")
public class PostRefresh {
	public static void Postfix() {
		BaseMod.publishPostRefresh();
	}
}
