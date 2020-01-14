package basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.FontHelper;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(
		clz=AbstractCard.class,
		method="initializeDescription"
)
public class FixColoredTextOffset
{
	@SpireInsertPatch(
			locator=Locator.class,
			localvars={"word", "sbuilder2"}
	)
	public static void Insert(AbstractCard __instance, GlyphLayout ___gl, String word, StringBuilder sbuilder2)
	{
		// TODO this check should be less hardcoded
		if (word.length() > 3) {
			___gl.setText(FontHelper.cardDescFont_N, word + sbuilder2);
		}
	}

	private static class Locator extends SpireInsertLocator
	{
		@Override
		public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
		{
			Matcher finalMatcher1 = new Matcher.MethodCallMatcher(ArrayList.class, "contains");
			Matcher finalMatcher2 = new Matcher.FieldAccessMatcher(AbstractCard.class, "logger");
			int[] result1 = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher1);
			int[] result2 = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher2);
			return new int[] { result1[1], result1[2], result1[3], result1[4], result1[5], result2[0] };
		}
	}
}
