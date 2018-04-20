package basemod.patches.com.megacrit.cardcrawl.actions.GameActionManager;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.CardQueueItem;

import basemod.BaseMod;
import javassist.CannotCompileException;
import javassist.CtBehavior;

@SpirePatch(cls="com.megacrit.cardcrawl.actions.GameActionManager", method="getNextAction")
public class GetNextActionHook {
	
	
	@SpireInsertPatch()
	public static void Insert(Object __obj_instance) {
		GameActionManager actionManager = (GameActionManager) __obj_instance;
		BaseMod.publishOnCardUse(((CardQueueItem)actionManager.cardQueue.get(0)).card);
	}
	
	public static class Locator extends SpireInsertLocator
	{
		public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
		{
			ArrayList<Matcher> prevMatches = new ArrayList<>();
			prevMatches.add(
					new Matcher.FieldAccessMatcher(
							"com.megacrit.cardcrawl.actions.GameActionManager",
							"cardsPlayedThisTurn"));
			
			Matcher finalMatcher = new Matcher.MethodCallMatcher("java.util.ArrayList", "add");

			return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
		}
	}
	
}
