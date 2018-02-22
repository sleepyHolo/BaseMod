package basemod.patches.com.megacrit.cardcrawl.actions.GameActionManager;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.CardQueueItem;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.actions.GameActionManager", method="getNextAction")
public class GetNextActionHook {
	@SpireInsertPatch(rloc=49)
	public static void Insert(Object __obj_instance) {
		GameActionManager actionManager = (GameActionManager) __obj_instance;
		BaseMod.publishOnCardUse(((CardQueueItem)actionManager.cardQueue.get(0)).card);
	}
}
