package basemod.patches.com.megacrit.cardcrawl.screens.custom.CustomModeScreen;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.screens.custom.CustomModeCharacterButton;
import com.megacrit.cardcrawl.screens.custom.CustomModeScreen;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(
		clz=CustomModeScreen.class,
		method="initializeCharacters"
)
public class InitializeCharacterButtons
{
	@SpireInsertPatch(
			locator=Locator.class
	)
	public static void Insert(CustomModeScreen __instance)
	{
		__instance.options.addAll(BaseMod.generateCustomCharacterOptions());
	}

	private static class Locator extends SpireInsertLocator
	{
		@Override
		public int[] Locate(CtBehavior ctBehavior) throws Exception {
			Matcher finalMatcher = new Matcher.NewExprMatcher(CustomModeCharacterButton.class);

			int[] locations = LineFinder.findAllInOrder(ctBehavior, finalMatcher);
			return new int[]{locations[locations.length - 1]+1};
		}
	}
}
