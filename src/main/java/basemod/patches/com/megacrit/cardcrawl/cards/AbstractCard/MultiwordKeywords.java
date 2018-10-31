package basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.TipHelper;
import javassist.CtBehavior;

import java.util.ArrayList;
import java.util.List;

public class MultiwordKeywords
{
	@SpirePatch(
			clz=AbstractCard.class,
			method="initializeDescription"
	)
	public static class InitializeDescription
	{
		@SpireInsertPatch(
				locator=Locator.class,
				localvars={"word", "keywordTmp"}
		)
		public static void Insert(AbstractCard __instance, @ByRef String[] word, String keywordTmp)
		{
			if (word[0].contains("_") && !keywordTmp.contains("_")) {
				String tmp = word[0].replace('_', ' ');
				StringBuilder builder = new StringBuilder();
				for (String w : tmp.split(" ")) {
					builder.append('*').append(w).append(' ');
				}
				// Trim to removing ending space
				// substring to remove starting *
				word[0] = builder.toString().trim().substring(1);
			}
		}

		private static class Locator extends SpireInsertLocator
		{
			@Override
			public int[] Locate(CtBehavior ctBehavior) throws Exception
			{
				Matcher matcher = new Matcher.MethodCallMatcher(StringBuilder.class, "append");
				List<Matcher> prevMatchers = new ArrayList<>();
				prevMatchers.add(matcher);
				prevMatchers.add(matcher);
				return LineFinder.findInOrder(ctBehavior, prevMatchers, matcher);
			}
		}
	}

	@SpirePatch(
			clz=TipHelper.class,
			method="capitalize"
	)
	public static class BetterCapitalize
	{
		public static String Replace(String input)
		{
			// Capitalize each word
			StringBuilder builder = new StringBuilder();
			for (String w : input.split(" ")) {
				builder.append(w.substring(0, 1).toUpperCase()).append(w.substring(1).toLowerCase()).append(' ');
			}
			return builder.toString().trim();
		}
	}
}
