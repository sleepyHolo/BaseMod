package basemod.patches.com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import javassist.CtBehavior;

@SpirePatch(
		clz=SingleCardViewPopup.class,
		method="renderTips"
)
public class FixEnergyTooltip
{
	@SpireInsertPatch(
			locator=Locator.class,
			localvars={"s"}
	)
	public static void Insert(SingleCardViewPopup __instance, SpriteBatch sb, @ByRef String[] s)
	{
		if (s[0].equals("[E]")) {
			s[0] = "[R]";
		}
	}

	private static class Locator extends SpireInsertLocator
	{
		@Override
		public int[] Locate(CtBehavior ctBehavior) throws Exception
		{
			Matcher matcher = new Matcher.MethodCallMatcher(String.class, "equals");
			return LineFinder.findInOrder(ctBehavior, matcher);
		}
	}
}
