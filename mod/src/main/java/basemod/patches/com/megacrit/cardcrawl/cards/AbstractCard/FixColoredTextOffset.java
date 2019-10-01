package basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.FontHelper;
import javassist.CtBehavior;

@SpirePatch(
		clz=AbstractCard.class,
		method="initializeDescription"
)
public class FixColoredTextOffset
{
	@SpireInsertPatch(
			locator=Locator.class,
			localvars={"word"}
	)
	public static void Insert(AbstractCard __instance, GlyphLayout ___gl, String word)
	{
		// TODO this check should be less hardcoded
		if (word.length() > 3) {
			___gl.setText(FontHelper.cardDescFont_N, word);
		}
	}

	private static class Locator extends SpireInsertLocator
	{
		@Override
		public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
		{
			Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "logger");
			return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
		}
	}
}
