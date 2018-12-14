package basemod.patches.com.megacrit.cardcrawl.actions.GameActionManager;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;

@SpirePatch(cls="com.megacrit.cardcrawl.actions.GameActionManager", method="getNextAction")
public class OnPlayerLoseBlockToggle {
	public static boolean on = false;
	
	@SpireInsertPatch(
			rloc=197
	)
	public static void InsertPre(GameActionManager __instance) {
		on = true;
	}

	@SpireInsertPatch(
			rloc=206
	)
	public static void InsertPost(GameActionManager __instance) {
		on = false;
	}

}
