package basemod.patches.com.megacrit.cardcrawl.screens.charSelect.CharacterOption;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.helpers.Prefs;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import javassist.CtBehavior;

@SpirePatch(
		clz=CharacterOption.class,
		method="saveChosenAscensionLevel"
)
public class SaveChosenAscensionLevelSwitch
{
	@SpireInsertPatch(
			locator=Locator.class,
			localvars={"pref"}
	)
	public static void Insert(CharacterOption __instance, int level, @ByRef Prefs[] pref)
	{
		if (!BaseMod.isBaseGameCharacter(__instance.c)) {
			pref[0] = BaseMod.playerStatsMap.get(__instance.c).pref;
		}
	}

	private static class Locator extends SpireInsertLocator
	{
		@Override
		public int[] Locate(CtBehavior ctBehavior) throws Exception
		{
			Matcher finalMatcher = new Matcher.MethodCallMatcher(Prefs.class, "putInteger");
			return LineFinder.findInOrder(ctBehavior, finalMatcher);
		}
	}
}
