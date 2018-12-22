package basemod.patches.com.megacrit.cardcrawl.actions.GameActionManager;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(
		clz=GameActionManager.class,
		method="getNextAction"
)
public class OnPlayerLoseBlockToggle
{
	public static boolean isEnabled = false;
	
	@SpireInsertPatch(
			locator=LocatorPre.class
	)
	public static void InsertPre(GameActionManager __instance)
	{
		isEnabled = true;
	}

	@SpireInsertPatch(
			locator=LocatorPost.class
	)
	public static void InsertPost(GameActionManager __instance)
	{
		isEnabled = false;
	}

	private static class LocatorPre extends SpireInsertLocator
	{
		@Override
		public int[] Locate(CtBehavior ctBehavior) throws Exception
		{
			Matcher matcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasPower");
			return LineFinder.findInOrder(ctBehavior, matcher);
		}
	}

	private static class LocatorPost extends SpireInsertLocator
	{
		@Override
		public int[] Locate(CtBehavior ctBehavior) throws Exception
		{
			Matcher matcher = new Matcher.FieldAccessMatcher(AbstractRoom.class, "isBattleOver");
			return LineFinder.findInOrder(ctBehavior, new ArrayList<>(), matcher);
		}
	}
}
